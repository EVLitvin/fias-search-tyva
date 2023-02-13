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
        address.setParentObjId(rs.getLong("parentObjId"));
        address.setStreetType(rs.getString("streetType"));
        address.setStreetName(rs.getString("streetName"));
        address.setStreetActualStatus(rs.getByte("streetActualStatus"));
        address.setStreetActiveStatus(rs.getByte("streetActiveStatus"));
        address.setPathToAddress(rs.getString("pathToAddress"));
        return address;
    }
}
