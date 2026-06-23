import { Link } from "react-router-dom"
import "../Styles/Styles.css"
import { useState } from "react";
import Input from "../components/Input";
import Button from "../components/Button";
import api from '../services/api';


function CriarConta () {

  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [nome, setNome] = useState('');
  const [role, setRole] = useState('ALUNO');

const handleCriarConta = async (e) => {

    e.preventDefault();

    try {

      const response = await api.post('/api/v1/auth/register', { email, senha, nome, role });
      
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

                <Input 
                onChange={e => setNome(e.target.value)}
                value={nome}
                name="nome" 
                type="nome" 
                id="nome" 
                autoComplete="nome" 
                required maxLength={30} 
                placeholder='Nome'
                />

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

                <select className="InputForm" value={role} onChange={e => setRole(e.target.value)}>

                  <option value={"ALUNO"}>Aluno</option>

                  <option value={"PROFESSOR"}>Professor</option>
                  
                </select>

                <div className="ContinuaTodo">

                  <Button id="Continua" type="submit">Continuar</Button>

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