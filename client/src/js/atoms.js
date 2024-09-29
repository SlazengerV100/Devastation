import { atom } from 'jotai';

// Connection status atom
export const connectionStatusAtom = atom('disconnected');

// Players atom
export const players = atom({
    1: { playerRole: 'PROJECT_MANAGER', direction: 'DOWN', x: 0, y: 0 },
    2: { playerRole: 'DEVELOPER', direction: 'DOWN', x: 0, y: 0 },
    3: { playerRole: 'TESTER', direction: 'DOWN', x: 0, y: 0 },
});

// Local player ID atom
export const localPlayerId = atom(-1);

// Tickets atom
export const ticketsAtom = atom([]);
