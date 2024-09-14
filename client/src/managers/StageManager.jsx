import LoadingStage from "../stages/LoadingStage.jsx";
import {useState} from "react";

const StageManager = () => {
    const [connected, setConnected] = useState(false)

    return (
        <>
            <LoadingStage />
        </>
    )
}

export default StageManager