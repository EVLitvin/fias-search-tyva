package evlitvin.fias_search_tyva.dao;

import evlitvin.fias_search_tyva.entity.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDao {

    List<Address> findAddressBySqlQueryUsePgTrgm(String addressDescription);

    String sqlQueryUsePgTrgm = "WITH RECURSIVE full_address(objectid, address, textsearch_index_column, parentobjid) AS (\n" +
            "SELECT aao1.objectid, aao1.address, aao1.textsearch_index_column, amh1.parentobjid\n" +
            "FROM tyva_schema.as_addr_obj aao1\n" +
            "   INNER JOIN tyva_schema.as_mun_hierarchy amh1 ON aao1.objectid = amh1.objectid\n" +
            "   UNION ALL\n" +
            "SELECT aao2.objectid, full_address.address || ' ' || aao2.address, aao2.textsearch_index_column, amh2.parentobjid\n" +
            "FROM tyva_schema.as_addr_obj aao2\n" +
            "INNER JOIN tyva_schema.as_mun_hierarchy amh2 ON aao2.objectid = amh2.objectid\n" +
            "             INNER JOIN full_address ON full_address.parentobjid = amh2.objectid\n" +
            "SELECT DISTINCT objectid, address, textsearch_index_column, parentobjid\n" +
            "FROM full_address\n" +
            "WHERE address ILIKE '%' || ? || '%'\n" +
            "ORDER BY address DESC LIMIT 100;";

    List<Address> findAddressBySqlQueryUseWebsearchToTsquery(String addressDescription);

    String sqlQueryUseWebsearchToTsquery = "WITH RECURSIVE full_address(objectid, address, textsearch_index_column, parentobjid) AS (\n" +
            "SELECT aao1.objectid, aao1.address, aao1.textsearch_index_column, amh1.parentobjid\n" +
            "FROM tyva_schema.as_addr_obj aao1\n" +
            "    INNER JOIN tyva_schema.as_mun_hierarchy amh1 ON aao1.objectid = amh1.objectid\n" +
            "        UNION ALL\n" +
            "        SELECT aao2.objectid, aao2.address, full_address.textsearch_index_column || ' ' || aao2.textsearch_index_column, amh2.parentobjid\n" +
            "        FROM tyva_schema.as_addr_obj aao2\n" +
            "            INNER JOIN tyva_schema.as_mun_hierarchy amh2 ON aao2.objectid = amh2.objectid\n" +
            "            INNER JOIN full_address ON full_address.parentobjid = amh2.objectid\n" +
            ")\n" +
            "SELECT DISTINCT objectid, address, textsearch_index_column, parentobjid\n" +
            "FROM full_address\n" +
            "WHERE textsearch_index_column @@ to_tsquery('russian', ?)\n" +
            "ORDER BY textsearch_index_column DESC LIMIT 100;";

    List<Address> findSelect2(String filter);

    String sqlSelect2 = "WITH RECURSIVE full_address(objectid, address, textsearch_index_column, parentobjid) AS (\n" +
            "    SELECT aao1.objectid, aao1.address, aao1.textsearch_index_column, amh1.parentobjid\n" +
            "    FROM tyva_schema.as_addr_obj aao1\n" +
            "             INNER JOIN tyva_schema.as_mun_hierarchy amh1 ON aao1.objectid = amh1.objectid\n" +
            "    UNION ALL\n" +
            "    SELECT aao2.objectid, full_address.address || ' ' || aao2.address, aao2.textsearch_index_column, amh2.parentobjid\n" +
            "    FROM tyva_schema.as_addr_obj aao2\n" +
            "             INNER JOIN tyva_schema.as_mun_hierarchy amh2 ON aao2.objectid = amh2.objectid\n" +
            "             INNER JOIN full_address ON full_address.parentobjid = amh2.objectid\n" +
            ")\n" +
            "SELECT DISTINCT objectid, address, textsearch_index_column, parentobjid\n" +
            "FROM full_address\n" +
            "WHERE address ILIKE '%' || ? || '%'\n" +
            "ORDER BY address DESC LIMIT 100;";
}
