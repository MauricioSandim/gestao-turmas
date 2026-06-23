import { useState } from 'react'
import './App.css'
import Login from './pages/Login.jsx'
import Turmas from './pages/Turma.jsx'
import CriarConta from './pages/CriarConta.jsx'
import { BrowserRouter, Router, Routes, Route } from 'react-router-dom';

function App() {
  const [count, setCount] = useState(0)

  return (

    <BrowserRouter>

      <Routes>

        <Route path="/login" element={<Login/>}/>

        <Route path="/" element={<Turmas/>}/>

        <Route path="/criarConta" element={<CriarConta/>}/>

      </Routes>

    </BrowserRouter>

  )
}

export default App
