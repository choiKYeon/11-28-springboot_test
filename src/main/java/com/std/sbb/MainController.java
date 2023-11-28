package com.std.sbb;


import org.springframework.web.bind.annotation.GetMapping;

public class MainController {
    @GetMapping("/")
    public String root(){
        return "redirect:/article/list";
    }
}
