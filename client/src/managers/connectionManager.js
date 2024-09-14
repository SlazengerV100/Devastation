import { Stomp } from "@stomp/stompjs";

let stompClient;

const connect = async () => {
    const socket = new WebSocket('ws://localhost:8080/stomp-endpoint');
    stompClient = Stomp.over(socket);

    return new Promise((resolve, reject) => {
        stompClient.connect({}, (frame) => {
            console.log('Connected: ' + frame);
            setupSubscriptions();
            requestState();
            resolve();
        }, (error) => {
            console.error('Connection error:', error);
            stompClient = null; // Clean up client
            reject(error);
        });
    });
};

const setupSubscriptions = () => {
    stompClient.subscribe('/topic/greetings', (greeting) => {
        const message = JSON.parse(greeting.body);
        showGreeting(message);
    });

    stompClient.subscribe('/topic/state', (stateUpdate) => {
        const state = JSON.parse(stateUpdate.body);
    });
};

function showGreeting(message) {
    return ("#greetings").append("<tr><td>" + message.message + "</td></tr>");
}

const requestState = () => {
    stompClient.send('/app/getState', {});
};

export const disconnect = () => {

}

export { connect };
