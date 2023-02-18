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
        address.setAddress(rs.getString("address"));
        address.setObjectType(rs.getString("objectType"));
        address.setObjectName(rs.getString("objectName"));
        address.setObjectId(rs.getLong("objectId"));
        address.setObjectGUID(rs.getString("objectGUID"));
        address.setObjectParent(rs.getLong("objectParent"));
        address.setObjectActiveStatus(rs.getByte("objectActiveStatus"));
        address.setObjectActualStatus(rs.getByte("objectActualStatus"));
        address.setObjectLevel(rs.getByte("objectLevel"));
        address.setPathToObject(rs.getString("pathToObject"));
        return address;
    }
}
