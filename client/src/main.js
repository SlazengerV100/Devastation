import * as PIXI from 'pixi.js';

// Create application with full-screen dimensions
const app = new PIXI.Application({
    width: window.innerWidth,
    height: window.innerHeight,
    backgroundColor: 0x1099bb,
    resizeTo: window
});

document.getElementById('app').appendChild(app.view);

const style = new PIXI.TextStyle({
    fontSize: 64,
    fill: '#ffffff',
    align: 'center'
});

const client = new PIXI.Text('Client :)', style);

client.anchor.set(0.5);
client.x = app.screen.width / 2;
client.y = app.screen.height / 2;

app.stage.addChild(client);

// Resize listener to adjust text position when the window is resized
window.addEventListener('resize', () => {
    app.renderer.resize(window.innerWidth, window.innerHeight);
    client.x = app.screen.width / 2;
    client.y = app.screen.height / 2;
});
