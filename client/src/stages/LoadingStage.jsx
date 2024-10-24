import { useAtomValue, useSetAtom } from "jotai";
import {connectionStatusAtom, currentPageAtom} from '../js/atoms.js'
import { Sprite, Stage} from "@pixi/react";
import {useEffect, useState} from "react";
import * as PIXI from "pixi.js";
import backgroundURL from "../../assets/background.png";
import titleURL from "../../assets/DEV-A-STATION.png";
import BlankButton from "../components/BlankButton.jsx";

const LoadingStage = ({attemptConnect}) => {
    const connectionStatus = useAtomValue(connectionStatusAtom)
    const setCurrentPage = useSetAtom(currentPageAtom)
    const [windowSize, setWindowSize] = useState({
        width: window.innerWidth,
        height: window.innerHeight
    });

    useEffect(() => {
        const handleResize = () => {
            setWindowSize({
                width: window.innerWidth,
                height: window.innerHeight
            });
        };
        window.addEventListener("resize", handleResize);

        return () => window.removeEventListener("resize", handleResize);
    }, []);

    return (
        <Stage
            width={windowSize.width}
            height={windowSize.height}
            options={{ backgroundColor: 0x1099bb }}
        >
            <Sprite texture={PIXI.Texture.from(backgroundURL)} width={windowSize.width} height={windowSize.height}/>
            <Sprite texture={PIXI.Texture.from(titleURL)} anchor={0.5} y={windowSize.height * 0.2} x={windowSize.width / 2}/>

            {
                connectionStatus === 'disconnected' && (
                    <BlankButton
                        text={'Try Connect'}
                        x={(windowSize.width / 2) - 200}
                        y={windowSize.height / 2}
                        size={'large'}
                        action={attemptConnect}
                    />
                )
            }
            {
                connectionStatus === 'connected' && (
                    <BlankButton
                        text={'Play'}
                        x={(windowSize.width / 2) - 200}
                        y={windowSize.height / 2}
                        size={'large'}
                        action={() => {
                            setCurrentPage("select")
                            console.log('clicked')
                        }}
                    />

                )
            }
        </Stage>
        // <div>
        //     {connectionStatus === 'disconnected' && (
        //         <div>
        //             <p>Not connected</p>
        //             <button onClick={attemptConnect}>Try Reconnecting</button>
        //
        //         </div>
        //     )}
        // </div>
    );
};

export default LoadingStage;
