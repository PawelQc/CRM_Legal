package pl.qceyco.client.additionalInfo;

import org.springframework.stereotype.Service;
import pl.qceyco.client.Client;
import pl.qceyco.client.ClientRepository;

@Service
public class AdditionalInfoClientService {

    private final AdditionalInfoClientRepository additionalInfoClientRepository;
    private final ClientRepository clientRepository;

    public AdditionalInfoClientService(AdditionalInfoClientRepository additionalInfoClientRepository, ClientRepository clientRepository) {
        this.additionalInfoClientRepository = additionalInfoClientRepository;
        this.clientRepository = clientRepository;
    }

    Client getClientById(Long clientId) {
        return clientRepository.findFirstById(clientId);
    }

    AdditionalInfoClient getAdditionalInfoById(Long infoId) {
        return additionalInfoClientRepository.findFirstById(infoId);
    }

    void saveAdd(Long clientId, AdditionalInfoClient additionalInfoClient) {
        additionalInfoClientRepository.save(additionalInfoClient);
        Client client = clientRepository.findFirstById(clientId);
        client.setAdditionalInfo(additionalInfoClient);
        clientRepository.save(client);
    }

    void saveUpdate(AdditionalInfoClient additionalInfoClient) {
        additionalInfoClientRepository.save(additionalInfoClient);
    }

    Client getClientByInfoId(Long infoId) {
        return clientRepository.findFirstByAdditionalInfo_Id(infoId);
    }

}
