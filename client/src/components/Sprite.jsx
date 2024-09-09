import {Container, Sprite, Text} from "@pixi/react";
import React from "react";
import {fetchIdle, fetchRunning, fetchSitting} from "../js/spriteFrameGrabber.js";

const Player = ({ player, frame }) => {
    console.log(player)
    const fetchFrame = (state, direction) => {
        switch(state) {
            case "IDLE":
                return fetchIdle(direction, frame);
            case "MOVING":
                return fetchRunning(direction, frame);
            case "WORKING":
                return fetchSitting(direction, frame); // Limited valid directions for sitting
            default:
                console.warn(`STATE: ${state} IS INVALID`)
                console.log(frame)
                return fetchIdle(direction, frame); // Fallback to idle frame if state is invalid
        }
    };

    const playerTexture = fetchFrame(player.state, player.direction);

    return (
        <Container>
            <Sprite
                texture={playerTexture}
                x={player.x}
                y={player.y}
            />
            <Text
                text={player.name}
                x={player.x}
                y={player.y + 85}
                style={{
                    fontFamily: 'Arial',
                    fontSize: 14,
                    fill: '#000000',
                    align: 'center',
                }}
            />
        </Container>
    );
};

export default Player