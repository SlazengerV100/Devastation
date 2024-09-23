import StageManager from "./managers/StageManager.jsx";
import {createStore, Provider, useAtom} from 'jotai';
import keyHandler from "./js/keyHandler.js";
import {useEffect} from "react";
import {sendPlayerMovement} from './managers/connectionManager.js'

// Create a store instance
export const store = createStore();

const App = () => {
    /*TODO INITIAL SETUP HERE*/

    useEffect(() => {
        const cleanup = keyHandler(sendPlayerMovement);

        return () => {
            cleanup();
        };
    }, []);

    return (
        <Provider store={store}>
            <StageManager />
        </Provider>
    )
}

export default App

