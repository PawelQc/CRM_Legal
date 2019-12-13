package pl.qceyco.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.qceyco.client.additionalInfo.AdditionalInfoClient;

import javax.persistence.*;

@Entity(name = "xxx_client")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "additional_info_id")
    private AdditionalInfoClient additionalInfo;

}