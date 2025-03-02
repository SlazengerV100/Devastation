# Before Running

Before you can run the client or server, make sure you have the following dependencies installed globally on your machine:

| Dependency | Description                                     |
|------------|-------------------------------------------------|
| **Maven**  | Required for running the backend (server).      |
| **Yarn**   | Used on the client side and for launch scripts. |
| **Node**   | Used on the client side for running scripts.    |

## Set the server environment variable
1. Navigate to the `client` directory
2. Create a file `.env`
3. Write `VITE_WEBSOCKET_URL=[VALUE]` to this file
   - If you are running server locally, use `ws://localhost:8080/stomp-endpoint`
   - If you are connecting to the server running on an AWS EC2 instance, use `ws://[YOUR-AWS-EC2-INSTANCE]:8080/stomp-endpoint`

## Running the server
If you are running both the server and client on the same machine, you will need to open a separate terminal window for each.
1. Navigate to the server/ directory: `cd server`
2. **If you are running the server for the first time, run** `mvn clean install`
3. Run Spring Boot: `mvn spring-boot:run`

The server will start on http://localhost:8080.

## Running the client
1. Navigate to the client directory: `cd client`
2. **If you are running the client for the first time, run** `yarn install`
3. Run the development server: `yarn run dev`

The client will be available at http://localhost:3000.

## Running the client and server simultaenously
If you are hosting the game and playing it, you can run the client and server using a single command.
1. Using the provided script in the `package.json`, from the project root directory, run `yarn run start-all`

This will start both the client and server on the following endpoints:
- Client: http://localhost:5173
- Server: http://localhost:8080
