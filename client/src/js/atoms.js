import { atom } from 'jotai'

export const connectionStatusAtom = atom('disconnected')
export const localCharacterAtom = atom({
    characterName: '',
    characterX: 0,
    characterY: 0
})
