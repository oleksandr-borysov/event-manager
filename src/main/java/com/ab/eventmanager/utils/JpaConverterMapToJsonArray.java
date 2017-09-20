package com.ab.eventmanager.utils;

import java.io.IOException;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JpaConverterMapToJsonArray implements AttributeConverter<Object, String> {

	private final static ObjectMapper objectMapper = new ObjectMapper();
	
	public String convertToDatabaseColumn(Object value) {
	    try {
	        return objectMapper.writeValueAsString(value);
	      } catch (JsonProcessingException ex) {
	    	return null;
	      }
	}

	public Object convertToEntityAttribute(String value) {
	    try {
	        return objectMapper.readValue(value, Object.class);
	      } catch (IOException ex) {
	        return null;
	      }
	}

}
