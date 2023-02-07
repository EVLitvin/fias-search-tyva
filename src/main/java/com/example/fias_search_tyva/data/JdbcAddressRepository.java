package com.example.fias_search_tyva.data;

import com.example.fias_search_tyva.Address;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcAddressRepository implements AddressRepository {

    private String sqlFindByDescription = "select typename, name, objectid, objectguid from as_addr_obj where name ilike ? and isactive = 1 and isactual = 1";

    private final JdbcTemplate jdbcTemplate;

    public JdbcAddressRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Address> findAddressByDescription(String description) {
        List<Address> searchResult = jdbcTemplate.query(
                sqlFindByDescription,
                this::mapRowToAddress,
                description
        );
        return searchResult;
    }

    private Address mapRowToAddress(ResultSet row, int rowNum)
            throws SQLException {
        return new Address(
                row.getString("typeName"),
                row.getString("name"),
                row.getLong("objectID"),
                row.getString("objectGUID"));
    }
}
