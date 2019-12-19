package pl.qceyco.report;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.qceyco.client.Client;
import pl.qceyco.client.ClientRepository;
import pl.qceyco.client.additionalInfo.AdditionalInfoClient;
import pl.qceyco.client.legalPerson.ClientLegalPerson;
import pl.qceyco.employee.Employee;
import pl.qceyco.employee.EmployeeRepository;
import pl.qceyco.employee.additinalInfo.AdditionalInfoEmployee;
import pl.qceyco.project.Project;
import pl.qceyco.project.ProjectRepository;
import pl.qceyco.timesheet.unit.TimesheetUnit;
import pl.qceyco.timesheet.unit.TimesheetUnitRepository;
import pl.qceyco.timesheet.workWeek.WorkWeek;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ProjectReportServiceTest {

    private TimesheetUnitRepository mockTimesheetUnitRepository;
    private ReportService mockReportService;

    @BeforeEach
    void setUp() {
        ProjectRepository mockProjectRepository = mock(ProjectRepository.class);
        EmployeeRepository mockEmployeeRepository = mock(EmployeeRepository.class);
        ClientRepository mockClientRepository = mock(ClientRepository.class);
        mockTimesheetUnitRepository = mock(TimesheetUnitRepository.class);
        mockReportService = new ReportService(mockTimesheetUnitRepository, mockProjectRepository, mockEmployeeRepository, mockClientRepository);
        given(mockProjectRepository.findFirstByIdWithProjectTeamMembers(anyLong())).willReturn(prepareProjectData());

    }

    @Test
    void givenNonEmptyDB_whenTestProcessProjectReport_thenResultIsReturned() {
        TimesheetUnit timesheetUnit1 = prepareTimesheetUnit1Data();
        TimesheetUnit timesheetUnit2 = prepareTimesheetUnit2Data();
        given(mockTimesheetUnitRepository.findAllByProjectIdOrderByEmployeeId(anyLong())).willReturn(Arrays.asList(timesheetUnit1, timesheetUnit2));

        ProjectReport actualReport = mockReportService.projectReportProcess(1L);
        assertThat(actualReport.getPotentialValueOfRenderedServices(), is(44000));
        assertThat(actualReport.getAmountOfHours(), is(80));
        assertThat(actualReport.isProjectIsProfitable(), is(false));
    }

    @Test
    void givenEmptyDB_whenTestProcessProjectReport_thenResultIsNoValues() {
        given(mockTimesheetUnitRepository.findAllByProjectIdOrderByEmployeeId(anyLong())).willReturn(Collections.emptyList());

        ProjectReport actualReport = mockReportService.projectReportProcess(1L);
        assertThat(actualReport.getPotentialValueOfRenderedServices(), is(0));
        assertThat(actualReport.getAmountOfHours(), is(0));
        assertThat(actualReport.isProjectIsProfitable(), is(true));
    }

    //######################### PREPARE TEST DATA ###############################################3

    private Employee prepareEmployee1Data() {
        AdditionalInfoEmployee additionalInfoEmployee1 = AdditionalInfoEmployee.builder()
                .hourlyRateChargingClients(400)
                .bonus(10)
                .targetBudget(10000).build();
        return Employee.builder()
                .id(1L)
                .additionalInfo(additionalInfoEmployee1).build();
    }

    private Employee prepareEmployee2Data() {
        AdditionalInfoEmployee additionalInfoEmployee2 = AdditionalInfoEmployee.builder()
                .hourlyRateChargingClients(500)
                .bonus(15)
                .targetBudget(15000).build();
        return Employee.builder()
                .id(2L)
                .additionalInfo(additionalInfoEmployee2).build();
    }

    private Project prepareProjectData() {
        return Project.builder()
                .client(prepareClientData())
                .projectTeam(Arrays.asList(prepareEmployee1Data(), prepareEmployee2Data()))
                .capOnRemuneration(10000)
                .isBillable(true)
                .id(1L).build();
    }

    private WorkWeek prepareWorkWeek1Data() {
        return WorkWeek.builder()
                .mondayHours(4)
                .tuesdayHours(4)
                .wednesdayHours(4)
                .thursdayHours(4)
                .fridayHours(4).build();
    }

    private WorkWeek prepareWorkWeek2Data() {
        return WorkWeek.builder()
                .mondayHours(12)
                .tuesdayHours(12)
                .wednesdayHours(12)
                .thursdayHours(12)
                .fridayHours(12).build();
    }

    private TimesheetUnit prepareTimesheetUnit1Data() {
        return TimesheetUnit.builder()
                .employee(prepareEmployee1Data())
                .project(prepareProjectData())
                .workWeek(prepareWorkWeek1Data()).build();
    }

    private TimesheetUnit prepareTimesheetUnit2Data() {
        return TimesheetUnit.builder()
                .employee(prepareEmployee2Data())
                .project(prepareProjectData())
                .workWeek(prepareWorkWeek2Data()).build();
    }

    private Client prepareClientData() {
        return ClientLegalPerson.builder()
                .additionalInfo(AdditionalInfoClient.builder().hourlyRateIsCharged(550).build())
                .build();
    }
}