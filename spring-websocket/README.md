# Spring WebSocket Application

This project is a simple WebSocket application built with Spring Boot. It demonstrates real-time communication using WebSockets with STOMP (Streaming Text Oriented Messaging Protocol).

## Overview

The application consists of:
- **A Spring Boot backend** that handles WebSocket connections and messaging.
- **A frontend HTML page** that interacts with the WebSocket server to send and receive messages.

### What It Does

The app provides a basic chat interface where users can:
1. Connect to a WebSocket server.
2. Send a greeting message with their name.
3. Receive a personalized greeting message from the server in real-time.
4. Allows users to control the position of a red circle. The circles position is stored in the server.

### Components

1. **Backend (`SpringWebsocketApplication`, `WebSocketConfiguration`, `GreetingController`)**:
    - **`SpringWebsocketApplication`**: The main entry point of the application.
    - **`WebSocketConfiguration`**: Configures WebSocket support and STOMP messaging.
    - **`GreetingController`**: Handles incoming messages and sends responses.

2. **Frontend (`index.html`, `app.js`)**:
    - **`index.html`**: Provides a user interface for connecting, sending, and receiving messages.
    - **`app.js`**: Contains JavaScript code to handle WebSocket connections and interactions.

## Dependencies

The project uses the following dependencies:

- **`spring-boot-starter-web`**: Provides  components for building a web application, including RESTful APIs and embedded web server capabilities.
- **`spring-boot-starter-websocket`**: Adds WebSocket support to the Spring Boot application, enabling real-time communication.
- **`webjars-locator-core`**: Helps in locating web assets (JavaScript, CSS) packaged as WebJars.
- **`sockjs-client`**: Provides a JavaScript library that offers WebSocket-like functionality over various transport protocols for better cross-browser support.
- **`stomp-websocket`**: A JavaScript library that implements the STOMP protocol, enabling messaging between the client and server over WebSockets.
- **`bootstrap`**: A front-end framework for designing responsive and visually appealing web interfaces.
- **`jquery`**: A JavaScript library that simplifies DOM manipulation, event handling, and Ajax interactions.

## Requirements

- **Java 17**: Make sure you have Java 17 installed on your machine.
- **Maven**: Used for building and managing project dependencies.

## Running the Application

### Build the project

Ensure you are in the project directory (directory this file is in and pom.xml is in) and run:

```agsl
mvn clean install
```

### Run the application
To start the Spring Boot application, use:

```agsl
mvn spring-boot:run
```

### Access the Application

Open a web browser and navigate to `http://localhost:8080`. You should see the WebSocket chat interface where you can:

- **Connect** to the WebSocket server.
- **Enter your name** and click "Send" to receive a personalized greeting.
- **Disconnect** from the WebSocket server.

### How It Works

1. **WebSocket Connection**:
   - The frontend connects to the WebSocket server at `/stomp-endpoint` using SockJS and STOMP.

2. **Sending Messages**:
   - When you click "Send," your name is sent to the server at the `/app/hello` endpoint.

3. **Receiving Messages**:
   - The server processes the incoming message and sends a personalized greeting to the `/topic/greetings` endpoint.
   - The frontend displays the received greeting message in the table.

