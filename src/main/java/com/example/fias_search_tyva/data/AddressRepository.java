package com.example.fias_search_tyva.data;

import com.example.fias_search_tyva.Address;

import java.util.List;

public interface AddressRepository {
    List<Address> findAddressByDescription(String description);

}
