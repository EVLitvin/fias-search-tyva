package evlitvin.fias_search_tyva.controller;

import evlitvin.fias_search_tyva.service.AddressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

}
