import * as PIXI from 'pixi.js';
import spriteSheetURL from '../../assets/testC.png';

const SPRITE_HEIGHT = 96;
const SPRITE_WIDTH = 48;
const SHEET_GAP = 20
const baseTexture = PIXI.BaseTexture.from(spriteSheetURL);

const fetchFrame = (x, y, width, height) => {
    const rectangle = new PIXI.Rectangle(x, y, width, height);
    return new PIXI.Texture(baseTexture, rectangle);
}

const directionValue = (direction) => {
    switch (direction) {
        case "RIGHT": return 0;
        case "UP": return 1;
        case "LEFT": return 2;
        case "DOWN": return 3;
        default: return 3;
    }
}

/**
 *
 * @param direction
 * @returns {Texture<Resource>}
 */
export const fetchStillIdle = (direction = "DOWN") => {
    direction = direction.toUpperCase()
    let offsetNum = directionValue(direction)

    const xOffset = offsetNum * SPRITE_WIDTH
    const yOffset = 0
    return fetchFrame(xOffset, yOffset, SPRITE_WIDTH, SPRITE_HEIGHT)
}

export const fetchIdle = (direction = "DOWN", frame) => {
    const frameNum = frame % 6

    direction = direction.toUpperCase()
    let offsetNum = directionValue(direction) * (6 * SPRITE_WIDTH)

    const xOffset = offsetNum + (SPRITE_WIDTH * frameNum)
    return fetchFrame(xOffset, SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT)
}

