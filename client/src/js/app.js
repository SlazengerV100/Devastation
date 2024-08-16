import io from 'socket.io-client';

const socket = io('http://localhost:9092');

let currentX = 0;
let currentY = 0;
const ballSize = 50; // Size of the ball
const canvasSize = 500; // Size of the canvas

// Handle connection
socket.on('connect', () => {
    console.log('Connected to the server');
});

// Handle disconnection
socket.on('disconnect', () => {
    console.log('Disconnected from the server');
});

// Handle state updates from the server
socket.on('stateUpdate', (state) => {
    console.log('Received state update: ', state);
    updateBallPosition(state.x, state.y);
});

// Functions to move the ball
function moveUp() {
    if (currentY > 0) {
        currentY -= 20;
        updateState(currentX, currentY);
    }
}

function moveDown() {
    if (currentY < canvasSize - ballSize) {
        currentY += 20;
        updateState(currentX, currentY);
    }
}

function moveLeft() {
    if (currentX > 0) {
        currentX -= 20;
        updateState(currentX, currentY);
    }
}

function moveRight() {
    if (currentX < canvasSize - ballSize) {
        currentX += 20;
        updateState(currentX, currentY);
    }
}

// Update the server with the new state
function updateState(x, y) {
    socket.emit('move', { x, y });
}

// Update the ball position on the screen
function updateBallPosition(x, y) {
    const ball = document.querySelector('.circle');
    ball.style.left = `${x}px`;
    ball.style.top = `${y}px`;
    currentX = x;
    currentY = y;
}

// Attach button events
document.getElementById('up').addEventListener('click', moveUp);
document.getElementById('down').addEventListener('click', moveDown);
document.getElementById('left').addEventListener('click', moveLeft);
document.getElementById('right').addEventListener('click', moveRight);
