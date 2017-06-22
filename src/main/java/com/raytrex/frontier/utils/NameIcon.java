package com.raytrex.frontier.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class NameIcon {

	public static BufferedImage genNameIcon(String name,Integer size){
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(Color.decode("#215B7D"));
		g2d.drawRect(0, 0, size, size);
		g2d.setColor(Color.WHITE);
		g2d.drawString(name, 0, 0);
		
		return image;
	}
}
