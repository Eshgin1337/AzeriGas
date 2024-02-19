package com.abonents.abon;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorldController {
    
    // Get HTTP method on http://localhost:8081/hello

    @GetMapping("/hello")
    public String helloMessageString() {
        return "Hello world";
    }
}
