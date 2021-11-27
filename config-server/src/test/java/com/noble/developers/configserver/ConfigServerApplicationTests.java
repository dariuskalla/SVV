package com.noble.developers.configserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "Admin", password = "Admin")
class ConfigServerApplicationTests {

	
	@Autowired
    private SettingController settingController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void contextLoads() {
		assertThat(settingController).isNotNull();
	}

	
    @Test
    public void testSettingController() {
        assertEquals(
                "class com.noble.developers.configserver.SettingController",
                this.settingController.getClass().toString());
    }	

    
    @Test
    public void testLoadInfo() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/info"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertTrue(content.contains("Web Server Status"));
        assertTrue(content.contains("Server Mode:"));
    }    
    
    
    @Test
    public void testLoadConfig() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/changeConfig"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        assertNotNull(content);
        assertTrue(content.contains("Web Server Configuration"));
        assertTrue(content.contains("Configuration Parameters"));
    }    
    
    
}
