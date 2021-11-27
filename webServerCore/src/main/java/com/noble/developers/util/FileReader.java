package com.noble.developers.util;

import java.io.File;
import java.nio.file.Files;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;



@Component
public class FileReader {

    public String  getFileBody(String filePath) 
    {
    	String rw = "";
    	File file;
		try {
			file = ResourceUtils.getFile(filePath);
//			System.out.println("File Found : " + file.exists());

			//Read File Content
			rw = new String(Files.readAllBytes(file.toPath()));  
			rw = rw.replaceAll(".png\"", ".png?skipMainFilter=1\"");
			rw = rw.replaceAll(".jpg\"", ".jpg?skipMainFilter=1\"");
			rw = rw.replaceAll(".jpeg\"", ".jpeg?skipMainFilter=1\"");
			rw = rw.replaceAll(".css\"", ".css?skipMainFilter=1\"");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return rw;   
}
    
    public String  getFileBody2(String filePath) 
    {
    	String rw = "";
    	File file;
		try {
			file = ResourceUtils.getFile(filePath);
//			System.out.println("File Found : " + file.exists());

			//Read File Content
			rw = new String(Files.readAllBytes(file.toPath()));  
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return rw;   
}

}