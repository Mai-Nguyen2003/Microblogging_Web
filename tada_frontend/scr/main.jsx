import {StrictMode} from 'react'
import {createRoot} from 'react-dom/client'
import TaDaFrontend from './TaDaFrontend.jsx'

createRoot(document.body).render(
    <StrictMode>
        <TaDaFrontend/>
    </StrictMode>,
)
