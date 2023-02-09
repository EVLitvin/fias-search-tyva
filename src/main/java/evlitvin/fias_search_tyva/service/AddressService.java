package evlitvin.fias_search_tyva.service;

import evlitvin.fias_search_tyva.dao.AddressDao;
import evlitvin.fias_search_tyva.entity.Address;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class AddressService implements AddressDao {

    private String sqlFindByDescription = "select tyva_schema.as_addr_obj.typename, tyva_schema.as_addr_obj.name, tyva_schema.as_addr_obj.objectid, tyva_schema.as_addr_obj.objectguid from tyva_schema.as_addr_obj where tyva_schema.as_addr_obj.name ilike ?";

    private final JdbcTemplate jdbcTemplate;

    public AddressService(JdbcTemplate jdbcTemplate) {
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
