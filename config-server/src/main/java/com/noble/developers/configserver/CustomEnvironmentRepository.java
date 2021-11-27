package com.noble.developers.configserver;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.stereotype.Component;


import org.springframework.core.Ordered;

@Component
public class CustomEnvironmentRepository implements EnvironmentRepository, Ordered
{
    @Autowired
    private SystemSetting systemSetting;
	
	
	
    public Environment findOne(String application, String profile, String label)
    {
        Environment environment = new Environment(application, profile);
        final Map<String, String> properties = new HashMap<String, String>();
        properties.put("server.mode", systemSetting.getServer_mode());
        properties.put("server.port", systemSetting.getServer_port());
        properties.put("management.endpoints.web.exposure.include", "refresh,restart");
        properties.put("Page.Maintenance", systemSetting.getPage_Maintenance());
        properties.put("Page.OutOfService", systemSetting.getPage_OutOfService());
        properties.put("base-dir.maintenance", systemSetting.getBase_dir_maintenance());
        properties.put("base-dir.general", systemSetting.getBase_dir_general());
        properties.put("spring.web.resources.static-locations", "file:"+systemSetting.getBase_dir_general()+",file:"+systemSetting.getBase_dir_maintenance());
        environment.add(new PropertySource("mapPropertySource", properties));
        return environment;
    }

	public int getOrder() {
		// TODO Auto-generated method stub
		return Ordered.HIGHEST_PRECEDENCE;
	}
}