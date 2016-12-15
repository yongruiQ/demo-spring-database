package com.example.controller;

import com.example.domain.Card;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Louie Qin on 24/11/16.
 */
@Controller
public class DemoController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public Card json(){
        Card card = new Card();
        card.setTitle("JSON example");
        return card;
    }
}
