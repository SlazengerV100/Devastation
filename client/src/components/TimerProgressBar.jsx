import {Sprite, Text} from '@pixi/react';
import { TILE_WIDTH } from "../js/spriteFrameGrabber.js";

const TimerProgressBar = ({timeLeft, progressBarDrawInfo}) => {
    const { x, y, width, height } = progressBarDrawInfo;


    return (
        <Text
            text={timeLeft}
            x={x}
            y={y}
            width={width}
            height={height}
        />
    );
};

export default TimerProgressBar;
