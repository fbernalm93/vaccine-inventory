package vaccinationinventory.vaccine.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VaccinePersonRelationship implements Serializable {
    private Integer idVaccine;
    private String idPerson;
    private Integer vaccineDoses;
}
