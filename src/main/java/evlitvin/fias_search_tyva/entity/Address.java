package evlitvin.fias_search_tyva.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String streetType;
    private String streetName;
    private Byte streetActualStatus;
    private Byte streetActiveStatus;

}
