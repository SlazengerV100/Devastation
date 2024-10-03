import { Stage, Sprite, Text } from '@pixi/react';
import { useAtomValue } from 'jotai';
import {localHeldTicket, players as playerAtoms, ticketsAtom, timeLeftAtom} from "../js/atoms.js";
import map from '../../assets/map.png'; // Map image asset
import Player from "../components/Player.jsx";
import { useState, useEffect } from 'react';
import {TILE_WIDTH } from "../js/spriteFrameGrabber.js";
import Ticket from "../components/Ticket.jsx";
import HeldTicket from "../components/HeldTicket.jsx";
import TimerProgressBar from "../components/TimerProgressBar.jsx"


const GameStage = () => {
    const players = useAtomValue(playerAtoms);
    const tickets = useAtomValue(ticketsAtom)
    const heldTicket = useAtomValue(localHeldTicket)
    const timeLeft = useAtomValue(timeLeftAtom)

    useEffect(() => {
        console.log(tickets)
    }, [tickets]);


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

    const mapDrawInfo = {
        x: TILE_WIDTH,
        y: (windowSize.height - (15 * TILE_WIDTH)) / 2,
        width: 30 * TILE_WIDTH,
        height: 15 * TILE_WIDTH
    };

    const progressBarDrawInfo = {
        x: TILE_WIDTH,
        y: TILE_WIDTH*2,
        width: 30 * TILE_WIDTH,
        height: TILE_WIDTH
    }

    console.log("Tickets to render: ")

    return (
            <Stage
                options={{backgroundColor: 0xf4f3ef}}
                width={windowSize.width}
                height={windowSize.height}
                style={{position: 'absolute', top: 0, left: 0}}
            >
                <TimerProgressBar timeLeft={timeLeft} progressBarDrawInfo={progressBarDrawInfo}/>
                {/* Render the map centered */}
                <Sprite image={map} x={mapDrawInfo.x} y={mapDrawInfo.y} width={mapDrawInfo.width} height={mapDrawInfo.h}/>

                {/* Render all players*/}
                {Object.values(players).map((player, index) => (
                    <Player
                        player={player}
                        key={index}
                        mapPosition={mapDrawInfo}
                    />
                ))}

                {/* Render all tickets*/}
                {Object.values(tickets).map((ticket, index) => (
                    <Ticket
                        ticket={ticket}
                        key={index}
                        mapPosition={mapDrawInfo}
                    />
                ))}
                <HeldTicket mapPosition={mapDrawInfo} mapWidth={mapDrawInfo.width} heldTicket = {heldTicket}/>
            </Stage>

    );
};

export default GameStage;
