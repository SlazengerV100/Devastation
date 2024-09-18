import { useState, useEffect } from "react";
import { requestState } from "../managers/connectionManager.js";

const CharacterSelectStage = () => {
    const [currentPlayers, setCurrentPlayers] = useState(null);
    const [loading, setLoading] = useState(true);

    const fetchPlayers = async () => {
        setLoading(true);
        try {
            const players = await requestState();
            setCurrentPlayers(players);
        } catch (error) {
            console.error('Failed to fetch players:', error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchPlayers();
    }, []);

    return (
        <div>
            {loading ? (
                <p>Loading players...</p>
            ) : (
                currentPlayers ? (
                    <div>
                        <h3>Available Players:</h3>
                        <ul>
                            {Object.keys(currentPlayers).map((player) => (
                                <li key={player}>{player}</li>
                            ))}
                        </ul>
                    </div>
                ) : (
                    <p>No players available yet.</p>
                )
            )}
            <button onClick={fetchPlayers}>
                Request Players
            </button>
        </div>
    );
};

export default CharacterSelectStage;
