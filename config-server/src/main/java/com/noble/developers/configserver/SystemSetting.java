package com.noble.developers.configserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton") 
public class SystemSetting {
	
	@Value("${default.server.port}")
	private String server_port;

	
	@Value("${initial.server.mode}")
	private String server_mode;
	
	
	@Value("${default.base-dir.general}")
	private String base_dir_general;
	
	@Value("${default.base-dir.maintenance}")
	private String  base_dir_maintenance;
	
	@Value("${default.Page.Maintenance}")
	private String  Page_Maintenance;
	
	@Value("${default.Page.OutOfService}")
	private String  Page_OutOfService;

	public SystemSetting() {
		super();
	}

	public String getServer_port() {
		return server_port;
	}

	public void setServer_port(String server_port) {
		this.server_port = server_port;
	}

	public String getBase_dir_general() {
		return base_dir_general;
	}

	public void setBase_dir_general(String base_dir_general) {
		this.base_dir_general = base_dir_general;
	}

	public String getBase_dir_maintenance() {
		return base_dir_maintenance;
	}

	public void setBase_dir_maintenance(String base_dir_maintenance) {
		this.base_dir_maintenance = base_dir_maintenance;
	}

	public String getPage_Maintenance() {
		return Page_Maintenance;
	}

	public void setPage_Maintenance(String page_Maintenance) {
		Page_Maintenance = page_Maintenance;
	}

	public String getPage_OutOfService() {
		return Page_OutOfService;
	}

	public void setPage_OutOfService(String page_OutOfService) {
		Page_OutOfService = page_OutOfService;
	}

	public String getServer_mode() {
		return server_mode;
	}

	public void setServer_mode(String server_mode) {
		this.server_mode = server_mode;
	}

	
	
	
	

}
