package pl.qceyco.app.client.naturalPerson;

import org.springframework.stereotype.Service;

@Service
public class ClientNaturalPersonService {

    private final ClientNaturalPersonRepository clientNaturalPersonRepository;

    public ClientNaturalPersonService(ClientNaturalPersonRepository clientNaturalPersonRepository) {
        this.clientNaturalPersonRepository = clientNaturalPersonRepository;
    }

    public void save(ClientNaturalPerson clientNaturalPerson) {
        clientNaturalPersonRepository.save(clientNaturalPerson);
    }

    public ClientNaturalPerson findClientById(Long clientId) {
        return clientNaturalPersonRepository.findFirstById(clientId);
    }


}
