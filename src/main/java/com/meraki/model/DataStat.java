package com.meraki.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;


@Entity
@IdClass(DataStat_ID.class)
public class DataStat {
	
	@Id
	private long device_id;
	@Id
	private long timestamp_start;
	private int min;
	private int max;
	private double avg;
	
	public DataStat(){
		
	}

	public DataStat(long device_id, long timestamp, int min, int max, double avg) {
		super();
		this.device_id = device_id;
		this.timestamp_start = timestamp;
		this.min = min;
		this.max = max;
		this.avg = avg;
	}

	public long getDevice_id() {
		return device_id;
	}

	public void setDevice_id(long device_id) {
		this.device_id = device_id;
	}

	public long getTimestampStart() {
		return timestamp_start;
	}

	public void setTimestampStart(long timestamp) {
		this.timestamp_start = timestamp;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	@Override
	public String toString() {
		return "DataStat [device_id=" + device_id + ", timestamp=" + timestamp_start + ", min=" + min + ", max=" + max
				+ ", avg=" + avg + "]";
	}
	
	
}
