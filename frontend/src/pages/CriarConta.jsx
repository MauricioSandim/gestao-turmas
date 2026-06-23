import { Link } from "react-router-dom"
import "../Styles/Styles.css"
import { useState } from "react";

import api from '../services/api';


function CriarConta () {

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');


const handleCriarConta = async (e) => {
    e.preventDefault();

    try {

      const response = await api.post('/api/v1/auth/register', { username, password });
      
      const token = response.data.token;
      localStorage.setItem('token', token);
      
      alert('Conta criada com sucesso!');
      window.location.href = '/login';
    } catch (error) {
      console.error("Erro ao criar conta", error);
      alert('Usuário ou senha inválidos.');
    }
  };

  return(

      <div className="BOX">

        <main className="TelaConta">

          <div className="FormularioTodo">

            <h1 className="Titulo">Criar Conta</h1>

            <form className="Formulario" id="form" autoComplete="on" onSubmit={handleCriarConta}>

                <div className="Input">

                    <input className="InputForm" onChange={e => setUsername(e.target.value)} name="email" type="email" id="email" autoComplete="email" required maxLength={30} placeholder='E-mail'></input>

                </div>

                <div className="Input">

                    <input className="InputForm" onChange={e => setPassword(e.target.value)} name="password" type="password" id="password" autoComplete="current-password" required minLength={8}maxLength={20} placeholder='Senha'></input>

                </div>

                <div className="ContinuaTodo">

                    <button className="login-button" id="Continua" type="submit">Continuar</button>

                </div>

            </form>

            <div className="ContinuaTodo">

              <Link to="/login" className="login-button">Fazer Login</Link>

            </div>

          </div>
        
        </main>

      </div>
         
  )

}

export default CriarConta