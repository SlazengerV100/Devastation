import {useAtomValue} from "jotai";
import {scoreAtom} from "../js/atoms.js";
const EndScreen = () => {
    const score = useAtomValue(scoreAtom)

    return(
        <div>{`This i the end ${score}`}</div>
    )
}

export default EndScreen