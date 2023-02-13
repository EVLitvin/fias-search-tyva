package evlitvin.fias_search_tyva.dao;

import evlitvin.fias_search_tyva.entity.Address;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDao {

    String sqlQueryUsePgTrgm = """
            SELECT aao.objectid    AS objectId,
                   aah.parentobjid AS parentObjId,
                   aao.typename    AS streetType,
                   aao.name        AS streetName,
                   aao.isactual    AS streetActualStatus,
                   aao.isactive    AS streetActiveStatus,
                   aah.path        AS pathToAddress
            FROM tyva_schema.as_addr_obj aao
                     FULL JOIN tyva_schema.as_adm_hierarchy aah ON aao.objectid = aah.objectid
            WHERE aao.name ILIKE '%' || ? || '%'
              AND aao.isactive = 1
              AND aao.isactual = 1;""";

    List<Address> findAddressBySqlQueryUsePgTrgm(String addressDescription);

    String sqlQueryUseWebsearchToTsquery = """
            SELECT aao.objectid    AS objectId,
                   aah.parentobjid AS parentObjId,
                   aao.typename    AS streetType,
                   aao.name        AS streetName,
                   aao.isactual    AS streetActualStatus,
                   aao.isactive    AS streetActiveStatus,
                   aah.path        AS pathToAddress
            FROM tyva_schema.as_addr_obj aao
                     FULL JOIN tyva_schema.as_adm_hierarchy aah ON aao.objectid = aah.objectid
            WHERE aao.name @@ websearch_to_tsquery(?)
                AND aao.isactive = 1
                AND aao.isactual = 1;""";

    List<Address> findAddressBySqlQueryUseWebsearchToTsquery(String addressDescription);

}
