package evlitvin.fias_search_tyva.controller;

import evlitvin.fias_search_tyva.entity.Address;
import evlitvin.fias_search_tyva.service.AddressService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexController {

    final AddressService addressService;

    public IndexController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping(path = "/search/pg-trgm")
    public String showIndexPageWithPgTrgmSearchResult(String pgTrgmAddressDescription, Model model) {
        List<Address> pgTrgmAddressList = addressService.findAdmHierarchyAddress(pgTrgmAddressDescription);
        model.addAttribute("pgTrgmAddressList", pgTrgmAddressList);
        return "index";
    }

}
