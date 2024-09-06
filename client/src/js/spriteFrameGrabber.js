import * as PIXI from 'pixi.js';
import spriteSheetURL from '../../assets/testC.png';

const SPRITE_HEIGHT = 96;
const SPRITE_WIDTH = 48;
const SPRITES_PER_DIRECTION = 6; // Number of frames per animation
const DAMAGE_FRAMES = 3;
const baseTexture = PIXI.BaseTexture.from(spriteSheetURL);

// Maps direction to its corresponding row in the sprite sheet
const directionValue = (direction) => {
    switch (direction.toUpperCase()) {
        case "RIGHT": return 0;
        case "UP": return 1;
        case "LEFT": return 2;
        case "DOWN": return 3;
        default: return 1;
    }
}

// General function to fetch a sprite frame
const fetchFrame = (direction, frame, yOffsetMultiplier) => {
    const directionOffset = directionValue(direction);
    const xOffset = (directionOffset * SPRITES_PER_DIRECTION * SPRITE_WIDTH) + (frame * SPRITE_WIDTH);
    const yOffset = yOffsetMultiplier * SPRITE_HEIGHT;

    return new PIXI.Texture(baseTexture, new PIXI.Rectangle(xOffset, yOffset, SPRITE_WIDTH, SPRITE_HEIGHT));
}

// Fetch the "still idle" sprite (first frame, no animation)
export const fetchStillIdle = (direction = "DOWN") => {
    return fetchFrame(direction, 0, 0); // Idle is at row 0
}

// Fetch the "idle" animation frames
export const fetchIdle = (direction = "DOWN", frame = 0) => {
    return fetchFrame(direction, frame % SPRITES_PER_DIRECTION, 1); // Idle animation is at row 1
}

// Fetch the "running" animation frames
export const fetchRunning = (direction = "DOWN", frame = 0) => {
    return fetchFrame(direction, frame % SPRITES_PER_DIRECTION, 2); // Running animation is at row 2
}

// Fetch the "sitting" animation frames
export const fetchSitting = (direction = "RIGHT", frame = 0) => {
    const yOffsetMultiplier = 4; // Sitting is on row 4
    const frameCount = SPRITES_PER_DIRECTION;
    const directionOffset = (direction.toUpperCase() === "LEFT" ? 1 : 0);
    const xOffset = directionOffset * frameCount * SPRITE_WIDTH + (frame % frameCount) * SPRITE_WIDTH;

    return new PIXI.Texture(baseTexture, new PIXI.Rectangle(xOffset, yOffsetMultiplier * SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT));
}

export const fetchDamage = (direction = "DOWN", frame = 0) => {
    const frameNum = frame % DAMAGE_FRAMES;  // Loop within the 3 damage frames
    const directionOffset = directionValue(direction);  // Get the direction offset
    const xOffset = directionOffset * DAMAGE_FRAMES * SPRITE_WIDTH + frameNum * SPRITE_WIDTH;
    const yOffset = 19 * SPRITE_HEIGHT;  // The 19th row for the damage animation

    return new PIXI.Texture(baseTexture, new PIXI.Rectangle(xOffset, yOffset, SPRITE_WIDTH, SPRITE_HEIGHT));
}

