package com.ncc.controller.controllerVrestcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {

    @GetMapping("/rest/hello")
    public String hello() {
        return "Hello, world!"; // Trả về dữ liệu JSON hoặc XML
    }
}
