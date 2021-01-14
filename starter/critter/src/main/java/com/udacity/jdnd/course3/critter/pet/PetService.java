package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {

    //TODO: Prefer constructor injection to field injection
    //      https://reflectoring.io/constructor-injection/
    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerService customerService;

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Pet savePet(Pet pet) {
        Pet savedPet = petRepository.save(pet);
        customerService.addPetToCustomer(savedPet, savedPet.getCustomer());
        return savedPet;
    }

    public Pet getPetById(Long petId) {
        return petRepository.getOne(petId);
    }

    public List<Pet> getPetsByOwner(Long ownerId) {
        return petRepository.findByCustomer_Id(ownerId);
    }
}
