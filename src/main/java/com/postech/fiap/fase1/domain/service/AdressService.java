package com.postech.fiap.fase1.domain.service;

import com.postech.fiap.fase1.configuration.exception.ApplicationException;
import com.postech.fiap.fase1.domain.assembler.AdressAssembler;
import com.postech.fiap.fase1.domain.dto.AdressDTO;
import com.postech.fiap.fase1.domain.dto.AdressInputDTO;
import com.postech.fiap.fase1.domain.dto.auth.SessionDTO;
import com.postech.fiap.fase1.domain.model.Adress;
import com.postech.fiap.fase1.domain.model.User;
import com.postech.fiap.fase1.domain.repository.AdressRepository;
import com.postech.fiap.fase1.domain.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.postech.fiap.fase1.domain.assembler.AdressAssembler.toDTO;
import static com.postech.fiap.fase1.domain.assembler.AdressAssembler.toEntity;

@Service
@RequiredArgsConstructor
public class AdressService {
    private final AdressRepository adressRepository;
    private final UserService userService;
    private final UserValidator userValidator;

    @Transactional
    public AdressDTO createAdress(AdressInputDTO adressInputDTO, SessionDTO sessionDTO) {
        User user = userService.getOne(sessionDTO, adressInputDTO.getUserId());
        userValidator.verifyUserLoggedIsAdminOrOwner(sessionDTO, user.getId());

        if (existsAdressByUserId(user)) {
            throw new ApplicationException("User already has an address");
        }

        Adress adress = toEntity(adressInputDTO, user);
        return toDTO(adressRepository.save(adress));
    }

    @Transactional
    public void delete(Long id, SessionDTO sessionDTO) {
        Adress adress = this.findById(id);
        User user = adress.getUser();
        userValidator.verifyUserLoggedIsAdminOrOwner(sessionDTO, user.getId());
        adressRepository.delete(adress);
    }

    @Transactional(readOnly = true)
    public boolean existsAdressByUserId(User user) {
        return adressRepository.existsByUserId(user.getId());
    }

    public Adress updateAdress(Long id, AdressInputDTO adressInputDTO) {
        Adress adress = this.findById(id);
        BeanUtils.copyProperties(adressInputDTO, adress, "id", "userId");
        return adressRepository.save(adress);
    }

    @Transactional(readOnly = true)
    public Adress findById(Long id) {
        return adressRepository.findById(id).orElseThrow(() -> new ApplicationException("Adress not found"));
    }

    @Transactional(readOnly = true)
    public Adress getOneById(Long id, SessionDTO sessionDTO) {
        Adress adress = this.findById(id);
        userValidator.verifyUserLoggedIsAdminOrOwner(sessionDTO, adress.getUser().getId());
        return adress;
    }

    @Transactional(readOnly = true)
    public List<AdressDTO> findAll() {
        List<Adress> adresses = adressRepository.findAll();
        if (adresses.isEmpty()) {
            return List.of();
        }
        return adresses.stream().map(AdressAssembler::toDTO).toList();
    }
}
