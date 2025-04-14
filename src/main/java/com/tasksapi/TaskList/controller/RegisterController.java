package com.tasksapi.TaskList.controller;

import com.tasksapi.TaskList.dto.DatosRegistrarUser;
import com.tasksapi.TaskList.repository.UserRepository;
import com.tasksapi.TaskList.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/register")
//Controlador para el endpoint de registro de usuarios nuevos en la API
public class RegisterController {
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public RegisterController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }
    //Endpoint para registrar un nuevo usuario en la API
    @PostMapping
    @Transactional
    public ResponseEntity<?> register(@RequestBody @Valid DatosRegistrarUser datos, UriComponentsBuilder uriComponentsBuilder) {
        //Se hace llamado al metodo crearUsuario del servicio creado UserService
        var newUser = userService.crearUsuario(datos);
        //Se crea un objeto DatosDetallesUser con los datos del nuevo usuario y se devuelve en un ResponseEntity con un status code 201 (Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Usuario creado con éxito"));
    }
}
