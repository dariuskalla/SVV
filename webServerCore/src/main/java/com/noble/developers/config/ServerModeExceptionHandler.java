package com.noble.developers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.noble.developers.dto.ServerMode;
import com.noble.developers.util.FileReader;


@RefreshScope
@RestControllerAdvice
public class ServerModeExceptionHandler {

	@Value("${base-dir.maintenance}")
	private String maintenanceDirectory;

	
	@Value("${Page.Maintenance}")
	private String maintenancePage;
	
	@Value("${Page.OutOfService}")
	private String outOfServicePage;

	@Autowired
	private FileReader fileReader;
	
	
    @ExceptionHandler(ServerModeException.class)
    public String  maintenance(ServerModeException ex) {

    	
    	String filePath = "";
    	if(ex.serverStatus==ServerMode.Maintanance)
    		filePath = maintenanceDirectory+"\\"+maintenancePage;
    	else if(ex.serverStatus==ServerMode.OutOfService)
    		filePath = maintenanceDirectory+"\\"+outOfServicePage;

    	String rw = fileReader.getFileBody(filePath);
        return rw;   
}
    

}