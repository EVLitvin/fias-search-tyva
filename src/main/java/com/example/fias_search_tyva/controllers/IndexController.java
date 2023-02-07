package com.example.fias_search_tyva.controllers;

import com.example.fias_search_tyva.Address;
import com.example.fias_search_tyva.data.JdbcAddressRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = {"/", "/search"})
public class IndexController {

    final JdbcAddressRepository jdbcAddressRepository;

    public IndexController(JdbcAddressRepository jdbcAddressRepository) {
        this.jdbcAddressRepository = jdbcAddressRepository;
    }

    @GetMapping
    public String showIndexPage(Model model, String name) {
        List<Address> addressList = jdbcAddressRepository.findAddressByDescription(name);
        model.addAttribute("addressList", addressList);
        return "index";
    }

}
