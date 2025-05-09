package com.postech.fiap.fase1.domain.service;

import com.postech.fiap.fase1.configuration.exception.ApplicationException;
import com.postech.fiap.fase1.domain.assembler.UserAssembler;
import com.postech.fiap.fase1.domain.dto.UserInputDTO;
import com.postech.fiap.fase1.domain.model.User;
import com.postech.fiap.fase1.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public User getOne(Long id) {
        return findById(id);
    }


    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ApplicationException("User not found"));
    }

    @Transactional(readOnly = true)
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(
                () -> new ApplicationException("User not found"));
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User create(UserInputDTO userInputDTO) {
        User user = UserAssembler.inputToModel(userInputDTO, passwordEncoder.encode(userInputDTO.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long idUser) {
        User user = findById(idUser);
        userRepository.delete(user);
    }
}
