package evlitvin.fias_search_tyva.service;

import evlitvin.fias_search_tyva.dao.AddressDao;
import evlitvin.fias_search_tyva.dao.AddressMapper;
import evlitvin.fias_search_tyva.entity.Address;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class AddressService implements AddressDao {

    private final JdbcTemplate jdbcTemplate;

    private final AddressMapper addressMapper;

    public AddressService(JdbcTemplate jdbcTemplate, AddressMapper addressMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.addressMapper = addressMapper;
    }

    @Override
    public List<Address> findAddressByDescription(String addressDescription) {
        List<Address> addressList = jdbcTemplate.query(sqlFindByDescription, addressMapper, addressDescription);
        return addressList;
    }

}
