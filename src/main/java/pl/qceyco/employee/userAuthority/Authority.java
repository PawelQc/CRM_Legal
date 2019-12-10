package pl.qceyco.employee.userAuthority;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "authority")
@Data
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private AuthorityType name;

}
