package org.demo.config;

import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.demo.model.OrderEvent;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class OrderConsumer {

    private static final Logger LOG = Logger.getLogger(OrderConsumer.class);

    // Inyectamos el canal de salida para reportar el estado
    @Inject
    @Channel("orders-status")
    Emitter<String> statusEmitter;

    @Incoming("orders-in")
    @Blocking
    public CompletionStage<Void> consume(Message<OrderEvent> message) {
        OrderEvent order = message.getPayload();
        LOG.infof("Procesando orden: %s", order.getId());

        try {
            processOrder(order);
            
            LOG.infof("Orden %s OK. Notificando éxito...", order.getId());
            
            // NOTIFICACIÓN PRODUCTIVA: Enviamos el evento de éxito
            statusEmitter.send(String.format("{\"id\":\"%s\", \"status\":\"PROCESADA\"}", order.getId()));
            
            return message.ack(); 
        } catch (Exception e) {
            LOG.errorf("Orden %s FALLÓ: %s. Notificando error...", order.getId(), e.getMessage());
            
            // NOTIFICACIÓN PRODUCTIVA: Enviamos el motivo del rechazo
            statusEmitter.send(String.format("{\"id\":\"%s\", \"status\":\"RECHAZADA\", \"reason\":\"%s\"}", 
                order.getId(), e.getMessage()));
            
            // Usamos nack para que el mensaje original se vaya al Dead Letter Queue (auditoría técnica)
            return message.nack(e);
        }
    }

    private void processOrder(OrderEvent order) {
        if (order.getAmount() <= 0) {
            throw new IllegalArgumentException("Monto inválido");
        }
        // Aquí iría tu lógica real: repo.save(order);
    }
}