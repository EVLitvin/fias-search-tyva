package evlitvin.fias_search_tyva.dao;

import evlitvin.fias_search_tyva.entity.Address;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AddressMapper implements RowMapper<Address> {
    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        Address address = new Address();
        address.setObjectId(rs.getLong("objectId"));
        address.setAddress(rs.getString("address"));
        address.setTextsearch_index_column(rs.getString("textsearch_index_column"));
        return address;
    }
}
