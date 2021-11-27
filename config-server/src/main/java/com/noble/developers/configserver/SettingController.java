package com.noble.developers.configserver;

import java.io.File;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class SettingController {

    @Autowired
    private SystemSetting systemSetting;
	

	  @GetMapping("/info")
	  public String info(Model model) 
	  {
		  SettingData settingData = new SettingData();
		  settingData.setServer_port(systemSetting.getServer_port());
		  settingData.setBase_dir_general(systemSetting.getBase_dir_general());
		  settingData.setBase_dir_maintenance(systemSetting.getBase_dir_maintenance());
		  settingData.setPage_Maintenance(systemSetting.getPage_Maintenance());
		  settingData.setPage_OutOfService(systemSetting.getPage_OutOfService());
		  model.addAttribute("setting", settingData);
		  switch (systemSetting.getServer_mode()) 
		  {
		  case "Running":
			    return "info_running";
		  case "OutOfService":
			    return "info_outofservice";
		  case "Maintanance":
			    return "info_maintenance";
		}
		  return null;
	  }
    
	  @PostMapping("/stop")
	  public String stop(@ModelAttribute SettingData settingData, Model model) 
	  {
		  changeMode("OutOfService");
		  systemSetting.setServer_mode("OutOfService");
		  return "redirect:/info";
	  }	
    
	  @PostMapping("/start")
	  public String start(@ModelAttribute SettingData settingData, Model model) 
	  {
		  changeMode("Running");
		  systemSetting.setServer_mode("Running");
		  return "redirect:/info";
	  }	

	  
	  @PostMapping("/maintenance")
	  public String maintenance(@ModelAttribute SettingData settingData, Model model) 
	  {
		  changeMode("Maintanance");
		  systemSetting.setServer_mode("Maintanance");
		  return "redirect:/info";
	  }	

	  @GetMapping("/changeConfig")
	  public String changeConfig(Model model) 
	  {
		  return changeConfig(model, null);
	  }
	  
	  @PostMapping("/changeConfig")
	  public String changeConfig(Model model,String errMsg) 
	  {
		  SettingData settingData = new SettingData();
		  settingData.setServer_port(systemSetting.getServer_port());
		  settingData.setBase_dir_general(systemSetting.getBase_dir_general());
		  settingData.setBase_dir_maintenance(systemSetting.getBase_dir_maintenance());
		  settingData.setPage_Maintenance(systemSetting.getPage_Maintenance());
		  settingData.setPage_OutOfService(systemSetting.getPage_OutOfService());
		  settingData.setErrMsg(errMsg);
		  model.addAttribute("setting", settingData);
		  switch (systemSetting.getServer_mode()) 
		  {
		  case "Running":
			    return "config_running";
		  case "OutOfService":
			    return "config_outofservice";
		  case "Maintanance":
			    return "config_maintenance";
		}
		  return null;
	  }
	  

	  @PostMapping("/saveConfigOut")
	  public String saveConfigOut(@ModelAttribute SettingData settingData, Model model) 
	  {
		  //check port
		  String lastport = systemSetting.getServer_port();
		  try 
		  {
			int port = Integer.parseInt(settingData.getServer_port());
			if(!settingData.getServer_port().equalsIgnoreCase(lastport))
			{
				if(!isTcpPortAvailable(port))
					return changeConfig(model, "Server Port is Busy");
			}
		  } catch (Exception e) {
			// TODO: handle exception
			return changeConfig(model, "Invalid Server Port");
		  }
		  // check base dir general
		  File f1 = new File(settingData.getBase_dir_general());
		  if(!f1.exists() || !f1.isDirectory()) { 
				return changeConfig(model, "Invalide Base Directory General");
		  }
		  // check base dir maintenance
		  File f2 = new File(settingData.getBase_dir_maintenance());
		  if(!f2.exists() || !f2.isDirectory()) { 
				return changeConfig(model, "Invalide Base Directory Maintenance");
		  }
		  
		  // check Maintenance Page
		  File f3 = new File(settingData.getBase_dir_maintenance()+"\\"+settingData.getPage_Maintenance());
		  if(!f3.exists() || f3.isDirectory()) { 
				return changeConfig(model, "Invalide Maintenance Page");
		  }
		  
		  // check OutOfService Page
		  File f4 = new File(settingData.getBase_dir_maintenance()+"\\"+settingData.getPage_OutOfService());
		  if(!f4.exists() || f4.isDirectory()) { 
				return changeConfig(model, "Invalide OutOfService Page");
		  }

		  
	    	String lastDirGen = systemSetting.getBase_dir_general();
	    	String lastDirMai = systemSetting.getBase_dir_maintenance();
	    	
		  
		  systemSetting.setServer_port(settingData.getServer_port());
		  systemSetting.setBase_dir_general(settingData.getBase_dir_general());
		  systemSetting.setBase_dir_maintenance(settingData.getBase_dir_maintenance());
		  systemSetting.setPage_Maintenance(settingData.getPage_Maintenance());
		  systemSetting.setPage_OutOfService(settingData.getPage_OutOfService());
		  
	    	if(!lastport.equalsIgnoreCase(settingData.getServer_port())
	    		|| !lastDirGen.equalsIgnoreCase(settingData.getBase_dir_general())
	    		|| !lastDirMai.equalsIgnoreCase(settingData.getBase_dir_maintenance()))
	    		restartServer(lastport);
	    	else
	    		refreshServer(lastport);
			  return "redirect:/info";
	  }	
	
	  @PostMapping("/saveConfigMaintenance")
	  public String saveConfigMaintenance(@ModelAttribute SettingData settingData, Model model) 
	  {
		  // check base dir general
		  File f1 = new File(settingData.getBase_dir_general());
		  if(!f1.exists() || !f1.isDirectory()) { 
				return changeConfig(model, "Invalide Base Directory General");
		  }
	    	String lastDirGen = systemSetting.getBase_dir_general();
		    systemSetting.setBase_dir_general(settingData.getBase_dir_general());
	    	if( !lastDirGen.equalsIgnoreCase(settingData.getBase_dir_general()))
	    		restartServer(systemSetting.getServer_port());
			  return "redirect:/info";
	  }	
	  
	  @PostMapping("/saveConfigRunning")
	  public String saveConfigRunning(@ModelAttribute SettingData settingData, Model model) 
	  {
		  // check base dir maintenance
		  File f2 = new File(settingData.getBase_dir_maintenance());
		  if(!f2.exists() || !f2.isDirectory()) { 
				return changeConfig(model, "Invalide Base Directory Maintenance");
		  }
		  
		  // check Maintenance Page
		  File f3 = new File(settingData.getBase_dir_maintenance()+"\\"+settingData.getPage_Maintenance());
		  if(!f3.exists() || f3.isDirectory()) { 
				return changeConfig(model, "Invalide Maintenance Page");
		  }
		  
		  // check OutOfService Page
		  File f4 = new File(settingData.getBase_dir_maintenance()+"\\"+settingData.getPage_OutOfService());
		  if(!f4.exists() || f4.isDirectory()) { 
				return changeConfig(model, "Invalide OutOfService Page");
		  }

		  
	    	String lastDirMai = systemSetting.getBase_dir_maintenance();
	    	
		  
		  systemSetting.setBase_dir_maintenance(settingData.getBase_dir_maintenance());
		  systemSetting.setPage_Maintenance(settingData.getPage_Maintenance());
		  systemSetting.setPage_OutOfService(settingData.getPage_OutOfService());
		  
	    	if(!lastDirMai.equalsIgnoreCase(settingData.getBase_dir_maintenance()))
	    		restartServer(systemSetting.getServer_port());
	    	else
	    		refreshServer(systemSetting.getServer_port());
			  return "redirect:/info";
	  }	
	  
	  private boolean isTcpPortAvailable(int port) 
	  {
		    try (ServerSocket serverSocket = new ServerSocket()) {
		        // setReuseAddress(false) is required only on OSX, 
		        // otherwise the code will not work correctly on that platform          
		        serverSocket.setReuseAddress(false);
		        serverSocket.bind(new InetSocketAddress(InetAddress.getByName("localhost"), port), 1);
		        return true;
		    } catch (Exception ex) {
		        return false;
		    }
		}   
	  
	  private boolean changeMode(String mode) 
	  {
	    	String port = systemSetting.getServer_port();
	    	final String uri = "http://localhost:"+port+"/setting/mode/"+mode;
	    	RestTemplate restTemplate = new RestTemplate();
	    	HttpHeaders headers = new HttpHeaders();
	    	headers.setContentType(MediaType.APPLICATION_JSON);
	    	headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    	ResponseEntity<String> response = restTemplate.exchange(
	    			uri,
	    	        HttpMethod.GET,
	    	        new HttpEntity<Object>(headers),
	    	        String.class
	    	);
	    	if (response.getStatusCode() == HttpStatus.OK) 
	    	    return true;
	    	return false;
	  }	
	  
	  private boolean refreshServer(String port) 
	  {
	    	final String uri = "http://localhost:"+port+"/actuator/refresh";
	    	RestTemplate restTemplate = new RestTemplate();
	    	HttpHeaders headers = new HttpHeaders();
	    	headers.setContentType(MediaType.APPLICATION_JSON);
	    	headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    	HttpEntity<Map<String, Object>> entity = new HttpEntity<>(null, headers);
	    	ResponseEntity<String> response = restTemplate.postForEntity(uri, entity, String.class);    	
	    	if (response.getStatusCode() == HttpStatus.OK) 
	    	    return true;
	    	return false;
	  }		
	  private boolean restartServer(String port) 
	  {
		  try 
		  {
			  boolean result = false;
		    	final String uri = "http://localhost:"+port+"/actuator/restart";
		    	RestTemplate restTemplate = new RestTemplate();
		    	HttpHeaders headers = new HttpHeaders();
		    	headers.setContentType(MediaType.APPLICATION_JSON);
		    	headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		    	HttpEntity<Map<String, Object>> entity = new HttpEntity<>(null, headers);
		    	ResponseEntity<String> response = restTemplate.postForEntity(uri, entity, String.class);    	
		    	if (response.getStatusCode() == HttpStatus.OK) 
		    	    result = true;
		    	Thread.sleep(3000);
		    	return result;
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	  }		
}
