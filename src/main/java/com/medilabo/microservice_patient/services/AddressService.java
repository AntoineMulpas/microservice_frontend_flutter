package com.medilabo.microservice_patient.services;

import com.medilabo.microservice_patient.exceptions.AddressDoesNotExistsException;
import com.medilabo.microservice_patient.models.Address;
import com.medilabo.microservice_patient.repositories.AddressRepository;
import com.medilabo.microservice_patient.utils.AddressMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address add(Address address) {
        return addressRepository.save(address);
    }

    public Address findById(Long id) throws AddressDoesNotExistsException {
        return addressExistById(id);
    }

    public Address updateById(Long id, Address address) throws AddressDoesNotExistsException {
        Address addressExistById = addressExistById(id);
        return addressRepository.save(AddressMapper.map(addressExistById, address));
    }

    public void deleteById(Long id) throws AddressDoesNotExistsException {
        Optional <Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            addressRepository.deleteById(id);
        }
        throw new AddressDoesNotExistsException("Patient does not exist for id: " + id);
    }

    public List <Address> findAll() {
        return addressRepository.findAll();
    }

    private Address addressExistById(Long id) throws AddressDoesNotExistsException {
        Optional <Address> address = addressRepository.findById(id);
        if (address.isPresent()) {
            return address.get();
        }
        throw new AddressDoesNotExistsException("Address does not exist for id: " + id);
    }


}