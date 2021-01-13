package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long employeeId) {
        return employeeRepository.getOne(employeeId);
    }

    public void setEmployeeAvailability(Set<DayOfWeek> availability, Long employeeId) {
        Employee employee = getEmployeeById(employeeId);
        employee.setDaysAvailable(availability);
        employeeRepository.save(employee);
    }

    public List<Employee> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {
        DayOfWeek day = employeeRequestDTO.getDate().getDayOfWeek();
        Set<EmployeeSkill> skills = employeeRequestDTO.getSkills();

        List<Employee> skilledEmployees = employeeRepository.findAll();
        System.out.println(skilledEmployees.size());
        List<Employee> availableSkilledEmployees = new ArrayList<>();

        skilledEmployees.forEach(employee -> {
            if(employee.getDaysAvailable().contains(day) && employee.getSkills().containsAll(skills)) {
                availableSkilledEmployees.add(employee);
            }
        });

        return availableSkilledEmployees;
    }
}