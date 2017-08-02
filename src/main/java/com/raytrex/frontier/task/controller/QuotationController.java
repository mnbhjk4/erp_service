package com.raytrex.frontier.task.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.raytrex.frontier.repository.bean.KeysightPrice;
import com.raytrex.frontier.repository.bean.Quotation;
import com.raytrex.frontier.repository.bean.QuotationItem;
import com.raytrex.frontier.task.service.QuotationService;
import com.raytrex.frontier.utils.GsonUtil;

@RestController
@RequestMapping("/quotation")
public class QuotationController {
	static Logger log = Logger.getLogger(QuotationController.class);

	@Autowired
	private QuotationService quotationService;
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@PostMapping(value="/uploadPriceFile")
	public String uploadPriceFile(String uid,String access_token,MultipartFile file){
		if(!file.isEmpty()){
			byte[] bytes  = null;
			try {
				bytes = file.getBytes();
				ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
				Workbook workbook = WorkbookFactory.create(bais);
				List<KeysightPrice> reuslt = quotationService.parseKeysightXLSXPriceExcel(workbook);
				
			} catch (IOException e) {
				log.error("IO loading with MultipartFile fail,please confirm content.",e);
			} catch (EncryptedDocumentException e) {
				log.error("XLSX access encrypt failed..",e);
			} catch (InvalidFormatException e) {
				log.error("XLSX access only,please upload XLSX format.",e);
			}
		}
		return ""; 
	}
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@PostMapping(value="/findProductNo")
	public String findProductNo(String key){
		Gson gson = GsonUtil.getGson();
		return gson.toJson(quotationService.findProductNo(key));
	}
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@PostMapping(value="/getExchangeRate")
	public String getExchangeRate(){
		Gson gson = GsonUtil.getGson();
		return gson.toJson(quotationService.getExchangeRate());
	}
	
	@CrossOrigin(origins = {"*","http://localhost:8100"})
	@PostMapping(value="/getQuotation")
	public String getQuotation(String taskNo){
		Gson gson = GsonUtil.getGson();
		Quotation quotation = quotationService.getQuotation(taskNo);
		List<QuotationItem> quotationItemnList = quotationService.getQuotationItem(quotation.getQuotationNo());
		JsonObject jo = new JsonObject();
		jo.add("quotation", gson.toJsonTree(quotation));
		jo.add("quotationItemList", gson.toJsonTree(quotationItemnList));
		return gson.toJson(jo);
	}
}
