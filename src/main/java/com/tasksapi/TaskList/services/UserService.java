package com.tasksapi.TaskList.services;

import com.tasksapi.TaskList.domain.users.User;
import com.tasksapi.TaskList.dto.DatosRegistrarUser;
import com.tasksapi.TaskList.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    /*
    * Metodo que permite crear un usuarip y guardarlo en la base de datos
    * Se usa el metodo encode de la clase PasswordEncoder
    * */
    public User crearUsuario(@Valid DatosRegistrarUser datos) {
        return userRepository.save(new User(datos.username(), datos.email(), passwordEncoder.encode(datos.password())));
    }
}
