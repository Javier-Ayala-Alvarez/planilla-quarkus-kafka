package org.demo.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.demo.model.OrderEvent;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import java.util.concurrent.CompletionStage;

@Path("/orders") // URL base: http://localhost:8081/orders
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private static final Logger LOG = Logger.getLogger(OrderResource.class);

    @Inject
    @Channel("orders-out")
    Emitter<OrderEvent> emitter;

    @POST
    public CompletionStage<Response> createOrder(OrderEvent order) {
        LOG.infof("Recibida petición de orden: %s", order.getId());

        // Enviamos el objeto recibido directamente a Kafka
        // send() devuelve un CompletionStage que se completa cuando Kafka da el ACK
        return emitter.send(order)
            .thenApply(unused -> {
                LOG.infof("Orden %s enviada con éxito a Kafka", order.getId());
                return Response.status(Response.Status.ACCEPTED)
                        .entity(order)
                        .build();
            })
            .exceptionally(failure -> {
                LOG.error("Fallo crítico al enviar a Kafka", failure);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error al procesar la orden en el broker")
                        .build();
            });
    }
}