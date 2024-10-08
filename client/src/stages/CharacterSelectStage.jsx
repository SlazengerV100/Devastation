import { useState, useEffect } from "react";
import { Stage, Container, Text, AnimatedSprite, Sprite } from '@pixi/react';
import { textures } from "../js/spriteFrameGrabber.js";
import { localPlayerId } from "../js/atoms.js";
import { requestState, activatePlayer } from "../managers/connectionManager.js";
import { store } from "../App.jsx";
import backgroundURL from '../../assets/SelectPlayerBackground.png';
import * as PIXI from 'pixi.js';

const CharacterSelectStage = () => {
    const [playerMap, setPlayerMap] = useState([]);
    const [loading, setLoading] = useState(true);
    const [windowSize, setWindowSize] = useState({
        width: window.innerWidth,
        height: window.innerHeight
    });

    useEffect(() => {
        updatePlayerSelection();

        const handleResize = () => {
            setWindowSize({
                width: window.innerWidth,
                height: window.innerHeight
            });
        };
        window.addEventListener("resize", handleResize);

        // Cleanup the event listener on component unmount
        return () => window.removeEventListener("resize", handleResize);
    }, []);

    const updatePlayerSelection = async () => {
        setLoading(true);
        try {
            const playerMap = await requestState();
            setPlayerMap(playerMap);
        } catch (error) {
            console.error('Failed to fetch players:', error);
        } finally {
            setLoading(false);
        }
    };

    const setSessionPlayer = async (playerId) => {
        try {
            const player = await activatePlayer(playerId);
            sessionStorage.setItem('playerID', player.id);
            store.set(localPlayerId, player.id);
            await updatePlayerSelection();
        } catch (error) {
            console.error('Failed to activate player:', error);
        }
    };

    // Constants for layout based on window size
    const stageWidth = windowSize.width;
    const stageHeight = windowSize.height;
    const buttonYPosition = stageHeight * 0.8;
    const characterYPosition = stageHeight * 0.4;
    const buttonWidth = stageWidth * 0.15;
    const characterScale = stageWidth * 0.001;

    return (
        <div>
            {loading ? (
                <p>Loading players...</p>
            ) : (
                <Stage
                    width={stageWidth}
                    height={stageHeight}
                    options={{ backgroundColor: 0x1099bb }}
                >
                    {/* Background */}
                    <Container>
                        <Sprite texture={PIXI.Texture.from(backgroundURL)} width={stageWidth}/>
                    </Container>

                    {/* Characters running above buttons */}
                    <Container>
                        {playerMap.map((player, index) => {
                            const xPosition = (index + 1) * (stageWidth / (playerMap.length + 1)) - buttonWidth / 2;

                            return (
                                <Container key={player.id}>
                                    {/* Animated character running in place */}
                                    <AnimatedSprite
                                        textures={textures[player.role].running["DOWN"]}
                                        animationSpeed={0.15}
                                        isPlaying={true}
                                        x={xPosition}
                                        y={characterYPosition}
                                        scale={{ x: characterScale, y: characterScale }}
                                    />

                                    {/* Player select button */}
                                    <Text
                                        text={player.role}
                                        style={{
                                            fontSize: stageWidth * 0.03,
                                            fill: player.active ? 0xAAAAAA : 0xFFFFFF,
                                            align: 'center',
                                        }}
                                        interactive={true}
                                        buttonMode={true}
                                        x={xPosition + buttonWidth / 2}
                                        y={buttonYPosition}
                                        anchor={0.5}
                                        pointerdown={() => setSessionPlayer(player.id)}
                                    />
                                </Container>
                            );
                        })}
                    </Container>
                </Stage>
            )}
        </div>
    );
};

export default CharacterSelectStage;
