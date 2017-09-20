package com.ab.eventmanager.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ab.eventmanager.utils.JpaConverterMapToJsonArray;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Event {

	public static class KeyValue {
		String key;
		String value;
		
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Timestamp timestamp;
	private String deviceId;
	private String eventType;
	@Convert(converter = JpaConverterMapToJsonArray.class)
	private List<KeyValue> payload = new ArrayList<KeyValue>();

	public Event() {
		
	}
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String value) {
		this.deviceId = value;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String value) {
		this.eventType = value;
	}

	public List<KeyValue> getPayload() {
		return payload;
	}

	public void setPayload(List<KeyValue> value) {
		this.payload = value;
	}
}
