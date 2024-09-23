import {Stage, Sprite, AnimatedSprite} from '@pixi/react'; // Import Stage and Sprite from @pixi/react
import { useAtomValue } from 'jotai';
import { localCharacterAtom } from "../js/atoms.js";
import map from '../../assets/map.png'; // Map image asset
import useUpdatePosition from '../hooks/useUpdatePosition.js';
import {textures} from '../js/spriteFrameGrabber.js'

const GameStage = () => {
    const localCharacter = useAtomValue(localCharacterAtom);
    const updatePosition = useUpdatePosition();

    return (
        <Stage
            options={{ backgroundColor: 0x1099bb }}
            width={window.innerWidth}
            height={window.innerHeight}
        >
            <Sprite
                image={map}
                x={window.innerWidth / 2}
                y={window.innerHeight / 2}
                anchor={0.5}

            />
            <AnimatedSprite
                isPlaying={true}
                textures={textures.running.DOWN}
                animationSpeed={0.15}
            />
        </Stage>
    );
};

export default GameStage;
