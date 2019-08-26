package pl.qceyco.app.client.legalPerson;

import org.springframework.stereotype.Service;

@Service
public class ClientLegalPersonService {

    private final ClientLegalPersonRepository clientLegalPersonRepository;

    public ClientLegalPersonService(ClientLegalPersonRepository clientLegalPersonRepository) {
        this.clientLegalPersonRepository = clientLegalPersonRepository;
    }

    public void save(ClientLegalPerson clientLegalPerson) {
        clientLegalPersonRepository.save(clientLegalPerson);
    }

    public ClientLegalPerson findClientById(Long clientId) {
        return clientLegalPersonRepository.findFirstById(clientId);
    }

}
