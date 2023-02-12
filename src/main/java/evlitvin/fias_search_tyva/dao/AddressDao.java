package evlitvin.fias_search_tyva.dao;

import evlitvin.fias_search_tyva.entity.Address;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDao {

    String sqlFindByDescription = "SELECT aao.typename AS streetType, aao.name AS streetName, aao.isactual AS streetActualStatus, aao.isactive AS streetActiveStatus\n" +
            "FROM tyva_schema.as_addr_obj aao\n" +
            "WHERE aao.name ILIKE '%' || ? || '%'\n" +
            "  AND aao.isactive = 1;";

    List<Address> findAddressByDescription(String sqlFindByDescription);

}
