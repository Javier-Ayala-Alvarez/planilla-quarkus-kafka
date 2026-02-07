
package org.demo.resource;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class OrderStatusConsumer {

    private static final Logger LOG = Logger.getLogger(OrderStatusConsumer.class);

    @Incoming("orders-status-in") // Nombre del canal de entrada para el productor
    public void processStatus(String statusJson) {
        // En producción aquí actualizarías tu Base de Datos: 
        // repo.updateStatus(id, status);
        
        LOG.infof("🔔 NOTIFICACIÓN RECIBIDA DESDE EL CONSUMIDOR: %s", statusJson);
        
        if (statusJson.contains("RECHAZADA")) {
            LOG.error("❌ Alerta: Una orden no pudo ser procesada. Tomando acciones correctivas...");
        } else {
            LOG.info("✅ Orden finalizada con éxito.");
        }
    }
}