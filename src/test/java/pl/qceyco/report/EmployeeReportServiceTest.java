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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class EmployeeReportServiceTest {

    private EmployeeRepository employeeRepository;
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        TimesheetUnitRepository timesheetUnitRepository = mock(TimesheetUnitRepository.class);
        ProjectRepository projectRepository = mock(ProjectRepository.class);
        ClientRepository clientRepository = mock(ClientRepository.class);
        employeeRepository = mock(EmployeeRepository.class);
        reportService = new ReportService(timesheetUnitRepository, projectRepository, employeeRepository, clientRepository);
    }

    @Test
    void givenNonEmptyDB_whenTestProcessEmployeeReport_thenResultIsReturned() {
        TimesheetUnit timesheetUnit1 = prepareTimesheetUnit1Data();
        TimesheetUnit timesheetUnit2 = prepareTimesheetUnit2Data();
        given(employeeRepository.findFirstById(1L)).willReturn(prepareEmployeeData());
        given(reportService.getAllEmployeeTimesheetsFrom4Weeks(1L, LocalDate.now(), LocalDate.now().plusDays(27))).willReturn(Arrays.asList(timesheetUnit1, timesheetUnit2));

        EmployeeReport actualReport = reportService.employeeReportProcess(LocalDate.now(), 1L);
        assertThat(actualReport.getAmountOfBillableHours(), is(80));
        assertThat(actualReport.getAmountOfNonBillableHours(), is(0));
        assertThat(actualReport.getBonusAmount(), is(2200));
        assertThat(actualReport.getValueOfRenderedServices(), is(32000));
        assertThat(actualReport.getWorkTimeUtilizationLevel(), is(100));
        assertThat(actualReport.isMonthlyTargetAchieved(), is(true));
    }

    @Test
    void givenEmptyDB_whenTestProcessEmployeeReport_thenResultIsNoValues() {
        given(employeeRepository.findFirstById(1L)).willReturn(prepareEmployeeData());
        given(reportService.getAllEmployeeTimesheetsFrom4Weeks(1L, LocalDate.now(), LocalDate.now())).willReturn(Collections.emptyList());

        EmployeeReport actualReport = reportService.employeeReportProcess(LocalDate.now(), 1L);
        assertThat(actualReport.getAmountOfBillableHours(), is(0));
        assertThat(actualReport.getAmountOfNonBillableHours(), is(0));
        assertThat(actualReport.getBonusAmount(), is(0));
        assertThat(actualReport.getValueOfRenderedServices(), is(0));
        assertThat(actualReport.getWorkTimeUtilizationLevel(), is(0));
        assertThat(actualReport.isMonthlyTargetAchieved(), is(false));
    }


    //######################### PREPARE TEST DATA ###############################################3

    private Employee prepareEmployeeData() {
        AdditionalInfoEmployee additionalInfoEmployee1 = AdditionalInfoEmployee.builder()
                .hourlyRateChargingClients(400)
                .hourlyRateReceivingSalary(200)
                .bonus(10)
                .targetBudget(10000).build();
        return Employee.builder()
                .id(1L)
                .additionalInfo(additionalInfoEmployee1).build();
    }

    private Project prepareProjectData() {
        return Project.builder()
                .client(prepareClientData())
                .projectTeam(Collections.singletonList(prepareEmployeeData()))
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
                .employee(prepareEmployeeData())
                .project(prepareProjectData())
                .workWeek(prepareWorkWeek1Data()).build();
    }

    private TimesheetUnit prepareTimesheetUnit2Data() {
        return TimesheetUnit.builder()
                .employee(prepareEmployeeData())
                .project(prepareProjectData())
                .workWeek(prepareWorkWeek2Data()).build();
    }

    private Client prepareClientData() {
        return ClientLegalPerson.builder()
                .additionalInfo(AdditionalInfoClient.builder().hourlyRateIsCharged(550).build())
                .build();
    }
}