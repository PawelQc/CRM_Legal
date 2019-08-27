package pl.qceyco.app.client.naturalPerson;

import org.springframework.stereotype.Service;

@Service
public class ClientNaturalPersonService {

    private final ClientNaturalPersonRepository clientNaturalPersonRepository;

    public ClientNaturalPersonService(ClientNaturalPersonRepository clientNaturalPersonRepository) {
        this.clientNaturalPersonRepository = clientNaturalPersonRepository;
    }

    void save(ClientNaturalPerson clientNaturalPerson) {
        clientNaturalPersonRepository.save(clientNaturalPerson);
    }

    ClientNaturalPerson findClientById(Long clientId) {
        return clientNaturalPersonRepository.findFirstById(clientId);
    }


}
