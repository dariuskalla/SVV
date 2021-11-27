package com.noble.developers.config;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.noble.developers.dto.ServerMode;
 
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerModeException extends RuntimeException 
{
	ServerMode serverStatus;
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ServerModeException(ServerMode mode) {
        super("");
		serverStatus = mode;
    }

}