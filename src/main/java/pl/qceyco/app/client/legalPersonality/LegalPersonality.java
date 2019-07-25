package pl.qceyco.app.client.legalPersonality;

import javax.persistence.*;

@Entity
@Table(name = "legal_personality")
public class LegalPersonality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String legalForm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLegalForm() {
        return legalForm;
    }

    public void setLegalForm(String legalForm) {
        this.legalForm = legalForm;
    }

    @Override
    public String toString() {
        return "LegalPersonality{" +
                "id=" + id +
                ", legalForm='" + legalForm + '\'' +
                '}';
    }
}
