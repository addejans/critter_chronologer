package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return convertScheduleToScheduleDTO(scheduleService.saveSchedule(convertScheduleDTOToSchedule(scheduleDTO)));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        schedules.forEach(schedule -> {
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        });
        return scheduleDTOS;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getScheduleForPet(petId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        schedules.forEach(schedule -> {
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        });
        return scheduleDTOS;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getScheduleForEmployee(employeeId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        schedules.forEach(schedule -> {
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        });
        return scheduleDTOS;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        schedules.forEach(schedule -> {
            scheduleDTOS.add(convertScheduleToScheduleDTO(schedule));
        });
        return scheduleDTOS;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        List<Long> employeeIds = new ArrayList<>();
        List<Long> petIds = new ArrayList<>();

        BeanUtils.copyProperties(schedule, scheduleDTO);

        if(schedule.getEmployees() != null && schedule.getEmployees().size() > 0) {
            schedule.getEmployees().forEach(employee -> {
                employeeIds.add(employee.getId());
            });
        }
        scheduleDTO.setEmployeeIds(employeeIds);

        if(schedule.getPets() != null && schedule.getPets().size() > 0) {
            schedule.getPets().forEach(pet -> {
                petIds.add(pet.getId());
            });
        }
        scheduleDTO.setPetIds(petIds);

        if(schedule.getActivities() != null && schedule.getActivities().size() > 0) {
            Set<EmployeeSkill> activities = new HashSet<>(schedule.getActivities());
            scheduleDTO.setActivities(activities);
        }

        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setEmployees(new ArrayList<>());
        schedule.setPets(new ArrayList<>());
        BeanUtils.copyProperties(scheduleDTO, schedule);

        if(scheduleDTO.getEmployeeIds() != null && scheduleDTO.getEmployeeIds().size() > 0) {
            scheduleDTO.getEmployeeIds().forEach(employeeId -> {
                Employee employee = new Employee();
                employee.setId(employeeId);
                schedule.getEmployees().add(employee);
            });
        }

        if(scheduleDTO.getPetIds() != null && scheduleDTO.getPetIds().size() > 0) {
            scheduleDTO.getPetIds().forEach(petId -> {
                Pet pet = new Pet();
                pet.setId(petId);
                schedule.getPets().add(pet);
            });
        }

        return schedule;
    }

}
