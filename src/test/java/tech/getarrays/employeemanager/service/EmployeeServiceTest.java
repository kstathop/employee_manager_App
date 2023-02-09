package tech.getarrays.employeemanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.getarrays.employeemanager.exception.UserNotFoundException;
import tech.getarrays.employeemanager.model.Employee;
import tech.getarrays.employeemanager.repo.EmployeeRepo;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepo employeeRepo;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void testFindAllEmployees() {
        // Given
        Employee employee1 = new Employee(1L, "John Doe", "Boss@.gmail", "Boss", "264105555", "testUrl", "1");
        Employee employee2 = new Employee(2L, "Jane Doe", "Developer@.gmail", "Developer", "264107855", "testUrl", "2");
        when(employeeRepo.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        // When
        List<Employee> employees = employeeService.findAllEmployees();

        // Then
        assertThat(employees).hasSize(2);
        assertThat(employees).containsExactlyInAnyOrder(employee1, employee2);
    }

    @Test
    public void testFindAllEmployeesWithEmptyList() {
        // Given
        when(employeeRepo.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Employee> employees = employeeService.findAllEmployees();

        // Then
        assertThat(employees).isEmpty();
    }

    @Test
    public void testFindEmployeeById() {
        // Given
        Employee employee1 = new Employee(1L, "John Doe", "Boss@.gmail", "Boss", "264105555", "testUrl", "1");
        when(employeeRepo.findEmployeeById(1L)).thenReturn(Optional.of(employee1));

        // When
        Employee employee = employeeService.findEmployeeById(1L);

        // Then
        assertThat(employee).isEqualTo(employee1);
    }

    @Test
    public void testFindEmployeeByIdThrowsException() {
        // Given
        when(employeeRepo.findEmployeeById(2L)).thenReturn(Optional.empty());

        // When and Then
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> employeeService.findEmployeeById(2L))
                .withMessage("User by id 2 was not found");
    }
}
