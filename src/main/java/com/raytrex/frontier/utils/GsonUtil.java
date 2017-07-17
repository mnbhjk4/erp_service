package com.raytrex.frontier.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {

	public static Gson getGson(){
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Double.class, new DoubleTypeAdapter());
		builder.registerTypeAdapter(Integer.class, new IntegerTypeAdapter());
		builder.registerTypeHierarchyAdapter(byte[].class,new ByteArrayToBase64TypeAdapter());
		builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssX:00");
		builder.excludeFieldsWithoutExposeAnnotation();
		return builder.create();
	}
}
