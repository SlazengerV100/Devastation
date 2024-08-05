# Justification for the Use of Technologies in Dev-a-station

## Chosen Technologies

1. **Java**
2. **Spring Boot (Java Backend)**
3. **JavaScript with a Graphics Library (Pixi.js)**
4. **Vite (Build Tool)**

## Justification

### 1. Java for backend (Programming language)
 
**Rationale:**
- **Familiarity:** All members of the development group are familiar with Java, which facilitates smoother collaboration and reduces the learning curve, leading to more efficient development.
- **Robust Ecosystem:** Java has a vast ecosystem with extensive libraries and frameworks, which can enhance productivity and reduce development time.
- **Performance and object oriented:** Java provides high performance and is suitable for handling the complex logic and real-time processing required by the game. It is also object oriented which lends itself to handling the logic of our game which we have been designing in an onbject oriented context.

### 2. Spring Boot (Java Framework)

**Rationale:**
- **RESTful API and HTTP Requests:** Spring Boot enables us to host a server written in Java with a RESTful API, simplifying the handling of HTTP requests. It provides a standardised way to expose the game's functionalities and data while streamlining the development of RESTful services for interaction between the frontend and backend by removing a lot of the complexities that would come from implementing an endpoint with socket server apis from scratch.
- **Robustness and Scalability:** Spring Boot provides a solid foundation for building scalable and robust applications. It is well-suited for handling the game state and logic, ensuring that the game can manage multiple players and complex interactions efficiently.
- **Microservices Architecture:** Spring Boot's support for microservices architecture allows for modular and maintainable code, which is crucial for a project that may expand or need updates over time.
- **Integration with Databases:** Spring Boot integrates seamlessly with various databases, allowing efficient management of game data, such as player progress, game states, and statistics.

### 3. JavaScript (Programming language)

**Rationale:**
- **Cross-Platform Compatibility:** JavaScript is universally supported across web browsers, ensuring that the game can be accessed by students on various devices without the need for additional software.
- **Real-Time Updates:** JavaScript, combined with WebSockets, allows for real-time updates and interactions, providing a seamless multiplayer experience where players can see changes and actions instantly.

### 4. Pixi.js (Graphics Library)

**Rationale:**
- **Interactive and Dynamic Graphics:** Pixi.js is a powerful graphics rendering library that enables the creation of interactive and dynamic visuals. This is crucial for engaging first-year students and providing a visually appealing learning experience.
- **Enhanced Performance:** Pixi.js offers high-performance graphics rendering, ensuring smooth and responsive visual elements in the game.
- **Well-Maintained and Documented:** Pixi.js is well-maintained with a strong community, comprehensive documentation, and active updates, which helps in troubleshooting and implementing features efficiently.
- **Free to Use:** Pixi.js is an open-source library, which means it is free to use and integrate into the project, reducing development costs.


### 4. Vite (Build Tool)

**Rationale:**
- **Fast Development:** Vite is a modern build tool that provides lightning-fast hot module replacement (HMR) and a rapid development environment, enhancing developer productivity.
- **Optimised Builds:** Vite produces highly optimized and performant builds, which is essential for delivering a smooth user experience in the game.
- **Ease of Use:** Vite's straightforward configuration and support for modern JavaScript features streamline the development process, reducing setup time and complexity.

## Considered Technologies

### Node.js (Backend)

**Evaluation:**
- **Pros:** Node.js is known for its non-blocking, event-driven architecture, which can handle multiple simultaneous connections efficiently. It’s also well-suited for building scalable network applications.
- **Decision:** Although Node.js is a powerful option for backend development, the team decided to use Spring Boot for its robust ecosystem and familiarity among team members.

### Socket.io (Multiplayer)

**Evaluation:**
- **Pros:** Socket.io provides real-time, bi-directional communication between clients and servers, making it a strong candidate for implementing multiplayer features.
- **Decision:** While Socket.io is well-suited for real-time communication it cannot intergrate with Spring. The team opted to use WebSockets directly with Spring Boot to streamline integration and leverage existing Java expertise.

### Firebase (Database)

**Evaluation:**
- **Pros:** Firebase offers real-time database capabilities, easy-to-use SDKs, and built-in authentication, which can simplify backend development and data management.
- **Decision:** The team realised that storing game data in a database was not necessary for the project’s objectives. Therefore, they chose to handle game state management within the application logic instead of using Firebase. There have been ideas to store some perstant data such as pre configured game levels and high scores. If we do end up needing to store and retrieve persistant storage, we will most. likely use Firebase



