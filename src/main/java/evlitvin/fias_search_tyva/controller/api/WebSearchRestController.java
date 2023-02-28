package evlitvin.fias_search_tyva.controller.api;

import evlitvin.fias_search_tyva.entity.Address;
import evlitvin.fias_search_tyva.service.AddressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/search", produces = "application/json")
@CrossOrigin(origins = "http://localhost:7070")
public class WebSearchRestController {

    private final AddressService addressService;

    public WebSearchRestController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping(path = "/websearch/{filter}", produces = {"application/json", "text/xml"})
    public List<Address> findAddressByWebsearchToTsqueryDescription(@PathVariable("filter") String filter) {
        return addressService.findAdmHierarchyAddressUseWebsearch(filter);
    }

}
