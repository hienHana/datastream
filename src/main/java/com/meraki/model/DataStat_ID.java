package com.meraki.model;

import java.io.Serializable;

import javax.persistence.Id;

public class DataStat_ID implements Serializable{
	private long device_id;
	private long timestamp_start;
	
	public DataStat_ID() {}

	public DataStat_ID(long device_id, long timestamp_start) {
		super();
		this.device_id = device_id;
		this.timestamp_start = timestamp_start;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (device_id ^ (device_id >>> 32));
		result = prime * result + (int) (timestamp_start ^ (timestamp_start >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataStat_ID other = (DataStat_ID) obj;
		if (device_id != other.device_id)
			return false;
		if (timestamp_start != other.timestamp_start)
			return false;
		return true;
	}
	
	
}
