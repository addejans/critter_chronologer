package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        return convertPetToPetDTO(petService.savePet(convertPetDTOToPet(petDTO)));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertPetToPetDTO(petService.getPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        List<PetDTO> petDTOs = pets.stream().map(this::convertPetToPetDTO).collect(Collectors.toList());
        return petDTOs;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetsByOwner(ownerId);
        List<PetDTO> petDTOs = new ArrayList<>();
        pets.forEach(pet -> {
            petDTOs.add(convertPetToPetDTO(pet));
        });
        return petDTOs;
    }

    //TODO: Suggestions to enhance this project and make it stand out!
/**
    1) Add additional validations. Can you prevent requests from creating invalid schedules? Avoid detached Pets with no owners?
    2) It may be possible to replace some or all DTOs by using JSONView and JSONIgnore annotations. Can you replace the DTO objects without compromising encapsulation between the data layer and the controller layer? Watch out for lazy loading of collections!
    3) Add support for custom behaviors for Pets, or maybe a way to specify which activities are relevant to which type of pet. Can you implement this solution in a way that allows you to add new pets or change pet behavior without modifying code each time?
    4) Create new endpoints that complete the rest of the missing CRUD operations and support them on the back end. Or come up with additional data about your Entities that may be relevant and write queries to reference it.
    5) Add support for Schedules that include a startTime and endTime. Add a query that can find an open employee for a specific timeslot during the day.
*/


    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        petDTO.setType(pet.getPetType());
        return petDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        Customer customer = customerService.getCustomerById(petDTO.getOwnerId());
        pet.setCustomer(customer);
        pet.setPetType(petDTO.getType());
        return pet;
    }
}
