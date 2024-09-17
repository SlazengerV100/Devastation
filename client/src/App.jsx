import StageManager from "./managers/StageManager.jsx";
import {useEffect} from "react";
import { Provider } from 'jotai';
import { createStore } from 'jotai';

// Create a store instance
export const store = createStore();

const App = () => {
    /*TODO INITIAL SETUP HERE*/
    useEffect(() => {

    }, []);

    return (
        <Provider store={store}>
            <StageManager />
        </Provider>
    )
}

export default App

