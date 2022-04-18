package com.yash.project.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorldController {


    //method to return "hellow world"
    //@RequestMapping(method = RequestMethod.GET,path = "/hello-world")
    @GetMapping("/")
    public String helloWorld(){


        return "Hello To Spring-Boot";
    }
}