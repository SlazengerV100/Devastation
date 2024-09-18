import { atom } from 'jotai'

export const connectionStatusAtom = atom('disconnected')
export const localCharacterAtom = atom({playerName: '', x: 0, y: 0})


