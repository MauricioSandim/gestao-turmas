import { useState, useEffect } from "react";
import { Link, Outlet, useLocation, useParams } from "react-router-dom";
import "../Styles/Styles.css";
import api from "../services/api";

function SalaDaTurmas() {

    const {nomeTurma} = useParams();
    const ondeEsta = useLocation();

    const selecaoDaTurma = ondeEsta.pathname === `/turma/${nomeTurma}`;

    const estaLogando = localStorage.getItem('token') === null; 



  return (
    <div className="LayoutTelaCompleta">

      <aside className="MenuLateralEsquerdo">

        <div className="MenuLateralTopo">

          <h1 className="TituloTurmas">{estaLogando === false ? JSON.parse(localStorage.getItem('usuario')) : ""}</h1>

          <h4 className="TituloTurmas">{estaLogando === false ? JSON.parse(localStorage.getItem('role')) : "Login"}</h4>

          <Link to="/login" className="login-button btn-sidebar-login">

            {estaLogando === false ? "Entrar em Outra Conta" : "Login"}

          </Link>

        </div>

        <div className="MenuLateralEspacoBotoes">

        </div>

      </aside>

      <div className="BOX">

        <main className="TelaConta">

          <div className="TurmasTodo">

            <h1 className="TituloTurmas">Turma: {`${nomeTurma}`}</h1>

            {selecaoDaTurma ? (

                <div className="Sala">

                  <div className="ParteSala">

                    <Link to={`/turma/${nomeTurma}/notas`} className="aAcao">

                    Notas

                    </Link>

                    <Link to={`/turma/${nomeTurma}/avaliacoes`} className="aAcao">

                    Atividades Avaliativas

                    </Link>

                    <Link to={`/turma/${nomeTurma}/faltas`} className="aAcao">

                    Faltas

                    </Link>

                    <Link to={`/turma/${nomeTurma}/horarios`} className="aAcao">

                    Horários

                    </Link>

                    <Link to={`/turma/${nomeTurma}/matriculas`} className="aAcao">

                    Matrículas

                    </Link>

                  </div>

                </div>

            ) : (

                <Outlet/>
                
            )}

          </div>

        </main>
        
      </div>

    </div>
  );
}

export default SalaDaTurmas;