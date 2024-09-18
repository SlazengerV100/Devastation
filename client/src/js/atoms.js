import { atom } from 'jotai'

export const connectionStatusAtom = atom('disconnected')
export const localCharacterAtom = atom({
    characterName: 'Developer',
    characterX: 0,
    characterY: 0
})
