var stompClient = null;
let currentX = 0;
let currentY = 0;
const ballSize = 50; // Size of the ball
const canvasSize = 500; // Size of the canvas

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

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/stomp-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body));
        });
        stompClient.subscribe('/topic/state', function (stateUpdate) {
            var state = JSON.parse(stateUpdate.body);
            showState(state);
        });

        // Request the current state
        requestState();
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message.message + "</td></tr>");
}

function updateState(x, y) {
    stompClient.send("/app/setState", {}, JSON.stringify({x: x, y: y}));
}

function requestState() {
    stompClient.send("/app/getState", {});
}

function showState(state) {
    document.getElementById('x-coordinate').innerText = state.x;
    document.getElementById('y-coordinate').innerText = state.y;
    updateCircle(state.x, state.y);

    currentX = state.x;
    currentY = state.y;
}

function updateCircle(x, y) {
    // Select the circle element
    let circle = document.querySelector('.circle');

    // Get the canvas dimensions
    let canvas = document.querySelector('.canvas');
    let canvasWidth = canvas.clientWidth;
    let canvasHeight = canvas.clientHeight;

    // Ensure x and y are within the canvas boundaries
    let circleSize = 50; // Same as circle width and height
    x = Math.max(0, Math.min(x, canvasWidth - circleSize));
    y = Math.max(0, Math.min(y, canvasHeight - circleSize));

    // Update the circle's position
    circle.style.left = x + 'px';
    circle.style.top = y + 'px';
}


$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function() { connect(); });
    $("#disconnect").click(function() { disconnect(); });
    $("#send").click(function() { sendName(); });

    $("#up").click(function() { moveUp(); });
    $("#down").click(function() { moveDown(); });
    $("#left").click(function() { moveLeft(); });
    $("#right").click(function() { moveRight(); });

});








