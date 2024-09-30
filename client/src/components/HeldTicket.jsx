import { Container, Graphics, Text } from "@pixi/react";
import { TextStyle } from "pixi.js";
import { store } from '../App';
import { localHeldTicket } from "../js/atoms.js";

const textStyle = (fontSize) => new TextStyle({
    fontFamily: 'Courier New, monospace',
    fontSize,
    fill: '#36454F',
    letterSpacing: 2 - (fontSize / 25), // Adjust letter spacing based on font size
    resolution: 1,
    padding: fontSize / 5,
});

const HeldTicket = ({ mapPosition, mapWidth }) => {
    const ticketViewX = mapPosition.x + mapWidth;
    const ticketViewY = mapPosition.y;

    const heldTicket = store.get(localHeldTicket);
    if (!heldTicket || !heldTicket.tasks) return null;

    return (
        <Container position={[ticketViewX, ticketViewY]}>
            <Graphics draw={g => {
                g.clear();
                g.lineStyle(4, 0x000000);
                g.beginFill(0xe4d5b7);
                g.drawRect(10, 10, 400, 500);
                g.endFill();
            }} />

            <Text text={`Ticket ${heldTicket.id}`} style={textStyle(25)} x={20} y={20} />
            <Text text={heldTicket.ticketTitle} style={textStyle(21)} x={30} y={50} />

            {heldTicket.tasks.map((task, index) => (
                <Text key={task.title} text={`${task.title}  ${task.completionTime}s`} style={textStyle(15)} x={40} y={80 + index * 30} />
            ))}
        </Container>
    );
};

export default HeldTicket;
