import { atom } from 'jotai'

export const connectionStatusAtom = atom('disconnected')

export const playerMap = atom({
    1: { playerRole: 'PROJECT_MANAGER', direction: 'DOWN', x: 0, y: 0},
    2: { playerRole: 'DEVELOPER', direction: 'DOWN', x: 0, y: 0 },
    3: { playerRole: 'TESTER', direction: 'DOWN', x: 0, y: 0 },
})

export const localPlayerId = atom(-1)



