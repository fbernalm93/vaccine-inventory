package vaccinationinventory.vaccine.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VaccinePerson {
    @EmbeddedId
    private VaccinePersonRelationship vaccinePersonRelationship;

    private Date vaccinationdate;

}
