package com.mayy5.admin.controller;

import com.mayy5.admin.model.res.UserRTO;
import com.mayy5.admin.service.MarketAgentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MarketAgentController {

    private final MarketAgentService marketAgentService;

    @GetMapping(value = "/v1/market_agents/new")
    public String createForm(Model model) {
        model.addAttribute("marketAgentForm", new MarketAgentForm());
        return "ok";
    }

    @PostMapping(value = "/v1/market_agents/new")
    public String create(@RequestBody @Valid UserRTO userRTO, @Valid MarketAgentForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "fail";
        }

        String userId = userRTO.getId();

        return "ok";
    }




}

