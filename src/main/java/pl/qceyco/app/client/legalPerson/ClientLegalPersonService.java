package pl.qceyco.app.client.legalPerson;

import org.springframework.stereotype.Service;

@Service
public class ClientLegalPersonService {

    private final ClientLegalPersonRepository clientLegalPersonRepository;

    public ClientLegalPersonService(ClientLegalPersonRepository clientLegalPersonRepository) {
        this.clientLegalPersonRepository = clientLegalPersonRepository;
    }

    void save(ClientLegalPerson clientLegalPerson) {
        clientLegalPersonRepository.save(clientLegalPerson);
    }

    ClientLegalPerson findClientById(Long clientId) {
        return clientLegalPersonRepository.findFirstById(clientId);
    }

}
