package evlitvin.fias_search_tyva.controller;

import evlitvin.fias_search_tyva.entity.Address;
import evlitvin.fias_search_tyva.service.AddressService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexController {

    final AddressService jdbcAddressRepository;

    public IndexController(AddressService jdbcAddressRepository) {
        this.jdbcAddressRepository = jdbcAddressRepository;
    }

    @GetMapping("/")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping(path = "/search")
    public String showIndexPageWithSearchResult(String addressDescription, Model model) {
        List<Address> addressList = jdbcAddressRepository.findAddressByDescription(addressDescription);
        model.addAttribute("addressList", addressList);
        return "index";
    }

    @GetMapping(path = "/api/search/{addressDescription}", produces = {"application/json", "text/xnl"})
    @ResponseBody
    public List<Address> findAddressByDescription(@PathVariable("addressDescription") String addressDescription) {
        return jdbcAddressRepository.findAddressByDescription(addressDescription);
    }

}
