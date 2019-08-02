package pl.qceyco.app.client;

import pl.qceyco.app.client.additionalInfo.AdditionalInfoClient;

import javax.persistence.*;

@Entity(name = "xxx_client")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Client {
    //TODO czy klasa musi abstrakcyjna? jak jest abstrakcyjna - jest problem przy dodawaniu clienta w legalcase

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //TODO czy klucz musi być auto? jak jest identity pojawia się błąd

    @OneToOne
    @JoinColumn(name = "additional_info_id")
    private AdditionalInfoClient additionalInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdditionalInfoClient getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(AdditionalInfoClient additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", additionalInfo=" + additionalInfo +
                '}';
    }
}