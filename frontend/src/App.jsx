import { useState } from 'react'
import './App.css'
import Login from './pages/Login.jsx'
import Turmas from './pages/Turma.jsx'
import CriarConta from './pages/CriarConta.jsx'
import SalaDaTurma from './pages/SalaDaTurma.jsx'
import Notas from './pages/Notas.jsx'
import Avaliacoes from './pages/Avaliacoes.jsx'
import Faltas from './pages/Faltas.jsx'
import Matriculas from './pages/Matriculas.jsx'
import Horarios from './pages/Horarios.jsx'
import { BrowserRouter, Router, Routes, Route, Navigate } from 'react-router-dom';

function App() {
  const [count, setCount] = useState(0)

  return (

    <BrowserRouter>

      <Routes>

        <Route path="/login" element={<Login/>}/>

        <Route path="/" element={<Navigate to="/turma" replace/>}/>
        
        <Route path='/turma' element={<Turmas/>}/>

        <Route path="/criarConta" element={<CriarConta/>}/>

      </Routes>

      <Routes>

        <Route path='/turma/:nomeTurma' element={<SalaDaTurma/>}>

          <Route path='notas' element={<Notas/>}/>

          <Route path='matriculas' element={<Matriculas/>}/>

          <Route path='horarios' element={<Horarios/>}/>

          <Route path='avaliacoes' element={<Avaliacoes/>}/>

          <Route path='faltas' element={<Faltas/>}/>


        </Route>

      </Routes>

    </BrowserRouter>

  )
}

export default App
