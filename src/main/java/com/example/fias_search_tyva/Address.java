package com.example.fias_search_tyva;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String typeName;
    private String name;
    private long objectID;
    private String objectGUID;
}
