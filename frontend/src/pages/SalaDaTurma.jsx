import { useState, useEffect } from "react";
import { Link, Outlet, useLocation, useParams } from "react-router-dom";
import "../Styles/Styles.css";
import api from "../services/api";

function SalaDaTurmas() {

    const {nomeTurma} = useParams();
    const ondeEsta = useLocation();

    const selecaoDaTurma = ondeEsta.pathname === `/turma/${nomeTurma}`;

  return (
    <div className="LayoutTelaCompleta">

      <aside className="MenuLateralEsquerdo">

        <div className="MenuLateralTopo">

          <Link to="/login" className="login-button btn-sidebar-login">

            Login

          </Link>

        </div>

        <div className="MenuLateralEspacoBotoes">

        </div>

      </aside>

      <div className="BOX">

        <main className="TelaConta">

          <div className="TurmasTodo">

            <h1 className="TituloTurmas">{`${nomeTurma}`}</h1>

            {selecaoDaTurma ? (

                <div className="Sala">

                    <Link to={`/turma/${nomeTurma}/notas`} className="aAcao">

                    Notas

                    </Link>

                    <Link to={`/turma/${nomeTurma}/Atividades Avaliativas`} className="aAcao">

                    Atividades Avaliativas

                    </Link>

                    <Link to={`/turma/${nomeTurma}/Faltas`} className="aAcao">

                    Faltas

                    </Link>

                    <Link to={`/turma/${nomeTurma}/Horários`} className="aAcao">

                    Horários

                    </Link>

                    <Link to={`/turma/${nomeTurma}/Martrículas`} className="aAcao">

                    Martrículas

                    </Link>

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