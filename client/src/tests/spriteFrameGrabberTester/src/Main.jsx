import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import SpriteTest from "./SpriteTest.jsx";

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <SpriteTest />
  </StrictMode>,
)
