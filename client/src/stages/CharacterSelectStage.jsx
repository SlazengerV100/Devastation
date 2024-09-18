import { useState, useEffect } from "react";
import { useAtom } from "jotai";
import { localCharacterAtom } from "../js/atoms.js";  // Import the atom
import { requestState, activatePlayer } from "../managers/connectionManager.js";

const CharacterSelectStage = () => {
    const [playerActiveState, setPlayerActiveState] = useState(null);
    const [loading, setLoading] = useState(true);
    const [, setStoredPlayer] = useAtom(localCharacterAtom); // Use the atom

    useEffect(() => {
        updatePlayerSelection();
    }, []);

    const updatePlayerSelection = async () => {
        setLoading(true);
        try {
            const playerMap = await requestState();
            const activeState = Object.entries(playerMap).map(([name, player]) => ({
                id: name,
                active: player.active,
            }));
            setPlayerActiveState(activeState);
        } catch (error) {
            console.error('Failed to fetch players:', error);
        } finally {
            setLoading(false);
        }
    };

    const setSessionPlayer = async (playerName) => {
        try {
            const updatedState = await activatePlayer(playerName);
            console.log('Player activated:', updatedState);

            // Update session storage
            sessionStorage.setItem('playerID', playerName);

            // Update the atom
            setStoredPlayer(prev => ({
                ...prev,
                playerName: playerName
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
                        {playerActiveState.map((p) => (
                            <button
                                disabled={p.active}
                                onClick={() => setSessionPlayer(p.id)}
                                key={p.id}
                            >
                                {p.id}
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
