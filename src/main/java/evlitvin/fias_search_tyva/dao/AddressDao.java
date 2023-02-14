package evlitvin.fias_search_tyva.dao;

import evlitvin.fias_search_tyva.entity.Address;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDao {

    String sqlQueryUsePgTrgm = "SELECT aao.typename    AS objectType,\n" +
            "       aao.name        AS objectName,\n" +
            "       aao.objectid    AS objectID,\n" +
            "       aao.objectguid  AS objectGUID,\n" +
            "       amh.parentobjid AS objectParent,\n" +
            "       aao.isactive    AS objectActiveStatus,\n" +
            "       aao.isactual    AS objectActualStatus,\n" +
            "       aao.level       AS objectLevel,\n" +
            "       amh.path        AS pathToObject\n" +
            "FROM tyva_schema.as_addr_obj aao\n" +
            "         FULL JOIN tyva_schema.as_mun_hierarchy amh ON aao.objectid = amh.objectid\n" +
            "WHERE aao.name ILIKE '%' || ? || '%'\n" +
            "  AND (aao.isactive = 1 AND aao.isactual = 1)\n" +
            "ORDER BY aao.level;";

    List<Address> findAddressBySqlQueryUsePgTrgm(String addressDescription);

    String sqlQueryUseWebsearchToTsquery = "SELECT aao.typename    AS objectType,\n" +
            "       aao.name        AS objectName,\n" +
            "       aao.objectid    AS objectID,\n" +
            "       aao.objectguid  AS objectGUID,\n" +
            "       amh.parentobjid AS objectParent,\n" +
            "       aao.isactive    AS objectActiveStatus,\n" +
            "       aao.isactual    AS objectActualStatus,\n" +
            "       aao.level       AS objectLevel,\n" +
            "       amh.path        AS pathToObject\n" +
            "FROM tyva_schema.as_addr_obj aao\n" +
            "         FULL JOIN tyva_schema.as_mun_hierarchy amh ON aao.objectid = amh.objectid\n" +
            "WHERE aao.name @@ websearch_to_tsquery(?)\n" +
            "  AND (aao.isactive = 1 AND aao.isactual = 1)\n" +
            "ORDER BY aao.level;";

    List<Address> findAddressBySqlQueryUseWebsearchToTsquery(String addressDescription);

}
