package com.mariluz.catalog.exceptions;

import com.mariluz.catalog.dto.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ─── Handlers de dominio ─────────────────────────────────────────────────

    // Handler para ProductDoesNotExistException
    @ExceptionHandler(ProductDoesNotExistException.class)
    public ResponseEntity<ErrorResponse> handleProductDoesNotExistException(
        ProductDoesNotExistException ex,
        HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .errors(Map.of("error", ex.getMessage()))
                .endpoint(request.getRequestURI())
                .build()
        );
    }

    // Handler para ForbiddenOperationException (permiso denegado)
    @ExceptionHandler(ForbiddenOperationException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenOperation(
        ForbiddenOperationException ex,
        HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
            ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .message(ex.getMessage())
                .errors(Map.of("error", ex.getMessage()))
                .endpoint(request.getRequestURI())
                .build()
        );
    }

    // Handler para InsufficientStockException (stock insuficiente)
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStock(
        InsufficientStockException ex,
        HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatusCode.valueOf(422)).body(
            ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(422)
                .message(ex.getMessage())
                .errors(Map.of("error", ex.getMessage()))
                .endpoint(request.getRequestURI())
                .build()
        );
    }

    // ─── Handlers de validación ───────────────────────────────────────────────

    // Handler para MethodArgumentNotValidException (validaciones de @Valid en @RequestBody)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
        MethodArgumentNotValidException ex,
        HttpServletRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
            .getFieldErrors()
            .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Error de validación")
                .errors(errors)
                .endpoint(request.getRequestURI())
                .build()
        );
    }

    // Handler para ConstraintViolationException (validaciones de @Validated en @PathVariable)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
        ConstraintViolationException ex,
        HttpServletRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(cv -> {
            String path = cv.getPropertyPath().toString();
            // Extraer solo el nombre del parámetro (ej: "getProductById.id")
            String field = path.contains(".")
                ? path.substring(path.lastIndexOf('.') + 1)
                : path;
            errors.put(field, cv.getMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Error de validación en parámetros")
                .errors(errors)
                .endpoint(request.getRequestURI())
                .build()
        );
    }

    // Handler para HttpMessageNotReadableException (JSON malformado o tipo de dato incorrecto en el body)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadable(
        HttpMessageNotReadableException ex,
        HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(
                    "El cuerpo de la solicitud es inválido o está mal formado"
                )
                .errors(
                    Map.of("error", "JSON inválido o tipo de dato incorrecto")
                )
                .endpoint(request.getRequestURI())
                .build()
        );
    }

    // ─── Handlers de HTTP ─────────────────────────────────────────────────────

    // Handler para HttpRequestMethodNotSupportedException (método HTTP no soportado en esa ruta)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(
        HttpRequestMethodNotSupportedException ex,
        HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
            ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .message(
                    "Método HTTP '" +
                        ex.getMethod() +
                        "' no permitido para esta ruta"
                )
                .errors(Map.of("error", ex.getMessage()))
                .endpoint(request.getRequestURI())
                .build()
        );
    }

    // ─── Handlers de autenticación JWT ───────────────────────────────────────

    // Handler para ExpiredJwtException (token expirado)
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredTokenException(
        ExpiredJwtException ex,
        HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Token expirado")
                .errors(
                    Map.of(
                        "error",
                        "El token ha expirado. Por favor, inicie sesión nuevamente."
                    )
                )
                .endpoint(request.getRequestURI())
                .build()
        );
    }

    // Handler para JwtException (token inválido o corrupto)
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtError(
        JwtException ex,
        HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Token inválido")
                .errors(
                    Map.of(
                        "error",
                        "El token proporcionado es inválido o está corrupto."
                    )
                )
                .endpoint(request.getRequestURI())
                .build()
        );
    }

    // ─── Handler genérico ────────────────────────────────────────────────────

    // Handler para Exception (captura cualquier excepción no manejada)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
        Exception ex,
        HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ErrorResponse.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Error interno del servidor")
                .errors(
                    Map.of(
                        "error",
                        "Ocurrió un error inesperado. Por favor, intente más tarde."
                    )
                )
                .endpoint(request.getRequestURI())
                .build()
        );
    }
}
