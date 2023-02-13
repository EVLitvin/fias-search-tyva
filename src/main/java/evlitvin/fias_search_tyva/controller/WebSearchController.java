package evlitvin.fias_search_tyva.controller;

import evlitvin.fias_search_tyva.entity.Address;
import evlitvin.fias_search_tyva.service.AddressService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class WebSearchController {

    final AddressService addressService;

    public WebSearchController(AddressService jdbcAddressRepository) {
        this.addressService = jdbcAddressRepository;
    }

    @GetMapping("/websearch")
    public String showIndexPage() {
        return "websearch";
    }

    @GetMapping(path = "/websearch/websearch-to-tsquery")
    public String showIndexPageWithWebsearchToTsquerySearchResult(String websearchToTsqueryAddressDescription, Model model) {
        List<Address> websearchToTsqueryTrgmAddressList = addressService.findAddressBySqlQueryUseWebsearchToTsquery(websearchToTsqueryAddressDescription);
        model.addAttribute("websearchToTsqueryTrgmAddressList", websearchToTsqueryTrgmAddressList);
        return "websearch";
    }

    @ResponseBody
    @GetMapping(path = "/api/websearch/{websearchToTsqueryAddressDescription}", produces = {"application/json", "text/xml"})
    public List<Address> findAddressByWebsearchToTsqueryDescription(@PathVariable("websearchToTsqueryAddressDescription") String websearchToTsqueryAddressDescription) {
        return addressService.findAddressBySqlQueryUseWebsearchToTsquery(websearchToTsqueryAddressDescription);
    }

}
