package com.example.helloworld.helloworld.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/Welcome")
public class RequestController {
    @GetMapping("/v1")
    public String Welcome() {
        return "Hello" + "Executed as a Default Code";
    }

    @GetMapping("/v2/{name}")
    public String WelcomePV(@PathVariable("name") String name) {
        return "Hello " + name + "Executing via Path Variable";
    }

    @GetMapping("/v3")
    public String WelcomeQP(@RequestParam("name") String name) {
        return "Hello " + name + "Executing via Request Param";
    }

    @GetMapping("/v4")
    public String WelcomeRH(@RequestHeader("name") String name) {
        return "Hello " + name + "Executing via Request Header";
    }
    
}
