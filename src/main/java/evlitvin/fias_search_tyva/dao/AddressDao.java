package evlitvin.fias_search_tyva.dao;

import evlitvin.fias_search_tyva.entity.Address;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDao {

//    String sqlQueryUsePgTrgm =

    String sqlQueryUsePgTrgm = "with recursive full_address(address, objectid, parentobjid, name) as (\n" +
            "    select aao1.typename || ' ' || aao1.name as address, aao1.objectid, amh1.parentobjid, aao1.name\n" +
            "    from tyva_schema.as_addr_obj aao1\n" +
            "             inner join tyva_schema.as_mun_hierarchy amh1 on aao1.objectid = amh1.objectid\n" +
            "    where aao1.name ilike '%' || ? || '%' -- and aao1.isactive = 1 and aao1.isactual = 1 and amh1.isactive = 1\n" +
            "    union all\n" +
            "    select full_address.address || ' ' || aao2.typename || ' ' || aao2.name as address, aao2.objectid, amh2.parentobjid, aao2.name\n" +
            "    from tyva_schema.as_addr_obj aao2\n" +
            "             inner join tyva_schema.as_mun_hierarchy amh2 on aao2.objectid = amh2.objectid\n" +
            "             inner join full_address on full_address.parentobjid = amh2.objectid\n" +
            "    -- where aao2.isactive = 1 and aao2.isactual = 1 and amh2.isactive = 1\n" +
            ")\n" +
            "select distinct address\n" +
            "from full_address\n" +
            "order by address desc;";

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
