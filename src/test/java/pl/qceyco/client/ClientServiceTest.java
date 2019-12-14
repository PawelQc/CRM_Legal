//package pl.qceyco.client;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import pl.qceyco.client.additionalInfo.AdditionalInfoClient;
//import pl.qceyco.client.additionalInfo.AdditionalInfoClientRepository;
//import pl.qceyco.client.legalPerson.ClientLegalPerson;
//import pl.qceyco.client.naturalPerson.ClientNaturalPerson;
//import pl.qceyco.project.ProjectRepository;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.mock;
//
//class ClientServiceTest {
//    private ClientService clientService;
//
//    @BeforeEach
//    void setUp() {
//        ProjectRepository projectRepository = mock(ProjectRepository.class);
//        ClientRepository clientRepository = mock(ClientRepository.class);
//        AdditionalInfoClientRepository additionalInfoClientRepository = mock(AdditionalInfoClientRepository.class);
//        clientService = new ClientService(clientRepository, additionalInfoClientRepository, projectRepository);
//    }
//
//    @Test
//    void givenNonEmptyDB_whenTestGetAllClients_thenResultIs2() {
//        List<Client> clients = prepareClientData();
//        given(clientService.getAllClients()).willReturn(clients);
//        List<Client> allClients = clientService.getAllClients();
//        assertThat(allClients, hasSize(2));
//    }
//
//    @Test
//    void givenEmptyDB_whenTestGetAllClients_thenResultIs0() {
//        given(clientService.getAllClients()).willReturn(Collections.emptyList());
//        List<Client> allClients = clientService.getAllClients();
//        assertThat(allClients, is(empty()));
//    }
//
//    private List<Client> prepareClientData() {
//        AdditionalInfoClient additionalInfoClient1 = AdditionalInfoClient.builder()
//                .id(1L)
//                .nip("9318235090")
//                .address("some address")
//                .bankAccount("77249000056420077578477743")
//                .email("kulczyk.j@gmail.com")
//                .hourlyRateIsCharged(550)
//                .phoneNumber("879876543")
//                .build();
//        AdditionalInfoClient additionalInfoClient2 = AdditionalInfoClient.builder()
//                .id(2L)
//                .nip("7818235090")
//                .address("some address")
//                .bankAccount("86106000058780488477816280")
//                .email("tarcz.and@onet.pl")
//                .hourlyRateIsCharged(660)
//                .phoneNumber("564432123")
//                .build();
//
//        Client client1 = ClientLegalPerson.builder()
//                .companyName("Some sp. z o.o.")
//                .id(1L)
//                .additionalInfo(additionalInfoClient1)
//                .build();
//        Client client2 = ClientNaturalPerson.builder()
//                .id(2L)
//                .firstName("Adam")
//                .lastName("Kowalski")
//                .additionalInfo(additionalInfoClient2)
//                .build();
//        return Arrays.asList(client1, client2);
//    }
//}