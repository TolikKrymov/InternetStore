package com.tolikkrymov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping(value = "/")
    String index() {
        return "main-page";
    }
}
