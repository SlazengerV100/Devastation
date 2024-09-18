import { atom } from 'jotai'
import { atomWithStorage } from 'jotai/utils'

export const connectionStatusAtom = atom('disconnected')
export const localCharacterAtom = atom({
    characterName: 'Developer',
    characterX: 0,
    characterY: 0
})

export const playerPosition = atom({x: 0, y: 0})

