<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Kafka + Quarkus - Sistema de Procesamiento de Ordenes</title>
  <style>
    body {
      font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
      margin: 0;
      background: linear-gradient(180deg, #020617, #0f172a);
      color: #e2e8f0;
      line-height: 1.7;
    }

    .container {
      max-width: 1150px;
      margin: auto;
      padding: 50px 24px;
    }

    h1, h2, h3 {
      color: #38bdf8;
      margin-top: 40px;
    }

    h1 {
      font-size: 2.7rem;
      text-align: center;
      margin-bottom: 8px;
    }

    .subtitle {
      text-align: center;
      color: #94a3b8;
      margin-bottom: 50px;
      font-size: 1.1rem;
    }

    .card {
      background: rgba(17,24,39,0.8);
      backdrop-filter: blur(6px);
      border-radius: 18px;
      padding: 26px;
      margin-top: 22px;
      box-shadow: 0 20px 40px rgba(0,0,0,0.45);
      border: 1px solid rgba(148,163,184,0.08);
    }

    .highlight {
      background: rgba(56,189,248,0.08);
      border-left: 4px solid #38bdf8;
      padding: 14px;
      border-radius: 10px;
      margin: 15px 0;
    }

    code {
      background: #020617;
      padding: 4px 7px;
      border-radius: 6px;
      color: #7dd3fc;
      font-size: 0.9rem;
    }

    pre {
      background: #020617;
      padding: 18px;
      border-radius: 14px;
      overflow-x: auto;
      border: 1px solid #1e293b;
      margin-top: 12px;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
    }

    th, td {
      padding: 14px;
      border-bottom: 1px solid #1e293b;
      text-align: left;
    }

    th {
      background: #020617;
      color: #38bdf8;
    }

    .grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
      gap: 22px;
    }

    .step {
      background: rgba(2,6,23,0.6);
      padding: 18px;
      border-radius: 14px;
      margin-top: 16px;
      border: 1px solid rgba(148,163,184,0.08);
    }

    .badge {
      display: inline-block;
      background: #0369a1;
      padding: 6px 12px;
      border-radius: 999px;
      font-size: 0.8rem;
      margin: 4px;
    }

    a {
      color: #38bdf8;
      text-decoration: none;
    }

    a:hover {
      text-decoration: underline;
    }

    .footer {
      text-align: center;
      margin-top: 70px;
      color: #64748b;
      font-size: 0.9rem;
    }
  </style>
</head>
<body>
  <div class="container">

    <h1>🚀 Kafka + Quarkus</h1>
    <p class="subtitle">Arquitectura basada en eventos para procesamiento asincrónico de órdenes</p>

    <div class="card">
      <h2>📌 Descripción General</h2>
      <p>
        Este proyecto demuestra cómo construir un <strong>sistema distribuido moderno</strong> utilizando <strong>Apache Kafka</strong> y <strong>Quarkus</strong>.
        Implementa comunicación asincrónica entre microservicios mediante eventos, simulando un flujo real de órdenes de compra.
      </p>

      <div class="highlight">
        💡 Ideal como proyecto de portafolio para demostrar conocimientos en <strong>microservicios</strong>, <strong>event-driven architecture</strong> y <strong>mensajería con Kafka</strong>.
      </div>

      <h3>Arquitectura</h3>
      <pre>
Kafka (Topic: orders)
[Producer] ──POST /orders──> [orders] ──────> [Consumer]
(puerto 8081)                               (puerto 8082)
                                                  │
                   Kafka (Topic: orders-status)   │
[Producer] <──────────── [orders-status] <────────┘
(Log de notificación)
      </pre>
    </div>

    <h2>🧩 Microservicios</h2>

    <div class="grid">
      <div class="card">
        <h3>📤 Producer</h3>
        <ul>
          <li>Endpoint <code>POST /orders</code> (Puerto <code>8081</code>)</li>
          <li>Publica eventos en Kafka</li>
          <li>Respuesta HTTP <code>202 ACCEPTED</code></li>
          <li>Escucha estados desde <code>orders-status</code></li>
          <li>Configurado con <code>acks=all</code></li>
        </ul>
      </div>

      <div class="card">
        <h3>📥 Consumer</h3>
        <ul>
          <li>Consume eventos desde <code>orders</code></li>
          <li>Valida reglas de negocio</li>
          <li>Publica resultados</li>
          <li>Soporte Dead Letter Queue</li>
          <li>Reintentos automáticos</li>
          <li>Puerto <code>8082</code></li>
        </ul>
      </div>
    </div>

    <div class="card">
      <h2>🛠️ Tecnologías</h2>
      <table>
        <tr><th>Tecnología</th><th>Versión</th></tr>
        <tr><td>Java</td><td>21</td></tr>
        <tr><td>Quarkus</td><td>3.31.2</td></tr>
        <tr><td>Apache Kafka</td><td>3.7.0</td></tr>
        <tr><td>Maven</td><td>3.9+</td></tr>
        <tr><td>Docker Compose</td><td>3.8</td></tr>
      </table>

      <h3>Dependencias Clave</h3>
      <span class="badge">quarkus-rest</span>
      <span class="badge">kafka</span>
      <span class="badge">reactive-messaging</span>
      <span class="badge">jackson</span>
      <span class="badge">CDI</span>
    </div>

    <div class="card">
      <h2>✅ Prerrequisitos</h2>
      <ul>
        <li>Java 21</li>
        <li>Docker Desktop</li>
        <li>Maven o Maven Wrapper</li>
        <li>Postman / Insomnia</li>
      </ul>
    </div>

    <div class="card">
      <h2>📁 Estructura del Proyecto</h2>
      <pre>
kafka-quarkus/
 ├── docker-compose.yml
 ├── quarkus-kafka-produce/
 └── quarkus-kafka-consumer/
      </pre>

      <div class="highlight">
        👉 <strong>IMPORTANTE:</strong> El archivo <code>docker-compose.yml</code> debe ejecutarse desde la carpeta raíz del proyecto.
      </div>
    </div>

    <div class="card">
      <h2>🐳 Levantar el Ambiente (Paso a Paso)</h2>

      <div class="step">
        <h3>1️⃣ Posicionarse en la carpeta raíz</h3>
        <pre>cd ruta/donde/clonaste/kafka-quarkus</pre>

        Verificá que exista el archivo:
        <pre>docker-compose.yml</pre>
      </div>

      <div class="step">
        <h3>2️⃣ Iniciar Kafka y Kafdrop</h3>
        <pre>docker compose up -d</pre>

        Esto iniciará:
        <ul>
          <li>Kafka Broker</li>
          <li>Zookeeper (si aplica)</li>
          <li>Kafdrop (UI Web)</li>
        </ul>

        Verificar contenedores:
        <pre>docker compose ps</pre>
      </div>

      <div class="step">
        <h3>3️⃣ Acceder a la UI de Kafka</h3>
        <p>
          Abrí tu navegador:
          👉 <a href="http://localhost:9000">http://localhost:9000</a>
        </p>
      </div>

      <div class="step">
        <h3>4️⃣ Ejecutar el Producer</h3>
        <pre>
cd quarkus-kafka-produce
./mvnw quarkus:dev
        </pre>

        Windows:
        <pre>mvnw.cmd quarkus:dev</pre>
      </div>

      <div class="step">
        <h3>5️⃣ Ejecutar el Consumer</h3>
        <pre>
cd quarkus-kafka-consumer
./mvnw quarkus:dev
        </pre>
      </div>

      <div class="highlight">
        ⚠️ Ejecutá Producer y Consumer en terminales separadas.
      </div>

    </div>

    <div class="card">
      <h2>🧪 Pruebas con Postman / Insomnia</h2>

      <div class="highlight">
        👉 <strong>Base URL:</strong> <code>http://localhost:8081</code>
      </div>

      <h3>📌 Endpoint</h3>
      <pre>POST http://localhost:8081/orders</pre>

      <strong>Headers:</strong>
      <pre>Content-Type: application/json</pre>

      <h3>✅ Caso 1 — Orden válida</h3>
      <pre>{
  "id": "ORD-001",
  "amount": 150.50
}</pre>

      <strong>Respuesta esperada:</strong>
      <pre>HTTP 202 ACCEPTED</pre>

      <div class="step">
        Flujo interno:
        <ul>
          <li>Producer recibe la petición</li>
          <li>Publica el evento en Kafka (<code>orders</code>)</li>
          <li>Consumer procesa la orden</li>
          <li>Se publica el estado en <code>orders-status</code></li>
        </ul>
      </div>

      <h3>❌ Caso 2 — Orden inválida</h3>
      <pre>{
  "id": "ORD-002",
  "amount": -10
}</pre>

      <strong>Importante:</strong>
      <ul>
        <li>El Producer igual responde <code>202</code> porque el mensaje fue enviado correctamente.</li>
        <li>El rechazo ocurre en el Consumer.</li>
        <li>El mensaje se envía automáticamente al <code>orders-dead-letter</code>.</li>
      </ul>
    </div>

    <div class="card">
      <h2>📊 Topics</h2>
      <table>
        <tr><th>Topic</th><th>Descripción</th></tr>
        <tr><td>orders</td><td>Eventos enviados</td></tr>
        <tr><td>orders-status</td><td>Resultados del procesamiento</td></tr>
        <tr><td>orders-dead-letter</td><td>Mensajes fallidos</td></tr>
      </table>
    </div>

    <div class="card">
      <h2>🛑 Detener el Ambiente</h2>
      <pre>docker compose down</pre>

      Para eliminar volúmenes (reset total):
      <pre>docker compose down -v</pre>
    </div>

    <p class="footer">
      Arquitectura moderna ⚡ Event Driven • Microservices • Kafka • Quarkus
    </p>

  </div>
</body>
</html>
