package com.tasksapi.TaskList.infra.errors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class TratadorDeErrores {

    /*
    * Se crea una clase record para mostrar los errores especificos de la clase MethodArgumentNotValidException
    */
    public record DatosErrorValidacion(String campo, String error){
        public DatosErrorValidacion(FieldError fieldError ) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    /*
    * Se crea una clase record para mostrar los errores especificos de la clase HttpMessageConversionException
    */
    public record DatosErrorConversion(String error, String message) {
        public DatosErrorConversion(String error, HttpMessageConversionException http) {
            this(error, http.getMessage());
        }
    }

    /*
    * Este método manejará todas las excepciones de tipo EntityNotFoundException
    * Si el usuario no existe, se lanza la excepción y se activará el método del manejador de errores.
    * Cuando se lanza esta excepción, se ejecuta este método y retorna una respuesta HTTP 404 (Not Found).
    * ResponseEntity.notFound().build() → Construye y devuelve una respuesta sin cuerpo con el código de estado HTTP 404.
    */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404() {
        return ResponseEntity.notFound().build();
    }

    /*
    * Este metodo captura errores de validación cuando una solicitud no cumple con las restricciones definidas en los DTOs
    * Intercepta todas las excepciones de tipo MethodArgumentNotValidException, que ocurren cuando la validación de datos falla en una solicitud HTTP.
    * ex.getFieldErrors(): Obtiene una lista de todos los errores de validación detectados en los campos del DTO o entidad
    * ResponseEntity.badRequest().body(errores): Retorna una respuesta HTTP 400 (Bad Request) con la lista de errores como cuerpo.
    */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException ex) {
        var errores = ex.getFieldErrors().stream()
                .map(DatosErrorValidacion::new).
                collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errores);
    }

    /*
    * Este metodo captura errores de conversion de datos cuando no se cumple en el envio del JSON
    * */
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity tratarError400Converted(HttpMessageConversionException http) {
        var errorConversion = new DatosErrorConversion("Error en la conversion de datos", http);
        return ResponseEntity.badRequest().body(errorConversion);
    }

    /*
    * Captura todas las excepciones de credenciales inválidas en la aplicación.
    * Devuelve un JSON con código 401 y un mensaje de error.
    */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity tratarErrorCredenciales(BadCredentialsException cr) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of("error", "Email o contraseña incorrectos")
        );
    }
}
