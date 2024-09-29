import { Stage, Sprite, Text } from '@pixi/react';
import { useAtomValue } from 'jotai';
import { players as playerAtoms, ticketsAtom } from "../js/atoms.js";
import map from '../../assets/map.png'; // Map image asset
import Player from "../components/Player.jsx";
import { useState, useEffect } from 'react';
import {TILE_WIDTH } from "../js/spriteFrameGrabber.js";
import Ticket from "../components/Ticket.jsx";
import HeldTicket from "../components/HeldTicket.jsx";


const GameStage = () => {
    const players = useAtomValue(playerAtoms);
    const tickets = useAtomValue(ticketsAtom)

    const MAP_WIDTH = 30 * TILE_WIDTH;
    const MAP_HEIGHT = 15 * TILE_WIDTH; // Example height

    const [windowSize, setWindowSize] = useState({
        width: window.innerWidth,
        height: window.innerHeight
    });

    // Handle window resize
    useEffect(() => {
        const handleResize = () => {
            setWindowSize({
                width: window.innerWidth,
                height: window.innerHeight
            });
        };

        window.addEventListener("resize", handleResize);

        return () => {
            window.removeEventListener("resize", handleResize);
        };
    }, []);

    const mapPosition = {
        x: TILE_WIDTH,
        y: (windowSize.height - MAP_HEIGHT) / 2
    };

    return (
            <Stage
                options={{backgroundColor: 0xf4f3ef}}
                width={windowSize.width}
                height={windowSize.height}
                style={{position: 'absolute', top: 0, left: 0}}
            >
                {/* Render the map centered */}
                <Sprite image={map} x={mapPosition.x} y={mapPosition.y} width={MAP_WIDTH} height={MAP_HEIGHT}/>

                {/* Render all players*/}
                {Object.values(players).map((player, index) => (
                    <Player
                        player={player}
                        key={index}
                        mapPosition={mapPosition}
                    />
                ))}

                {/* Render all tickets*/}
                {Object.values(tickets).map((ticket, index) => (
                    <Ticket
                        ticket={ticket}
                        key={index}
                        mapPosition={mapPosition}
                    />
                ))}
                <HeldTicket mapPosition={mapPosition} mapWidth={MAP_WIDTH}/>
            </Stage>

    );
};

export default GameStage;
