// GameStage.jsx
import React from 'react';
import useUpdatePosition from '../hooks/useUpdatePosition';
import { useAtomValue } from 'jotai';
import {localCharacterAtom} from "../js/atoms.js";

const GameStage = () => {
    const updatePosition = useUpdatePosition();
    const localCharacter = useAtomValue(localCharacterAtom)

    return (
        <div>
            this is a gameStage
            <div>
                <h2>{localCharacter.playerName} X: {localCharacter.x}, Y: {localCharacter.y}</h2>
            </div>
            <div>
                <button onClick={() => {
                    updatePosition("UP");
                }}>UP
                </button>
                <button onClick={() => {
                    updatePosition("DOWN");
                }}>DOWN
                </button>
                <button onClick={() => {
                    updatePosition("LEFT");
                }}>LEFT
                </button>
                <button onClick={() => {
                    updatePosition("RIGHT");
                }}>RIGHT
                </button>
            </div>
        </div>
    );
};

export default GameStage;

