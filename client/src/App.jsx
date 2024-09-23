import StageManager from "./managers/StageManager.jsx";
import {createStore, Provider } from 'jotai';
// Create a store instance
export const store = createStore();

const App = () => {
    /*TODO INITIAL SETUP HERE*/

    return (
        <Provider store={store}>
            <StageManager />
        </Provider>
    )
}

export default App

