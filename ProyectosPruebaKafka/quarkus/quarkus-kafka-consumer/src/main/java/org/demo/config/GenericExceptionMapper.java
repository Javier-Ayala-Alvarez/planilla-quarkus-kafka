package org.demo.conf;

import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic Exception Mapper para Quarkus
 * Captura cualquier excepción y devuelve un JSON o HTML amigable.
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Context
    HttpHeaders headers;

    @Override
    public Response toResponse(Throwable exception) {

        // Log en consola para debug
        exception.printStackTrace();

        // Crear un modelo para enviar
        Map<String, Object> model = new HashMap<>();
        String message = exception.getMessage() != null ? exception.getMessage() : "Error desconocido";

        // Detectar si es petición API (JSON) o navegador (HTML)
        boolean wantsJson = headers != null &&
                headers.getAcceptableMediaTypes().stream()
                        .anyMatch(mt -> mt.isCompatible(MediaType.APPLICATION_JSON_TYPE));

        if (wantsJson) {
            // Retornar JSON: { "error": "mensaje" }
            model.put("error", message);
            return Response.status(500)
                    .entity(model)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else {
            // Retornar HTML sencillo para navegador
            String html = "<html><body>"
                    + "<h1>Ocurrió un error</h1>"
                    + "<p>" + message + "</p>"
                    + "</body></html>";
            return Response.status(500)
                    .entity(html)
                    .type(MediaType.TEXT_HTML)
                    .build();
        }
    }
}
