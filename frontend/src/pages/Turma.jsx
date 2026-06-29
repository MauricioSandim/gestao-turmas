import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "../Styles/Styles.css";
import api from "../services/api";

function Turmas() {

  const [turmas, setTurmas] = useState([]); 
  const [view, setView] = useState("list"); 
  const [nome, setNome] = useState("");
  const [editingId, setEditingId] = useState(null);

  // Executa automaticamente quando a tela abre
  useEffect((e) => {

    fetchTurmas();
    
  }, []);

  const fetchTurmas = async () => {
    try {

      const response = await api.get('/api/v1/turmas'); 

      setTurmas(response.data);

    } catch (error) {

      console.error("Erro detalhado do backend:", error.response);

      alert(`Erro ${error.response?.status}: Falha ao carregar turmas.`);

    }

  };

  const handleCreate = async (e) => {

    e.preventDefault();

    if (!nome) return;

    try {

      await api.post('/api/v1/turmas', { nome });

      resetForm();

      fetchTurmas();

    } catch (error) {

      console.error("Erro ao criar turma", error);

    }

  };

  const handleEdit = (turma) => {

    setEditingId(turma.id);

    setNome(turma.nome);

    setView("edit");

  };

  const handleUpdate = async (e) => {

    e.preventDefault();

    try {

      await api.put(`/api/v1/turmas/${editingId}`, { nome });

      resetForm();

      fetchTurmas();

    } catch (error) {

      console.error("Erro ao atualizar turma", error);

    }

  };

  const handleDelete = async (id) => {

    if(!window.confirm("Tem certeza que deseja excluir esta turma?")) return;
    
    try {
      await api.delete(`/api/v1/turmas/${id}`);

      fetchTurmas();

    } catch (error) {

      console.error("Erro ao deletar turma", error);

    }
  };

  const resetForm = () => {

    setNome("");

    setEditingId(null);

    setView("list");

  };

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

            {view === "list" && (

              <>

                <h1 className="TituloTurmas">Turmas</h1>
                
                <div className="Crud">

                  <div className="ListaContainer">

                    {turmas.map((turma) => (

                      <div key={turma.id} className="ItemTurma">

                        <div className="InfoTurma">

                          <p className="TxtNomeTurma">{turma.nome}</p>

                        </div>

                        <div className="AcoesTurma">

                          <a className="aAcao" onClick={() => handleEdit(turma)}>Editar</a>

                          <a className="aAcao aExcluir" onClick={() => handleDelete(turma.id)}>Excluir</a>

                          <Link to={`/turma/${turma.nome}`} className="aAcao"> 

                          Entrar

                          </Link>

                        </div>

                      </div>

                    ))}

                  </div>

                  <div className="ContinuaTodo">

                    <button type="button" className="login-button" onClick={() => setView("create")}>

                      Nova Turma

                    </button>

                  </div>

                </div>

              </>

            )}

            {(view === "create" || view === "edit") && (

              <>

                <h1 className="Titulo">

                  {view === "create" ? "Nova Turma" : "Editar Turma"}

                </h1>

                <form className="Formulario" onSubmit={view === "create" ? handleCreate : handleUpdate}>

                  <div className="Input">
                    
                    <input
                      className="InputForm"
                      type="text"
                      placeholder="Nome da Turma"
                      value={nome}
                      onChange={(e) => setNome(e.target.value)}
                      required
                      maxLength={30}
                    />

                  </div>

                  <div className="ContinuaTodo">

                    <button type="submit" className="login-button">

                      Salvar

                    </button>

                    <button className="login-button" onClick={resetForm}>

                      Cancelar

                    </button>

                  </div>

                </form>

              </>

            )}

          </div>

        </main>
        
      </div>

    </div>
  );
}

export default Turmas;