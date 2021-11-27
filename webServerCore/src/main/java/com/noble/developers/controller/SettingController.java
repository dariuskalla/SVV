package com.noble.developers.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.noble.developers.dto.ServerMode;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/setting")

public class SettingController {

    @Autowired
    private SystemSetting systemSetting;

    @RequestMapping(value="/mode/{mode}", method = RequestMethod.GET)
    public ServerMode set(@PathVariable(value = "mode") ServerMode mode)
    {
    	systemSetting.setSystemMode(mode);
        return mode;
    }


}
