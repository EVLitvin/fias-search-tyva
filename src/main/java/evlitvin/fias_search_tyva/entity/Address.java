package evlitvin.fias_search_tyva.entity;

import lombok.*;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private Long objectId;
    private String address;
    private String textsearch_index_column;

}
