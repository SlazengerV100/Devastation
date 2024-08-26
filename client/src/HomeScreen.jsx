import React from 'react';
import useWebSocket from "./hooks/useWebSocket.jsx";

const HomeScreen = ({connect}) => {


    const connectToSocket = () => {
        // Example WebSocket connection code
        console.log("Connecting...");

        if (connect() == null){
            console.log("Error connecting to socket");
        }

    };

    return (
        <div
            style={{
                width: '100vw',
                height: '100vh',
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                padding: '20px',
                textAlign: 'center' // Ensures that the text inside the container is centered
            }}
        >
            <h1 style={{ fontSize: '3rem', marginBottom: '20px' }}>Dev-A-Station</h1>
            <button
                onClick={connectToSocket}
                style={{
                    padding: '10px 20px',
                    fontSize: '1.2rem',
                    cursor: 'pointer',
                    backgroundColor: '#007bff',
                    color: 'white',
                    border: 'none',
                    borderRadius: '5px'
                }}
            >
                Connect to Socket
            </button>
        </div>
    );
};

export default HomeScreen;



