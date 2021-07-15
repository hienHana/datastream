package com.meraki.model;


public class DataInput {
	private long device_id;
	private long timestamp;
	private int value;
	
	public DataInput() {}

	public DataInput(long device_id, long timestamp, int value) {
		super();
		this.device_id = device_id;
		this.timestamp = timestamp;
		this.value = value;
	}

	public long getDevice_id() {
		return device_id;
	}

	public void setDevice_id(long device_id) {
		this.device_id = device_id;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "DataInput [device_id=" + device_id + ", timestamp=" + timestamp + ", value=" + value + "]";
	}
	
	
}
