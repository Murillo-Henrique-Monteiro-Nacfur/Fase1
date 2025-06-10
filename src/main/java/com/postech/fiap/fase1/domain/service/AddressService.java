package com.postech.fiap.fase1.domain.service;

import com.postech.fiap.fase1.configuration.exception.ApplicationException;
import com.postech.fiap.fase1.domain.assembler.AddressAssembler;
import com.postech.fiap.fase1.domain.dto.AddressDTO;
import com.postech.fiap.fase1.domain.dto.AddressInputDTO;
import com.postech.fiap.fase1.domain.dto.AddressInputUpdateDTO;
import com.postech.fiap.fase1.domain.dto.auth.SessionDTO;
import com.postech.fiap.fase1.domain.model.Address;
import com.postech.fiap.fase1.domain.model.User;
import com.postech.fiap.fase1.domain.repository.AddressRepository;
import com.postech.fiap.fase1.domain.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.postech.fiap.fase1.domain.assembler.AddressAssembler.toDTO;
import static com.postech.fiap.fase1.domain.assembler.AddressAssembler.toEntity;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final UserValidator userValidator;

    @Transactional
    public AddressDTO createAddress(AddressInputDTO addressInputDTO, SessionDTO sessionDTO) {
        User user = userService.getOne(sessionDTO, addressInputDTO.getUserId());
        userValidator.verifyUserLoggedIsAdminOrOwner(sessionDTO, user.getId());

        if (existsAddressByUserId(user)) {
            throw new ApplicationException("User already has an address");
        }

        Address address = toEntity(addressInputDTO, user);
        return toDTO(addressRepository.save(address));
    }

    @Transactional
    public void delete(Long id, SessionDTO sessionDTO) {
        Address address = this.findById(id);
        User user = address.getUser();
        userValidator.verifyUserLoggedIsAdminOrOwner(sessionDTO, user.getId());
        addressRepository.delete(address);
    }

    @Transactional(readOnly = true)
    public boolean existsAddressByUserId(User user) {
        return addressRepository.existsByUserId(user.getId());
    }

    @Transactional
    public Address updateAddress(Long id, AddressInputUpdateDTO addressInputUpdateDTO) {
        Address address = this.findById(id);
        BeanUtils.copyProperties(addressInputUpdateDTO, address, "id", "userId");
        return addressRepository.save(address);
    }

    @Transactional(readOnly = true)
    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new ApplicationException("Address not found"));
    }

    @Transactional(readOnly = true)
    public Address getOneById(Long id, SessionDTO sessionDTO) {
        Address address = this.findById(id);
        userValidator.verifyUserLoggedIsAdminOrOwner(sessionDTO, address.getUser().getId());
        return address;
    }

    @Transactional(readOnly = true)
    public List<AddressDTO> findAll() {
        List<Address> addresses = addressRepository.findAll();
        if (addresses.isEmpty()) {
            return List.of();
        }
        return addresses.stream().map(AddressAssembler::toDTO).toList();
    }
}
