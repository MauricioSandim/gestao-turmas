import { Link } from "react-router-dom"
import "../Styles/Styles.css"
import { useState } from "react";
import Input from "../components/Input";
import Button from "../components/Button";
import api from '../services/api';


function Login () {

  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');

const handleLogin = async (e) => {

    e.preventDefault();

    try {

      const response = await api.post('/api/v1/auth/login', { email, senha });
      
      const token = response.data.token;
    
      localStorage.setItem('token', token);
      
      alert('Login realizado com sucesso!');

      window.location.href = '/';

    } catch (error) {

      console.error("Erro ao fazer login", error);

      alert('Usuário ou senha inválidos.');

    }

  };

  return(

      <div className="BOX">

        <main className="TelaConta">

          <div className="FormularioTodo">

            <h1 className="Titulo">Login</h1>

            <form className="Formulario" id="form" autoComplete="on" onSubmit={handleLogin}>

              <Input 
              onChange={e => setEmail(e.target.value)} 
              value={email}
              name="email" 
              type="email" 
              id="email" 
              autoComplete="email" 
              required 
              maxLength={30} 
              placeholder="E-mail" 
              />

              <Input 
              onChange={e => setSenha(e.target.value)} 
              value={senha}
              name="password" 
              type="password" 
              id="password" 
              autoComplete="current-password" 
              required 
              minLength={8} 
              maxLength={20} 
              placeholder="Senha" 
              />

              <div className="ContinuaTodo">

                <Button id="Continua" type="submit">Continuar</Button>

              </div>

            </form>

            <div className="ContinuaTodo">

              <Link to="/criarConta" className="login-button">Criar Conta</Link>

            </div>

          </div>
        
        </main>

      </div>
         
  )

}

export default Login