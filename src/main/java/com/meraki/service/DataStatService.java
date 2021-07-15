package com.meraki.service;

import java.util.List;

import com.meraki.model.DataInput;
import com.meraki.model.DataStat;

public interface DataStatService {
	public long getMinuteCount(DataInput data);
	public void setNextMinuteCount();
	public void saveToDb(List<DataStat> list);
	public List<DataStat> getAllDeviceStat();
	public void updateDb(DataStat dataStatList);
}
