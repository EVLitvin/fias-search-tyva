package evlitvin.fias_search_tyva.dao;

import evlitvin.fias_search_tyva.entity.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressDao {

    List<Address> findAdmHierarchyAddress(String sqlQueryUsePgTrgm);

    String sqlQueryUsePgTrgm = "select id, text from tyva_schema.as_adm_addr_str where text ilike '%' || ? || '%' order by text limit 20;";

    List<Address> findAdmHierarchyAddressUseWebsearch(String sqlQueryUseWebsearchToTsquery);

    String sqlQueryUseWebsearchToTsquery = "select id, text from tyva_schema.as_adm_addr_str where textsearch_index_column @@ websearch_to_tsquery('russian', ?) order by text limit 20;";

    List<Address> findSelect2(String filter);

    String sqlSelect2 = "select id, text from tyva_schema.as_adm_addr_str where textsearch_index_column @@ websearch_to_tsquery('russian', ?) order by text limit 20;";
}
