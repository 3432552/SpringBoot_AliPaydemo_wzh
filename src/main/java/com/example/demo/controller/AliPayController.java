package com.example.demo.controller;

import com.example.demo.domain.entity.Orders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AliPayController {
    @PostMapping("/etr")
    @ResponseBody
    public String hge(@RequestBody Orders orders) {
        System.out.println("主体内容:" + orders.toString());
        return "ok";
    }

    @RequestMapping("/mes")
    public String sf() {
        return "pay";
    }
}
