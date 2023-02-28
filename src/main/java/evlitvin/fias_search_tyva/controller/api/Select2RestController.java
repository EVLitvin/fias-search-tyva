package evlitvin.fias_search_tyva.controller.api;

import evlitvin.fias_search_tyva.entity.Address;
import evlitvin.fias_search_tyva.service.AddressService;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/", produces = "application/json")
@CrossOrigin(origins = "http://localhost:7070")
public class Select2RestController {

    private final AddressService addressService;

    public Select2RestController(AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping(value = "search", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<Address> findAddress(@RequestParam(value = "term", required = false) String term) {
        return addressService.findSelect2(term);
    }

}
