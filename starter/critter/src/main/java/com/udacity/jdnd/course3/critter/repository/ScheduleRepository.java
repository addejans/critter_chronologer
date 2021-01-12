package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByPets_Id(Long petId);
    List<Schedule> findByEmployees_Id(Long petId);

    List<Schedule> getAllByPetsIn(List<Pet> pets);
    List<Schedule> getAllByPetsContains(Pet pet);
    List<Schedule> getAllByEmployeesContains(Employee employee);

    List<Schedule> findAllEmployeesById(long employeeId);
    List<Schedule> findAllPetById(long petId);
}
