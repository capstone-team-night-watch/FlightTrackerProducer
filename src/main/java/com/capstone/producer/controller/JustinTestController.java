package com.capstone.producer.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JustinTestController {

    @CrossOrigin
    @RequestMapping(
            path = "/hello_world",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String helloWorld(){
        return "Hello, World!";
    }
}
