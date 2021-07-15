package com.meraki.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.meraki.model.DataInput;
import com.meraki.model.DataStat;
import com.meraki.service.DataStatService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebMvcTest(DataStatController.class)
public class DataStatControllerTest {
	@Autowired private MockMvc mockMvc;
	
	@MockBean
	private DataStatService dataStatService;
	
	private DataStatController dataStatController;
	
	@Test
	public void displayAllDeviceStatAPI() throws Exception {
		when(dataStatService.getAllDeviceStat())
		.thenReturn(Arrays.asList( 
				new DataStat(1, 1611741600, 1, 2, 1.5),
				new DataStat(2, 1611741600, 1, 3, 2),
				new DataStat(1, 1611741660, 6, 6, 6),
				new DataStat(1, 1611741900, 9, 9, 9)));

	
		RequestBuilder request = MockMvcRequestBuilders
				.get("/datastream")
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json(
						"[{device_id: 1, min: 1, max: 2, avg: 1.5, timestampStart: 1611741600},\r\n" + 
						"{device_id: 2, min: 1, max: 3, avg: 2.0, timestampStart: 1611741600},\r\n" + 
						"{device_id: 1, min: 6, max: 6, avg: 6.0, timestampStart: 1611741660},\r\n" + 
						"{device_id: 1, min: 9, max: 9, avg: 9.0, timestampStart: 1611741900}]"
						))					
				.andReturn();
		
	}
	
	
}

