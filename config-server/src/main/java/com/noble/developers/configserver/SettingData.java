package com.noble.developers.configserver;


public class SettingData {

	private String server_port;
	private String base_dir_general;
	private String  base_dir_maintenance;
	private String  page_Maintenance;
	private String  page_OutOfService;
	private String errMsg;
	
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
		return page_Maintenance;
	}
	public void setPage_Maintenance(String page_Maintenance) {
		this.page_Maintenance = page_Maintenance;
	}
	public String getPage_OutOfService() {
		return page_OutOfService;
	}
	public void setPage_OutOfService(String page_OutOfService) {
		this.page_OutOfService = page_OutOfService;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
