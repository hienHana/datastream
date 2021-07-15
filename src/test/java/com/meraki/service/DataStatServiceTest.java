package com.meraki.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import com.meraki.model.DataStat;
import com.meraki.repository.DataStatRepository;

@RunWith(MockitoJUnitRunner.class)
public class DataStatServiceTest {

	@Mock 
	private DataStatRepository repo;
	
	@InjectMocks 
	private DataStatServiceImpl service;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getAllDeviceStatTest() {
		when(repo.findAll()).thenReturn(Arrays.asList(
				new DataStat(1, 1611741600, 1, 2, 1.5),
				new DataStat(2, 1611741600, 1, 3, 2),
				new DataStat(1, 1611741660, 6, 6, 6),
				new DataStat(1, 1611741900, 9, 9, 9)));
		
		List<DataStat> list = service.getAllDeviceStat();
		assertEquals(4, list.size());
	}
}
