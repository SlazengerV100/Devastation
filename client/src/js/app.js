import { io } from 'socket.io-client';

const socket = io('http://localhost:9092'); // Replace with your server's URL and port

// Handle connection
socket.on('connect', () => {
    console.log('Connected to the server');
});

// Handle disconnection
socket.on('disconnect', () => {
    console.log('Disconnected from the server');
});

// Handle receiving greetings from the server
socket.on('greeting', (data) => {
    console.log('Received greeting: ' + data.content);
    showGreeting(data.content);
});

// Handle receiving state updates from the server
socket.on('stateUpdate', (state) => {
    console.log('Received state update: ', state);
    showState(state);
});

// Send a greeting message to the server
function sendName() {
    const name = document.getElementById('name').value;
    socket.emit('message', { name });
}

// Show the greeting in the UI
function showGreeting(message) {
    const greetingsElement = document.getElementById('greetings');
    const newRow = greetingsElement.insertRow();
    const cell = newRow.insertCell(0);
    cell.textContent = message;
}

// Send move commands to the server
function moveUp() {
    socket.emit('move', { x: currentX, y: currentY - 10 });
}

function moveDown() {
    socket.emit('move', { x: currentX, y: currentY + 10 });
}

function moveLeft() {
    socket.emit('move', { x: currentX - 10, y: currentY });
}

function moveRight() {
    socket.emit('move', { x: currentX + 10, y: currentY });
}

// Update the circle's position based on the state
function showState(state) {
    const circle = document.querySelector('.circle');
    currentX = state.x;
    currentY = state.y;
    circle.style.left = `${state.x}px`;
    circle.style.top = `${state.y}px`;
}

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('connect').addEventListener('click', () => {
        socket.connect();
    });

    document.getElementById('disconnect').addEventListener('click', () => {
        socket.disconnect();
    });

    document.getElementById('send').addEventListener('click', sendName);
    document.getElementById('up').addEventListener('click', moveUp);
    document.getElementById('down').addEventListener('click', moveDown);
    document.getElementById('left').addEventListener('click', moveLeft);
    document.getElementById('right').addEventListener('click', moveRight);
});
