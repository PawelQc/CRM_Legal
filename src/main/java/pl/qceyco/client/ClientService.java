package pl.qceyco.client;

import org.springframework.stereotype.Service;
import pl.qceyco.client.additionalInfo.AdditionalInfoClientRepository;
import pl.qceyco.project.Project;
import pl.qceyco.project.ProjectRepository;

import java.util.List;

@Service
public class ClientService {

    private final ProjectRepository projectRepository;
    private final ClientRepository clientRepository;
    private final AdditionalInfoClientRepository additionalInfoClientRepository;

    public ClientService(ClientRepository clientRepository, AdditionalInfoClientRepository additionalInfoClientRepository, ProjectRepository projectRepository) {
        this.clientRepository = clientRepository;
        this.additionalInfoClientRepository = additionalInfoClientRepository;
        this.projectRepository = projectRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Project getAnyProjectByClientId(Long clientId) {
        return projectRepository.findFirstByClientId(clientId);
    }

    public void deleteClient(Long clientId) {
        Client clientToDelete = clientRepository.findFirstById(clientId);
        clientRepository.deleteById(clientId);
        if (clientToDelete.getAdditionalInfo() != null) {
            Long infoId = clientToDelete.getAdditionalInfo().getId();
            additionalInfoClientRepository.deleteById(infoId);
        }
    }

}
