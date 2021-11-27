package com.noble.developers.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import com.noble.developers.dto.ServerMode;

@RefreshScope
@Component
public class SystemSetting {
	
	@Value("${server.mode}")
	private String server_mode;
	
	
	public SystemSetting() {
		super();
	}
	

	public ServerMode getSystemMode() 
	{
		ServerMode systemMode=null;
		if(server_mode.equals("Running"))
			systemMode = ServerMode.Running;
		else if(server_mode.equals("Maintanance"))
			systemMode = ServerMode.Maintanance;
		else
			systemMode = ServerMode.OutOfService;
		return systemMode;
	}

	public void setSystemMode(ServerMode serverMode) {
		this.server_mode = serverMode.toString();
	}
	
	
	
	

}
