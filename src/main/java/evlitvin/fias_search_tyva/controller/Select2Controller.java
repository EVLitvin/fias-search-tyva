package evlitvin.fias_search_tyva.controller;

import evlitvin.fias_search_tyva.entity.Address;
import evlitvin.fias_search_tyva.service.AddressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class Select2Controller {

    private final AddressService addressService;

    public Select2Controller(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("select2")
    public String showSelect2Page() {
        return "select2";
    }

    @ResponseBody
    @GetMapping(path = "/api/search/select2/{term}", produces = "application/json")
    public List<Address> findAddress(@PathVariable(value = "term", required = false) String term) {
        return addressService.findSelect2(term);
    }

}
