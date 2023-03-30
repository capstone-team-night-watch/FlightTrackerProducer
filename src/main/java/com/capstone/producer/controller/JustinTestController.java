package com.capstone.producer.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class JustinTestController {

    @RequestMapping(
            path = "/hello_world",
            method = RequestMethod.GET
    )
    @ResponseBody
    public String helloWorld(){
        return "Hello, World!";
    }
}
