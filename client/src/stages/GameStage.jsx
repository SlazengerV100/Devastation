import { Stage, Sprite } from '@pixi/react';
import { useAtomValue } from 'jotai';
import { players as playerAtoms } from "../js/atoms.js";
import map from '../../assets/map.png'; // Map image asset
import Player from "../Player.jsx";
import { useState, useEffect } from 'react';
import {TILE_WIDTH } from "../js/spriteFrameGrabber.js";

const GameStage = () => {
    const players = useAtomValue(playerAtoms);

    // Map size (replace with your actual map's width and height)
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


    // Calculate the top-left corner position of the map to center it on the screen
    const mapPosition = {
        x: TILE_WIDTH,
        y: (windowSize.height - MAP_HEIGHT) / 2
    };

    return (
        <Stage
            options={{ backgroundColor: 0xf4f3ef }}
            width={windowSize.width}
            height={windowSize.height}
            style={{ position: 'absolute', top: 0, left: 0 }}
        >
            {/* Render the map centered */}
            <Sprite image={map} x={mapPosition.x} y={mapPosition.y} width={MAP_WIDTH} height={MAP_HEIGHT}/>

            {/* Render all players, positioning them relative to the map */}
            {Object.values(players).map((player, index) => (
                <Player
                    player={player}
                    key={index}
                    mapPosition={mapPosition} // Pass the map position to adjust player rendering
                />
            ))}
        </Stage>
    );
};

export default GameStage;
