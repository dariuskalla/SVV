package com.noble.developers.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.noble.developers.controller.SystemSetting;
import com.noble.developers.dto.ServerMode;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class CheckServerModeFilter implements Filter {

    public static final String API_actuator_URI = "/actuator/";
    public static final String API_MAINTENANCE_URI = "/setting/mode/";
    
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionHandler;

    @Autowired
    private SystemSetting systemSetting;

    
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
    {
		String uri = ((HttpServletRequest) request).getRequestURI();
		String queryString = ((HttpServletRequest) request).getQueryString();
		if(queryString==null)
			queryString = "";
		if(queryString.contains("skipMainFilter") || uri.contains(API_MAINTENANCE_URI) || uri.contains(API_actuator_URI))
		{
			 chain.doFilter(request, response);
		}
		else 
		{
	    	ServerMode serverMode = systemSetting.getSystemMode();
	        if (serverMode!=ServerMode.Running) 
	        {
//	            LOGGER.warn("Message handled during maintenance [{}]", ((HttpServletRequest) request).getRequestURI());
	            exceptionHandler.resolveException((HttpServletRequest) request, (HttpServletResponse) response, null, new ServerModeException(serverMode));
	        }
	        else 
	        {
	            chain.doFilter(request, response);
	        }
		}
		
		
    }

}
