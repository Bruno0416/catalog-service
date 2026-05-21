package com.mariluz.catalog.exceptions;

/*
  Excepción para operaciones prohibidas por falta de permisos (HTTP 403 FORBIDDEN).

  Diferencia:
    - 401 UNAUTHORIZED → el usuario NO está autenticado (sin token o token inválido).
    - 403 FORBIDDEN    → el usuario SÍ está autenticado, pero no tiene permiso suficiente.
 */
public class ForbiddenOperationException extends RuntimeException {

    public ForbiddenOperationException(String message) {
        super(message);
    }
}
