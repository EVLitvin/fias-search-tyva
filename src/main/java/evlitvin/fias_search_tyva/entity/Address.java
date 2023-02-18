package evlitvin.fias_search_tyva.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor

@AllArgsConstructor
public class Address {

    private String address;
    private String objectType;
    private String objectName;
    private Long objectId;
    private String objectGUID;
    private Long objectParent;
    private Byte objectActiveStatus;
    private Byte objectActualStatus;
    private Byte objectLevel;
    private String pathToObject;
}
