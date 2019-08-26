package pl.qceyco.app.client.additionalInfo;

import org.springframework.stereotype.Service;
import pl.qceyco.app.client.Client;
import pl.qceyco.app.client.ClientRepository;

@Service
public class AdditionalInfoClientService {

    private final AdditionalInfoClientRepository additionalInfoClientRepository;
    private final ClientRepository clientRepository;

    public AdditionalInfoClientService(AdditionalInfoClientRepository additionalInfoClientRepository, ClientRepository clientRepository) {
        this.additionalInfoClientRepository = additionalInfoClientRepository;
        this.clientRepository = clientRepository;
    }

    public Client getClientById(Long clientId) {
        return clientRepository.findFirstById(clientId);
    }

    public AdditionalInfoClient getAdditionalInfoById(Long infoId) {
        return additionalInfoClientRepository.findFirstById(infoId);
    }

    public void saveAdd(Long clientId, AdditionalInfoClient additionalInfoClient) {
        additionalInfoClientRepository.save(additionalInfoClient);
        Client client = clientRepository.findFirstById(clientId);
        client.setAdditionalInfo(additionalInfoClient);
        clientRepository.save(client);
    }

    public void saveUpdate(AdditionalInfoClient additionalInfoClient) {
        additionalInfoClientRepository.save(additionalInfoClient);
    }

    public Client getClientByInfoId(Long infoId) {
        return clientRepository.findFirstByAdditionalInfo_Id(infoId);
    }

}
