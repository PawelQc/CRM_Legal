package pl.qceyco.app.client;

import org.springframework.stereotype.Service;
import pl.qceyco.app.client.additionalInfo.AdditionalInfoClientRepository;
import pl.qceyco.app.project.Project;
import pl.qceyco.app.project.ProjectRepository;

import java.util.List;

@Service
public class ClientService {

    // Tylko jako ciekawostka
    // Jedna z wersji metodologii używania serwisów mówi, że serwis komunikuje się
    // z repozytorium za które odpowiada oraz innymi serwisami
    // Ale tak jest dobrze, to tylko ciekawostka
    // I tak wszystko zależy od założeń projektu na którym będziesz pracować
    private final ProjectRepository projectRepository;
    private final ClientRepository clientRepository;
    private final AdditionalInfoClientRepository additionalInfoClientRepository;

    public ClientService(ClientRepository clientRepository, AdditionalInfoClientRepository additionalInfoClientRepository, ProjectRepository projectRepository) {
        this.clientRepository = clientRepository;
        this.additionalInfoClientRepository = additionalInfoClientRepository;
        this.projectRepository = projectRepository;
    }

    List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    Project getAnyProjectByClientId(Long clientId) {
        return projectRepository.findFirstByClientId(clientId);
    }

    void deleteClient(Long clientId) {
        Client clientToDelete = clientRepository.findFirstById(clientId);
        clientRepository.deleteById(clientId);
        if (clientToDelete.getAdditionalInfo() != null) {
            Long infoId = clientToDelete.getAdditionalInfo().getId();
            additionalInfoClientRepository.deleteById(infoId);
        }
    }

}
