import { Stomp } from "@stomp/stompjs";

let stompClient;

const connect = async () => {
    const socket = new WebSocket('ws://localhost:8080/stomp-endpoint');
    stompClient = Stomp.over(socket);

    return new Promise((resolve, reject) => {
        stompClient.connect({}, (frame) => {
            console.log('Connected:', frame);
            setupSubscriptions();
            requestState();
            resolve();
        }, (error) => {
            console.error('Connection error:', error);
            stompClient = null;
            reject(error);
        });
    });
};

const setupSubscriptions = () => {
    stompClient.subscribe('/topic/greetings', ({ body }) => {
        console.log(body)
    });

    stompClient.subscribe('/topic/state', ({ body }) => {
        console.log(JSON.parse(body))
    });
};

export const requestState = () => {
    stompClient.send('/app/getState');
};

export { connect };
