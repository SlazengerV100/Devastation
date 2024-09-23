import { useState, useEffect } from "react";
import { useAtom } from "jotai";
import { localCharacterAtom } from "../js/atoms.js";  // Import the atom
import { requestState, activatePlayer } from "../managers/connectionManager.js";

const CharacterSelectStage = () => {
    const [playerMap, setPlayerMap] = useState(null);
    const [loading, setLoading] = useState(true);
    const [, setStoredPlayer] = useAtom(localCharacterAtom); // Use the atom

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

            // Update the atom
            setStoredPlayer(prev => ({
                ...prev,
                playerRole: player.role
            }));

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
