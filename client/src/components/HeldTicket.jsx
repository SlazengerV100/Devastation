import { Container, Graphics, Text } from "@pixi/react";
import { TextStyle } from "pixi.js";
import { store } from '../App';
import { localHeldTicket } from "../js/atoms.js";

const h1Style = new TextStyle({
    fontFamily: 'Courier New, monospace',
    fontSize: 25,
    fill: '#36454F',
    letterSpacing: 2,
    resolution: 1,
    padding: 10,
});

const h2Style = new TextStyle({
    fontFamily: 'Courier New, monospace',
    fontSize: 21,
    fill: '#36454F',
    letterSpacing: 2,
    resolution: 1,
    padding: 5,
});

const h3Style = new TextStyle({
    fontFamily: 'Courier New, monospace',
    fontSize: 15,
    fill: '#36454F',
    letterSpacing: 1,
    resolution: 1,
    padding: 5,
});

const HeldTicket = ({ mapPosition, mapWidth }) => {
    const ticketViewX = mapPosition.x + mapWidth;
    const ticketViewY = mapPosition.y;

    const heldTicket = store.get(localHeldTicket);

    if (heldTicket && Object.keys(heldTicket).length === 0) {
        return <></>; // Return nothing if no ticket is held
    }


    return (
        <Container
            position={[ticketViewX, ticketViewY]}
        >
            {/* Draw the background (simulating padding) */}
            <Graphics
                draw={g => {
                    g.clear();

                    // Set the line style for the border
                    g.lineStyle(4, 0x000000); // 4px black border (you can adjust color and width)

                    // Fill the rectangle with a color
                    g.beginFill(0xe4d5b7); // Light gray background color

                    // Draw the rectangle with the border
                    g.drawRect(10, 10, 400, 500);

                    g.endFill();
                }}
            />

            <Text
                text={"Ticket " + heldTicket.id}
                style={h1Style} // Apply the new TextStyle with the updated fontSize
                x={20} // Simulate left padding
                y={20} // Simulate top padding
            />

            <Text
                text={heldTicket.ticketTitle}
                style={h2Style} // Apply the new TextStyle with the updated fontSize
                x={30} // Simulate left padding
                y={50} // Simulate top padding
            />

            {heldTicket.tasks.map((task, index) => (
                <Text
                    key={task.title} // Unique key for each task
                    text={`${task.title}  ${task.completionTime}s`} // Display task title and time
                    style={h3Style} // Apply TextStyle for task details
                    x={40} // Simulate left padding
                    y={80 + index * 30} // Dynamic top padding based on index
                />
            ))}

        </Container>
    );
};

export default HeldTicket;

