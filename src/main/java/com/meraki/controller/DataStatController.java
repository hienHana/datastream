package com.meraki.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meraki.model.DataInput;
import com.meraki.model.DataStat;
import com.meraki.service.DataStatService;

@RestController
@RequestMapping(value = "datastream")
public class DataStatController {
	
	@Autowired DataStatService dataStatService;
	
	//map <key: device_id, value: list<min, max, sum, count>>>
	private Map<Long,List<Integer>> map = new HashMap<>();
	
	private Map<Long,List<Integer>> copy;
	
	@PostMapping
	public ResponseEntity<List<DataStat>> storeDataEachMinute(@RequestBody DataInput data){
		 
		long minuteStart = dataStatService.getMinuteCount(data);
		long minuteEnd = minuteStart + 60;
		
		if(data.getTimestamp() >= minuteEnd) { //over one minute
			List<DataStat> listToSaveToDb = processData(map, minuteStart);
			dataStatService.saveToDb(listToSaveToDb);
			setNextMinute(minuteEnd, data);	
			
			//make a copy so it can be used in case seeing a timestamp falls into previous timestamp
			copy = new HashMap<>(map);
			map.clear();
			keepDataInMapForProcessing(map, data.getDevice_id(), data.getValue());
			System.out.println("In memory map after each minute of given timestamp: " + map);
		}
		//timestamp falls into previous timestamp
		else if(data.getTimestamp() < minuteStart) { 
			DataStat updateItem = getUpdate(copy, minuteStart-60, data);
			dataStatService.updateDb(updateItem);
		}
		else {
			keepDataInMapForProcessing(map, data.getDevice_id(), data.getValue());
			System.out.println("In memory map after each minute of given timestamp: " + map);
		}
			
		return new ResponseEntity<List<DataStat>>(dataStatService.getAllDeviceStat(), HttpStatus.OK);
	}
	
	public void keepDataInMapForProcessing(Map<Long,List<Integer>> map, long device_id, int value){
		
		List<Integer> list = new ArrayList<>();
		int min = value;
		int max = value;
		int sum = value;
		int count = 1;
		
		if(map.containsKey(device_id)) {
			list = map.get(device_id);
		
			min = Math.min(list.get(0), min);
			max = Math.max(list.get(1), max);
			sum += list.get(2);
			count = list.get(3) + 1;
			list.clear();			
		}
		
		list.add(min);
		list.add(max);
		list.add(sum);
		list.add(count);
				
		map.put(device_id, list);
	}
	
	public List<DataStat>  processData(Map<Long,List<Integer>> map, long minuteStart) {
		List<DataStat> list = new ArrayList<>();
		for(Long key:map.keySet()) {
			DataStat eachDevice = new DataStat();
			eachDevice.setDevice_id(key);
			eachDevice.setTimestampStart(minuteStart);
			
			List<Integer> listOfValues = map.get(key);
			int min = listOfValues.get(0);
			int max = listOfValues.get(1);
			int sum = listOfValues.get(2); 
			int count = listOfValues.get(3);
			double avg = (double)sum/count;
			
			eachDevice.setMin(min);
			eachDevice.setMax(max);
			eachDevice.setAvg(avg);
			
			list.add(eachDevice);
		}
		return list;
	}
	
	public DataStat getUpdate(Map<Long,List<Integer>> copy, long minuteStart, DataInput data) {
		DataStat dataStat = new DataStat();
		dataStat.setDevice_id(data.getDevice_id());
		dataStat.setTimestampStart(minuteStart);
		
		List<Integer> list = copy.get(data.getDevice_id());
		
		int min = Math.min(data.getValue(), list.get(0));
		int max = Math.max(data.getValue(), list.get(1));
		int sum = data.getValue() + list.get(2);
		int count = list.get(3)+1;
		
		dataStat.setMin(min);
		dataStat.setMax(max);
		dataStat.setAvg((double)sum / count);
		
		list.clear();
		list.add(min);
		list.add(max);
		list.add(sum);
		list.add(count);
		
		return dataStat;
	}
	
	public long setNextMinute(long minuteEnd, DataInput data) {
		long minuteStart = 0;
		do {
			dataStatService.setNextMinuteCount();
			minuteStart = dataStatService.getMinuteCount(data);
		}while(minuteStart + 60 < data.getTimestamp());
		return minuteStart;
	}
	
	@GetMapping
	public ResponseEntity<List<DataStat>> displayAllDeviceStat(){
		return new ResponseEntity<List<DataStat>>(dataStatService.getAllDeviceStat(), HttpStatus.OK);
	}
}
