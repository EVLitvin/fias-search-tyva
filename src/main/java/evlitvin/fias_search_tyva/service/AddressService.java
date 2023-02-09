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

    private final JdbcTemplate jdbcTemplate;

    public AddressService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

        private String sqlFindByDescription = "select tyva_schema.as_addr_obj.typename,\n" +
            "       tyva_schema.as_addr_obj.name\n" +
            "from tyva_schema.as_addr_obj\n" +
            "where tyva_schema.as_addr_obj.name ilike ? \n" +
            "  and tyva_schema.as_addr_obj.opertypeid = 1\n" +
            "  and tyva_schema.as_addr_obj.isactive = 1;";

    @Override
    public List<Address> findAddressByDescription(String addressDescription) {
        List<Address> searchResult = jdbcTemplate.query(
                sqlFindByDescription,
                this::mapRowToAddress,
                addressDescription
        );
        return searchResult;
    }

    private Address mapRowToAddress(ResultSet row, int rowNum)
            throws SQLException {
        return new Address(
                row.getString("typeName"),
                row.getString("name"));
//                row.getLong("objectID"),
//                row.getString("objectGUID"));
    }
}
