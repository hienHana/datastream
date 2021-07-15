package com.meraki.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.meraki.model.DataInput;
import com.meraki.model.DataStat;
import com.meraki.repository.DataStatRepository;

@Service
public class DataStatServiceImpl implements DataStatService{

	@Autowired DataStatRepository repo;
	@Autowired JdbcTemplate jdbcTemplate;
	
	
	private static long minuteCount = 0;

	@Override
	public long getMinuteCount(DataInput data) {
		//if minuteCount is 0, assign the given timestamp with the condition that it is the multiplication of 60
		if(minuteCount == 0) {
			long givenTimeStamp = data.getTimestamp();
			minuteCount = givenTimeStamp - (givenTimeStamp % 60) ;
		}
		return minuteCount;
	}
	
	@Override
	public void setNextMinuteCount() {
		minuteCount += 60;
	}
	
	//use jdbcTemplate.batchUpdate() to insert to DB in batch to improve performance
	public void saveToDb(List<DataStat> dataStatList){
		String sql ="insert into datastat(device_id, timestamp_start, min, max, avg) values (?, ?, ?, ?, ?)";
		
		List<Object[]> objList = new ArrayList<>();
		for (DataStat each : dataStatList){
			Object[] objArr = {each.getDevice_id(), each.getTimestampStart(), each.getMin(), each.getMax(), each.getAvg() };
			objList.add(objArr);
		}
		
		jdbcTemplate.batchUpdate(sql, objList);
	}
	
	public void updateDb(DataStat dataStat){
		String sql ="update datastat set min=?, max=?, avg=? where device_id=? and timestamp_start=?";
		
		jdbcTemplate.update(sql, new Object[] {dataStat.getMin(), dataStat.getMax(), dataStat.getAvg(), 
				dataStat.getDevice_id(), dataStat.getTimestampStart()});
	}

	@Override
	public List<DataStat> getAllDeviceStat() {
		return repo.findAll();
	}
	
}
