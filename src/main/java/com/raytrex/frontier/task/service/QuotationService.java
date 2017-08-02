package com.raytrex.frontier.task.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raytrex.frontier.repository.KeysightPriceHistoryRepository;
import com.raytrex.frontier.repository.KeysightPriceRepository;
import com.raytrex.frontier.repository.QuotationItemDetailRespository;
import com.raytrex.frontier.repository.QuotationItemRespository;
import com.raytrex.frontier.repository.QuotationRespository;
import com.raytrex.frontier.repository.SerialNoRepository;
import com.raytrex.frontier.repository.bean.KeysightPrice;
import com.raytrex.frontier.repository.bean.KeysightPriceHistory;
import com.raytrex.frontier.repository.bean.Quotation;
import com.raytrex.frontier.repository.bean.QuotationItem;
import com.raytrex.frontier.repository.bean.QuotationItemDetail;
import com.raytrex.frontier.repository.bean.SerialNo;

@Service
public class QuotationService {
	static Logger log = Logger.getLogger(QuotationService.class);
	@Autowired
	private KeysightPriceRepository keysightPriceRepository;
	@Autowired
	private KeysightPriceHistoryRepository keysightPriceHistoryRepository;
	@Autowired
	private QuotationRespository quotationRespository;
	@Autowired
	private QuotationItemRespository quotationItemRespository;
	@Autowired
	private QuotationItemDetailRespository quotationItemDetailRespository;
	@Autowired
	private SerialNoRepository serialRepository;
	
	public List<KeysightPrice> parseKeysightXLSXPriceExcel(Workbook wb) {
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rowIt = sheet.rowIterator();
		List<KeysightPrice> kpList = new ArrayList<KeysightPrice>();
		boolean firstRow = true;
		String listPriceCurrency = "";
		String netCurrency = "";
		while (rowIt.hasNext()) {
			Row row = rowIt.next();
			KeysightPrice kp = new KeysightPrice();

			Cell productNoCell = row.getCell(0);
			if (productNoCell.getCellTypeEnum().equals(CellType.STRING)) {
				kp.setProductNo(productNoCell.getStringCellValue());
			}
			Cell productOptionCell = row.getCell(1);
			if (productOptionCell.getCellTypeEnum().equals(CellType.STRING)) {
				kp.setProductOption(productOptionCell.getStringCellValue());
			}
			Cell descriptionCell = row.getCell(2);
			if (descriptionCell.getCellTypeEnum().equals(CellType.STRING)) {
				kp.setDescription(descriptionCell.getStringCellValue());
			}
			Cell listPriceCell = row.getCell(3);
			if (listPriceCell.getCellTypeEnum().equals(CellType.NUMERIC)) {
				kp.setListPrice(String.valueOf(Double.valueOf(listPriceCell.getNumericCellValue()).intValue()));
			} else if (listPriceCell.getCellTypeEnum().equals(CellType.STRING)) {
				kp.setListPrice(listPriceCell.getStringCellValue());
			}

			Cell discountQualifierGroupCell = row.getCell(4);
			if (discountQualifierGroupCell.getCellTypeEnum().equals(CellType.STRING)) {
				kp.setDiscountQulifierGroup(discountQualifierGroupCell.getStringCellValue());
			}
			Cell dicountPercentageCell = row.getCell(5);
			if (dicountPercentageCell.getCellTypeEnum().equals(CellType.STRING)) {
				kp.setDiscountPersentage(dicountPercentageCell.getStringCellValue());
			} else if (listPriceCell.getCellTypeEnum().equals(CellType.NUMERIC)) {
				kp.setDiscountPersentage(
						String.valueOf(Double.valueOf(dicountPercentageCell.getNumericCellValue()).intValue()));
			}
			Cell netPriceCell = row.getCell(6);
			if (netPriceCell.getCellTypeEnum().equals(CellType.STRING)) {
				kp.setNetPrice(netPriceCell.getStringCellValue());
			} else if (listPriceCell.getCellTypeEnum().equals(CellType.NUMERIC)) {
				kp.setNetPrice(String.valueOf(Double.valueOf(netPriceCell.getNumericCellValue()).intValue()));
			}
			Cell productLineCell = row.getCell(7);
			if (productLineCell.getCellTypeEnum().equals(CellType.STRING)) {
				kp.setProductLine(productLineCell.getStringCellValue());
			}
			Cell rmuCodeCell = row.getCell(8);
			if (rmuCodeCell.getCellTypeEnum().equals(CellType.STRING)) {
				kp.setRmuCode(rmuCodeCell.getStringCellValue());
			}
			Cell lastOrderDateCell = row.getCell(9);
			if (lastOrderDateCell.getCellTypeEnum().equals(CellType.STRING)) {
				kp.setLastOrderDate(lastOrderDateCell.getStringCellValue());
			}
			if (!firstRow) {
				kp.setListCurrency(listPriceCurrency);
				kp.setNetCurrency(netCurrency);
				kpList.add(kp);
			} else {
				if (kp.getListPrice().indexOf("(") != -1) {
					String lp = kp.getListPrice();
					listPriceCurrency = kp.getListPrice().substring(lp.indexOf("(") + 1, lp.length() - 1);
				}
				if (kp.getNetPrice().indexOf("(") != -1) {
					String np = kp.getNetPrice();
					netCurrency = kp.getNetPrice().substring(np.indexOf("(") + 1, np.length() - 1);
				}
				firstRow = false;
			}
		}
		this.saveKeysightPriceList(kpList);
		return kpList;
	}

	private void saveKeysightPriceList(List<KeysightPrice> keysightPriceList) {
		if (keysightPriceList.isEmpty()) {
			return;
		}
		List<KeysightPrice> dbKeysightPriceList = keysightPriceRepository.findAll();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date nowC = new Date(System.currentTimeMillis());
		String priceVersion = sdf.format(nowC);
		Timestamp now = new Timestamp(nowC.getTime());
		// 轉存現存Keysight Price到Keysight Price History
		for (KeysightPrice dbKp : dbKeysightPriceList) {
			KeysightPriceHistory kph = new KeysightPriceHistory();
			kph.setPriceVersion(priceVersion);
			kph.setProductNo(dbKp.getProductNo());
			kph.setProductOption(dbKp.getProductOption());
			kph.setDescription(dbKp.getDescription());
			kph.setListPrice(dbKp.getListPrice());
			kph.setListCurrency(dbKp.getListCurrency());
			kph.setDiscountQulifierGroup(dbKp.getDiscountQulifierGroup());
			kph.setDiscountPersentage(dbKp.getDiscountPersentage());
			kph.setNetPrice(dbKp.getNetPrice());
			kph.setNetCurrency(dbKp.getNetCurrency());
			kph.setProductLine(dbKp.getProductLine());
			kph.setRmuCode(dbKp.getRmuCode());
			kph.setLastOrderDate(dbKp.getLastOrderDate());
			kph.setUpdateTime(now);
			keysightPriceHistoryRepository.save(kph);
		}
		// 刪除全部現存Keysight Price
		keysightPriceRepository.deleteAll();

		// 將新的Keysight Price存入
		for (KeysightPrice kp : keysightPriceList) {
			kp.setUpdateTime(now);
			keysightPriceRepository.save(kp);
		}
	}

	public List<KeysightPrice> findProductNo(String key) {
		return keysightPriceRepository.findAllByProductNoLike("%" + key + "%");
	}

	public Map<String, Double> getExchangeRate() {
		Map<String,Double> exchangeMap = new HashMap<String,Double>();
		try {
			URI uri = new URI("http://rate.bot.com.tw/xrt/flcsv/0/day");
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet get = new HttpGet();
			get.setURI(uri);
			HttpResponse response = client.execute(get);
			int responseCode = response.getStatusLine().getStatusCode();

			log.info("Sending 'GET' request to URL : " + uri.toString());
			log.info("Response Code : " + responseCode);
			InputStream content = response.getEntity().getContent();
			BufferedReader rd = new BufferedReader(new InputStreamReader(content));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line).append("\n");
			}
			content.close();
			String[] rLines = result.toString().split("\n");
			for(int index = 1; index < rLines.length ;index++){
				String[] rLineSplit = rLines[index].split(",");
				if(rLineSplit.length >= 3){
					Double rate = Double.valueOf(rLineSplit[12]);
					exchangeMap.put(rLineSplit[0], rate);
				}	
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exchangeMap;
	}
	
	public Quotation getQuotation(String taskNo){
		List<Quotation> list = quotationRespository.findAllByTaskNo(taskNo);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}
	
	public List<QuotationItem> getQuotationItem(String quotationNo){
		return quotationItemRespository.findAllByQuotationNo(quotationNo);
	}
	
	public Quotation saveQuotation(Quotation quotation){
		Quotation newQ = quotationRespository.save(quotation);
		return newQ;
	}
	
	public List<QuotationItem> saveQuotationItem(Quotation quotation,List<QuotationItem> quotationItemList){
		for(QuotationItem qi : quotationItemList){
			qi.setQuotationNo(quotation.getQuotationNo());
			QuotationItem newQi = quotationItemRespository.save(qi);
			List<QuotationItemDetail> qidList = qi.getQuotationItemDetailList();
			for(QuotationItemDetail qid:qidList){
				qid.setQuotationItem(newQi);
				if(qid.getUpdateTime() == null){
					qid.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				}
				quotationItemDetailRespository.save(qid);
			}
		}
		return quotationItemList;
	}
	
	public String getQuotationNo(){
		DecimalFormat df = new DecimalFormat("00");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(new Date());
		SerialNo serialNo = serialRepository.findOne("QN");
		if(serialNo == null){
			SerialNo sn = new SerialNo();
			sn.setCount(0);
			sn.setSerialName("QN");
			sn.setDescription("Quotation序號");
			serialNo = serialRepository.save(sn);
		}else if(!sdf.format(serialNo.getUpdateTime()).equalsIgnoreCase(today)){
			serialNo.setCount(0);
		}
		
		Integer count = serialNo.getCount()+1;
		serialNo.setCount(count);
		String no = "QN"+today+ df.format(count);
		serialRepository.save(serialNo);
		return no;
	}
}
