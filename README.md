# 🚀 Planilla Quarkus + Kafka

### Arquitectura basada en eventos para procesamiento asincrónico de órdenes

![Java](https://img.shields.io/badge/Java-21-red)
![Quarkus](https://img.shields.io/badge/Quarkus-3.x-blue)
![Kafka](https://img.shields.io/badge/Apache-Kafka-black)
![Docker](https://img.shields.io/badge/Docker-Compose-blue)
![License](https://img.shields.io/badge/license-MIT-green)

------------------------------------------------------------------------

## 📌 Descripción

Este proyecto demuestra cómo construir un **sistema distribuido
moderno** utilizando **Apache Kafka** y **Quarkus**, implementando
comunicación asincrónica entre microservicios mediante eventos.

Simula un flujo real de procesamiento de órdenes donde:

✅ Un Producer recibe solicitudes HTTP\
✅ Publica eventos en Kafka\
✅ Un Consumer procesa las órdenes\
✅ Se notifica el resultado mediante otro topic\
✅ Soporta Dead Letter Queue para errores

👉 Ideal como proyecto de **portafolio backend** para demostrar
conocimientos en:

-   Microservicios\
-   Event Driven Architecture\
-   Mensajería con Kafka\
-   Programación reactiva\
-   Docker

------------------------------------------------------------------------

## 🧠 Arquitectura

    Kafka (Topic: orders)

    [ Producer ] --POST /orders--> [ orders ] -----> [ Consumer ]
       :8081                                      :8082
                                                      |
                         Kafka (orders-status)        |
    [ Producer ] <----------- status <---------------|

------------------------------------------------------------------------

## 🧩 Microservicios

### 📤 Producer

-   Endpoint `POST /orders`
-   Publica eventos en Kafka
-   Configurado con `acks=all`
-   Escucha estados desde `orders-status`
-   Retorna **HTTP 202 ACCEPTED**

**Puerto:** `8081`

------------------------------------------------------------------------

### 📥 Consumer

-   Consume eventos desde `orders`
-   Aplica reglas de negocio
-   Publica resultados
-   Manejo de errores
-   Dead Letter Queue
-   Reintentos automáticos

**Puerto:** `8082`

------------------------------------------------------------------------

## 🛠️ Tecnologías

  Tecnología       Versión
  ---------------- ---------
  Java             21
  Quarkus          3.x
  Apache Kafka     3.x
  Maven            3.9+
  Docker Compose   3.8

### Dependencias principales

-   Quarkus REST
-   Reactive Messaging
-   Kafka Client
-   Jackson
-   CDI

------------------------------------------------------------------------

## ✅ Prerrequisitos

Antes de iniciar asegúrate de tener instalado:

-   ✅ Java 21\
-   ✅ Docker Desktop\
-   ✅ Maven (o Maven Wrapper)\
-   ✅ Git

Verifica:

``` bash
java -version
docker --version
```

------------------------------------------------------------------------

# 🐳 Levantar el Ambiente (IMPORTANTE)

## 1️⃣ Clonar el repositorio

``` bash
git clone https://github.com/Javier-Ayala-Alvarez/planilla-quarkus-kafka.git
```

------------------------------------------------------------------------

## 2️⃣ Posicionarse en la carpeta raíz

Debes estar donde existe el archivo:

    docker-compose.yml

Ejemplo:

``` bash
cd planilla-quarkus-kafka
```

Verifica:

``` bash
ls
```

Debe aparecer:

    docker-compose.yml
    quarkus-kafka-produce
    quarkus-kafka-consumer

------------------------------------------------------------------------

## 3️⃣ Levantar Kafka

``` bash
docker compose up -d
```

Esto iniciará:

✅ Kafka Broker\
✅ Zookeeper (si aplica)\
✅ Kafdrop (UI web)

------------------------------------------------------------------------

## 4️⃣ Verificar contenedores

``` bash
docker compose ps
```

------------------------------------------------------------------------

## 5️⃣ Acceder a la UI de Kafka

Abre tu navegador:

👉 http://localhost:9000

Desde aquí puedes visualizar:

-   Topics\
-   Mensajes\
-   Brokers

------------------------------------------------------------------------

# ▶️ Ejecutar los Microservicios

## Producer

Nueva terminal:

``` bash
cd quarkus-kafka-produce
./mvnw quarkus:dev
```

Windows:

``` bash
mvnw.cmd quarkus:dev
```

------------------------------------------------------------------------

## Consumer

Otra terminal:

``` bash
cd quarkus-kafka-consumer
./mvnw quarkus:dev
```

⚠️ Ejecuta ambos en terminales separadas.

------------------------------------------------------------------------

# 🧪 Pruebas con Postman / Insomnia

## Endpoint

    POST http://localhost:8081/orders

### Headers

    Content-Type: application/json

------------------------------------------------------------------------

## ✅ Orden válida

``` json
{
  "id": "ORD-001",
  "amount": 150.50
}
```

**Respuesta esperada:**

    HTTP 202 ACCEPTED

### Flujo interno:

1.  Producer recibe la petición\
2.  Publica en `orders`\
3.  Consumer procesa\
4.  Publica estado en `orders-status`

------------------------------------------------------------------------

## ❌ Orden inválida

``` json
{
  "id": "ORD-002",
  "amount": -10
}
```

📌 Importante:

-   El Producer responderá **202**
-   El error ocurre en el Consumer
-   El mensaje se envía automáticamente al:

```{=html}
<!-- -->
```
    orders-dead-letter

------------------------------------------------------------------------

# 📊 Topics

  Topic                Descripción
  -------------------- -----------------------------
  orders               Eventos enviados
  orders-status        Resultado del procesamiento
  orders-dead-letter   Mensajes fallidos

------------------------------------------------------------------------

# 🛑 Detener el Ambiente

``` bash
docker compose down
```

### Reset total (elimina volúmenes)

``` bash
docker compose down -v
```

------------------------------------------------------------------------

# 📁 Estructura del Proyecto

    planilla-quarkus-kafka
    │
    ├── docker-compose.yml
    ├── quarkus-kafka-produce
    └── quarkus-kafka-consumer

------------------------------------------------------------------------

# ⭐ Por qué este proyecto es valioso

Este tipo de arquitectura es utilizada por empresas que manejan **alto
volumen de datos**, como:

-   Fintech\
-   E-commerce\
-   Bancos\
-   Sistemas de logística

Demuestra habilidades reales de ingeniería backend moderna.
