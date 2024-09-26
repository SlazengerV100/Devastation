import { Stage, Sprite } from '@pixi/react';
import { useAtomValue } from 'jotai';
import { playerMap } from "../js/atoms.js";
import map from '../../assets/map.png'; // Map image asset
import Player from "../Player.jsx";

const GameStage = () => {
    const players = useAtomValue(playerMap);
    return (
        <Stage options={{ backgroundColor: 0x1099bb }} width={window.innerWidth} height={window.innerHeight}>
            {/* Render the map */}
            <Sprite image={map} x={0} y={0}/>

            {/* Render all players */}
            {Object.values(players).map((player, index) =>  <Player player={player} key={index}/>)}
        </Stage>
    );
};

export default GameStage;


