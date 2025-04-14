package com.tasksapi.TaskList.controller;

import com.tasksapi.TaskList.domain.users.User;
import com.tasksapi.TaskList.dto.DatosAuthenticationUser;
import com.tasksapi.TaskList.dto.DatosJWTToken;
import com.tasksapi.TaskList.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    //AuthenticationManager es un componente de Spring Security encargado de autenticar usuarios
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    /*
    * UsernamePasswordAuthenticationToken es un objeto que encapsula el email y la contraseña del usuario.
    * Se usa para pasar las credenciales al sistema de autenticación de Spring
    *
    * authenticationManager.authenticate(authToken):
    *   • Verifica si las credenciales son correctas.
    *   • Si son correctas, devuelve un objeto Authentication con los detalles del usuario autenticado.
    *   • Si son incorrectas, lanza una excepción (BadCredentialsException).
    * */
    @PostMapping
    public ResponseEntity<DatosJWTToken> authenticationUser(@RequestBody @Valid DatosAuthenticationUser datos) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datos.email(), datos.password());
        var userAuthenticate = authenticationManager.authenticate(authToken);

        //Se obtiene el usuario autenticado
        var JWTToken = tokenService.generateToken((User) userAuthenticate.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWTToken));
    }
}
