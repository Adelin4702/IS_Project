package com.example.helloworld;

import com.example.helloworld.messages.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @GetMapping("")
    public String showHomePage(){
        return "index";
    }


}