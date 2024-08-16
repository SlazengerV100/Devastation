import io from 'socket.io-client';

const socket = io('http://localhost:9092');

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
    socket.emit('move', { direction: 'up' });
}

function moveDown() {
    socket.emit('move', { direction: 'down' });
}

function moveLeft() {
    socket.emit('move', { direction: 'left' });
}

function moveRight() {
    socket.emit('move', { direction: 'right' });
}

// Update the ball position on the screen
function updateBallPosition(x, y) {
    const ball = document.querySelector('.circle');
    ball.style.left = `${x}px`;
    ball.style.top = `${y}px`;
}

// Attach button events
document.getElementById('up').addEventListener('click', moveUp);
document.getElementById('down').addEventListener('click', moveDown);
document.getElementById('left').addEventListener('click', moveLeft);
document.getElementById('right').addEventListener('click', moveRight);
