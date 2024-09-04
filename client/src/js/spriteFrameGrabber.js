import * as PIXI from 'pixi.js';
import spriteSheetURL from '../../assets/testC.png';

const SPRITE_HEIGHT = 96;
const SPRITE_WIDTH = 48;
const baseTexture = PIXI.BaseTexture.from(spriteSheetURL);

const fetchFrame = (x, y, width, height) => {
    const rectangle = new PIXI.Rectangle(x, y, width, height);
    return new PIXI.Texture(baseTexture, rectangle);
}

/**
 *
 * @param direction
 * @returns {Texture<Resource>}
 */
export const fetchIdle = (direction = "DOWN") => {
    direction = direction.toUpperCase()
    let offsetNum;
    switch (direction) {
        case "RIGHT":
            offsetNum = 0;
            break;
        case "UP":
            offsetNum = 1;
            break;
        case "LEFT":
            offsetNum = 2;
            break;
        case "DOWN":
            offsetNum = 3;
            break;
        default:
            offsetNum = 3;
    }

    const xOffset = offsetNum * SPRITE_WIDTH
    const yOffset = 0
    return fetchFrame(xOffset, yOffset, SPRITE_WIDTH, SPRITE_HEIGHT)
}

