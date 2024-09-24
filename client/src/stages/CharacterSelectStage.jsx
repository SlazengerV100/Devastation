import { useState, useEffect } from "react";
import { useAtom } from "jotai";
import {localPlayerId} from "../js/atoms.js";  // Import the atom
import { requestState, activatePlayer } from "../managers/connectionManager.js";
import {store} from "../App.jsx";

const CharacterSelectStage = () => {
    const [playerMap, setPlayerMap] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        updatePlayerSelection();
    }, []);

    const updatePlayerSelection = async () => {
        setLoading(true);
        try {
            const playerMap = await requestState();
            setPlayerMap(playerMap);
        } catch (error) {
            console.error('Failed to fetch players:', error);
        } finally {
            setLoading(false);
        }
    };

    const setSessionPlayer = async (playerId) => {
        try {
            const player = await activatePlayer(playerId);
            console.log('Player activated:', player);

            // Update session storage
            sessionStorage.setItem('playerID', player.id);

            store.set(localPlayerId, player.id)

            // Update player selection state
            await updatePlayerSelection();
        } catch (error) {
            console.error('Failed to activate player:', error);
        }
    };

    return (
        <div>
            {loading ? (
                <p>Loading players...</p>
            ) : (
                <div>
                    <h3>Available Players:</h3>
                    <div>
                        {playerMap.map((p) => (
                            <button
                                disabled={p.active}
                                onClick={() => setSessionPlayer(p.id)}
                                key={p.id}
                            >
                                {p.role}
                            </button>
                        ))}
                    </div>
                </div>
            )}
            <button onClick={updatePlayerSelection}>
                Request Players
            </button>
        </div>
    );
};

export default CharacterSelectStage;
