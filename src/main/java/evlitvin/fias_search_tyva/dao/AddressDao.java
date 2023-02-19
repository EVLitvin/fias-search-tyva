package evlitvin.fias_search_tyva.dao;

import evlitvin.fias_search_tyva.entity.Address;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDao {

    String sqlQueryUsePgTrgm = "WITH RECURSIVE full_address(address, typename, name, objectid, objectguid, parentobjid, isactive, isactual, level, path) AS (\n" +
            "    SELECT aao1.typename || ' ' || aao1.name AS address, aao1.typename, aao1.name, aao1.objectid, aao1.objectguid,\n" +
            "           amh1.parentobjid, aao1.isactive, aao1.isactual, aao1.level, amh1.path\n" +
            "    FROM tyva_schema.as_addr_obj aao1\n" +
            "             INNER JOIN tyva_schema.as_mun_hierarchy amh1 ON aao1.objectid = amh1.objectid\n" +
            "    WHERE aao1.name ILIKE '%' || ? || '%' -- AND aao1.isactive = 1 AND aao1.isactual = 1 AND amh1.isactive = 1\n" +
            "    UNION ALL\n" +
            "    -- SELECT aao2.typename || ' ' || aao2.name || ' ' || full_address.address AS address, aao2.typename, aao2.name,\n" +
            "    SELECT full_address.address || ' ' || aao2.typename || ' ' || aao2.name AS address, aao2.typename, aao2.name,\n" +
            "           aao2.objectid, aao2.objectguid, amh2.parentobjid, aao2.isactive, aao2.isactual, aao2.level, amh2.path\n" +
            "    FROM tyva_schema.as_addr_obj aao2\n" +
            "             INNER JOIN tyva_schema.as_mun_hierarchy amh2 ON aao2.objectid = amh2.objectid\n" +
            "             INNER JOIN full_address ON full_address.parentobjid = amh2.objectid\n" +
            "    -- WHERE aao2.isactive = 1 AND aao2.isactual = 1 AND amh2.isactive = 1\n" +
            ")\n" +
            "SELECT DISTINCT address, typename AS objectType, name AS objectName, objectid AS objectId,\n" +
            "                objectguid AS objectGUID, parentobjid AS objectParent, isactive AS objectActiveStatus,\n" +
            "                isactual AS objectActualStatus, level AS objectLevel, path AS pathToObject\n" +
            "FROM full_address\n" +
            "ORDER BY level, address DESC LIMIT 50;";

    List<Address> findAddressBySqlQueryUsePgTrgm(String addressDescription);

    String sqlQueryUseWebsearchToTsquery = "WITH RECURSIVE full_address(address, typename, name, objectid, objectguid, parentobjid, isactive, isactual, level, path) AS (\n" +
            "    SELECT aao1.typename || ' ' || aao1.name AS address, aao1.typename, aao1.name, aao1.objectid, aao1.objectguid,\n" +
            "    amh1.parentobjid, aao1.isactive, aao1.isactual, aao1.level, amh1.path\n" +
            "    FROM tyva_schema.as_addr_obj aao1\n" +
            "    INNER JOIN tyva_schema.as_mun_hierarchy amh1 ON aao1.objectid = amh1.objectid\n" +
            "    WHERE aao1.name @@ websearch_to_tsquery(?) -- AND aao1.isactive = 1 AND aao1.isactual = 1 AND amh1.isactive = 1\n" +
            "    UNION ALL\n" +
            "    -- SELECT aao2.typename || ' ' || aao2.name || ' ' || full_address.address AS address, aao2.typename, aao2.name,\n" +
            "    SELECT full_address.address || ' ' || aao2.typename || ' ' || aao2.name AS address, aao2.typename, aao2.name,\n" +
            "    aao2.objectid, aao2.objectguid, amh2.parentobjid, aao2.isactive, aao2.isactual, aao2.level, amh2.path\n" +
            "    FROM tyva_schema.as_addr_obj aao2\n" +
            "    INNER JOIN tyva_schema.as_mun_hierarchy amh2 ON aao2.objectid = amh2.objectid\n" +
            "    INNER JOIN full_address ON full_address.parentobjid = amh2.objectid\n" +
            "    -- WHERE aao2.isactive = 1 AND aao2.isactual = 1 AND amh2.isactive = 1\n" +
            "            )\n" +
            "    SELECT DISTINCT address, typename AS objectType, name AS objectName, objectid AS objectId,\n" +
            "    objectguid AS objectGUID, parentobjid AS objectParent, isactive AS objectActiveStatus,\n" +
            "    isactual AS objectActualStatus, level AS objectLevel, path AS pathToObject\n" +
            "    FROM full_address\n" +
            "    ORDER BY level, address DESC LIMIT 50;";

    List<Address> findAddressBySqlQueryUseWebsearchToTsquery(String addressDescription);

}
