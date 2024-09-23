import * as PIXI from 'pixi.js';
import spriteSheetURL from '../../assets/testC.png';
import buttonSheetUrl from '../../assets/buttonSheet.png';

// Button Sprite dimensions
const BUTTON_HEIGHT = 110;
const BUTTON_WIDTH = 222;
const buttonTexture = PIXI.BaseTexture.from(buttonSheetUrl);

// Sprite player dimensions
const SPRITE_HEIGHT = 96;
const SPRITE_WIDTH = 48;
const SPRITES_PER_DIRECTION = 6; // Number of frames per animation
const DAMAGE_FRAMES = 3;
const spriteTexture = PIXI.BaseTexture.from(spriteSheetURL);

export const textures = {};

export const init = () => {
    initRunning();
    initIdle();
    initSitting();
    initDamage(); // Initialize all types of textures
};

// Fetch the "still idle" sprite (first frame, no animation)
export const fetchStillIdle = (direction = "DOWN") => {
    return fetchFrame(direction, 0, 0); // Idle is at row 0
};

const initRunning = () => {
    const runningFrames = {};

    ["LEFT", "RIGHT", "UP", "DOWN"].forEach(direction => {
        const currentTextures = [];
        for (let i = 0; i < 6; i++) {
            currentTextures.push(fetchRunning(direction, i));
        }
        runningFrames[direction] = currentTextures;
    });

    textures.running = runningFrames;
};

const initIdle = () => {
    const idleFrames = {};

    ["LEFT", "RIGHT", "UP", "DOWN"].forEach(direction => {
        const currentTextures = [];
        for (let i = 0; i < 6; i++) {
            currentTextures.push(fetchIdle(direction, i));
        }
        idleFrames[direction] = currentTextures;
    });

    textures.idle = idleFrames;
};

const initSitting = () => {
    const sittingFrames = {};

    ["LEFT", "RIGHT", "UP", "DOWN"].forEach(direction => {
        const currentTextures = [];
        for (let i = 0; i < 6; i++) {
            currentTextures.push(fetchSitting(direction, i));
        }
        sittingFrames[direction] = currentTextures;
    });

    textures.sitting = sittingFrames;
};

const initDamage = () => {
    const damageFrames = {};

    ["LEFT", "RIGHT", "UP", "DOWN"].forEach(direction => {
        const currentTextures = [];
        for (let i = 0; i < DAMAGE_FRAMES; i++) {
            currentTextures.push(fetchDamage(direction, i));
        }
        damageFrames[direction] = currentTextures;
    });

    textures.damage = damageFrames;
};

// Maps direction to its corresponding row in the sprite sheet
const directionValue = (direction) => {
    switch (direction.toUpperCase()) {
        case "RIGHT": return 0;
        case "UP": return 1;
        case "LEFT": return 2;
        case "DOWN": return 3;
        default: return 1;
    }
};

// General function to fetch a sprite frame
const fetchFrame = (direction, frame, yOffsetMultiplier) => {
    const directionOffset = directionValue(direction);
    const xOffset = (directionOffset * SPRITES_PER_DIRECTION * SPRITE_WIDTH) + (frame * SPRITE_WIDTH);
    const yOffset = yOffsetMultiplier * SPRITE_HEIGHT;

    return new PIXI.Texture(spriteTexture, new PIXI.Rectangle(xOffset, yOffset, SPRITE_WIDTH, SPRITE_HEIGHT));
};

// Fetch the "idle" animation frames
export const fetchIdle = (direction = "DOWN", frame = 0) => {
    return fetchFrame(direction, frame % SPRITES_PER_DIRECTION, 1); // Idle animation is at row 1
};

// Fetch the "running" animation frames
export const fetchRunning = (direction = "DOWN", frame = 0) => {
    return fetchFrame(direction, frame % SPRITES_PER_DIRECTION, 2); // Running animation is at row 2
};

// Fetch the "sitting" animation frames
export const fetchSitting = (direction = "RIGHT", frame = 0) => {
    const yOffsetMultiplier = 4; // Sitting is on row 4
    const frameCount = SPRITES_PER_DIRECTION;
    const directionOffset = (direction.toUpperCase() === "LEFT" ? 1 : 0);
    const xOffset = directionOffset * frameCount * SPRITE_WIDTH + (frame % frameCount) * SPRITE_WIDTH;

    return new PIXI.Texture(spriteTexture, new PIXI.Rectangle(xOffset, yOffsetMultiplier * SPRITE_HEIGHT, SPRITE_WIDTH, SPRITE_HEIGHT));
};

// Fetch the damage animation frames
export const fetchDamage = (direction = "DOWN", frame = 0) => {
    const frameNum = frame % DAMAGE_FRAMES;  // Loop within the 3 damage frames
    const directionOffset = directionValue(direction);  // Get the direction offset
    const xOffset = directionOffset * DAMAGE_FRAMES * SPRITE_WIDTH + frameNum * SPRITE_WIDTH;
    const yOffset = 19 * SPRITE_HEIGHT;  // The 19th row for the damage animation

    return new PIXI.Texture(spriteTexture, new PIXI.Rectangle(xOffset, yOffset, SPRITE_WIDTH, SPRITE_HEIGHT));
};

// Fetch the play button texture
export const fetchPlayButton = (pressed = false) => {
    const x = pressed ? BUTTON_WIDTH : 0;
    return new PIXI.Texture(buttonTexture, new PIXI.Rectangle(x, 0, BUTTON_WIDTH, BUTTON_HEIGHT));
};
