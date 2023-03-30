package com.capstone.producer.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JustinTestController {

    @CrossOrigin
    @RequestMapping(
            path = "/hello_world",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public String helloWorld(){
        return "Hello, World!";
    }
}
