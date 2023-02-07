package com.example.fias_search_tyva.api;

import com.example.fias_search_tyva.Address;
import com.example.fias_search_tyva.data.JdbcAddressRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/",
        produces = {"application/json", "text/xml"})
@CrossOrigin(origins = "http://localhost:7070")
public class AddressRestController {

    private final JdbcAddressRepository jdbcAddressRepository;

    public AddressRestController(JdbcAddressRepository jdbcAddressRepository) {
        this.jdbcAddressRepository = jdbcAddressRepository;
    }

    @GetMapping("/search/{description}")
    public List<Address> findAddressByDescription(@PathVariable("description") String description) {
        return jdbcAddressRepository.findAddressByDescription(description);
    }
}
