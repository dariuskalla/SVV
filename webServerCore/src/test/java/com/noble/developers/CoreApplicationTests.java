package com.noble.developers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.noble.developers.controller.SettingController;
import com.noble.developers.util.FileReader;
import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class CoreApplicationTests {

	@Autowired
    private SettingController settingController;

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private FileReader fileReader;
	
	@Value("${base-dir.maintenance}")
	private String maintenanceDirectory;

	@Value("${base-dir.general}")
	private String generalDirectory;
	
	@Value("${Page.Maintenance}")
	private String maintenancePage;
	
	@Value("${Page.OutOfService}")
	private String outOfServicePage;
	
	
	@Test
	void contextLoads() {
		assertThat(settingController).isNotNull();
	}
	
    @Test
    public void testSettingController() {
        assertEquals(
                "class com.noble.developers.controller.SettingController",
                this.settingController.getClass().toString());
    }	
	
    
	@Test
	public void testMaintananceMode() throws Exception 
	{
		this.mockMvc.perform(get("/setting/mode/Maintanance")).andExpect(status().isOk())
				.andExpect(content().string(containsString("Maintanance")));

    	String filePath = maintenanceDirectory+"\\"+maintenancePage;
    	String expected = fileReader.getFileBody(filePath);
		
		this.mockMvc.perform(get("/Home.html")).andExpect(status().isOk())
		.andExpect(content().string(containsString(expected)));
	}    
    
	@Test
	public void testOutOfServiceMode() throws Exception 
	{
		this.mockMvc.perform(get("/setting/mode/OutOfService")).andExpect(status().isOk())
				.andExpect(content().string(containsString("OutOfService")));

    	String filePath = maintenanceDirectory+"\\"+outOfServicePage;
    	String expected = fileReader.getFileBody(filePath);
		
		this.mockMvc.perform(get("/Home.html")).andExpect(status().isOk())
		.andExpect(content().string(containsString(expected)));
	}    
	
	
	@Test
	public void testRunningMode() throws Exception 
	{
		this.mockMvc.perform(get("/setting/mode/Running")).andExpect(status().isOk())
				.andExpect(content().string(containsString("Running")));

    	String filePath = generalDirectory+"\\Home.html";
    	String expected = fileReader.getFileBody2(filePath);
		
		this.mockMvc.perform(get("/Home.html")).andExpect(status().isOk())
		.andExpect(content().string(containsString(expected)));
	}    
	
	
}
