import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "../Styles/Styles.css";
import api from "../services/api";

function Turmas() {

// Começa com um array vazio, os dados virão do Spring Boot
  const [turmas, setTurmas] = useState([]); 
  const [view, setView] = useState("list"); 
  const [nome, setNome] = useState("");
  const [curso, setCurso] = useState("");
  const [editingId, setEditingId] = useState(null);

  // Executa automaticamente quando a tela abre
  useEffect((e) => {
    fetchTurmas();
  }, []);

  // GET: Busca todas as turmas do backend
  const fetchTurmas = async () => {
    try {
      // Como o JWT já deve estar no cabeçalho do Axios, basta chamar a rota
      const response = await api.get('/api/v1/turmas'); 
      setTurmas(response.data);
    } catch (error) {
      console.error("Erro ao carregar turmas. Você está logado?", error);
    }
  };

  // POST: Cria uma nova turma
  const handleCreate = async (e) => {
    e.preventDefault();
    if (!nome || !curso) return;

    try {
      await api.post('/api/v1/turmas', { nome, curso });
      resetForm();
      fetchTurmas(); // Atualiza a lista na tela chamando o GET novamente
    } catch (error) {
      console.error("Erro ao criar turma", error);
    }
  };

  // Prepara o formulário para edição
  const handleEdit = (turma) => {
    setEditingId(turma.id);
    setNome(turma.nome);
    setCurso(turma.curso);
    setView("edit");
  };

  // PUT: Atualiza uma turma existente
  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await api.put(`/api/v1/turmas/${editingId}`, { nome, curso });
      resetForm();
      fetchTurmas(); // Atualiza a lista
    } catch (error) {
      console.error("Erro ao atualizar turma", error);
    }
  };

  // DELETE: Exclui uma turma
  const handleDelete = async (id) => {
    if(!window.confirm("Tem certeza que deseja excluir esta turma?")) return;
    
    try {
      await api.delete(`/api/v1/turmas/${id}`);
      fetchTurmas(); // Atualiza a lista
    } catch (error) {
      console.error("Erro ao deletar turma", error);
    }
  };

  const resetForm = () => {
    setNome("");
    setCurso("");
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

                          <p className="TxtCursoTurma">{turma.curso}</p>

                        </div>

                        <div className="AcoesTurma">

                          <a className="aAcao" onClick={() => handleEdit(turma)}>Editar</a>

                          <a className="aAcao aExcluir" onClick={() => handleDelete(turma.id)}>Excluir</a>

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

                  <div className="Input">

                    <input
                      className="InputForm"
                      type="text"
                      placeholder="Curso"
                      value={curso}
                      onChange={(e) => setCurso(e.target.value)}
                      required
                      maxLength={40}
                    />

                  </div>

                  <div className="ContinuaTodo" style={{ flexDirection: "column", gap: "12px" }}>

                    <button type="submit" className="login-button">

                      Salvar

                    </button>

                    <a className="Login" style={{ color: "#8D2222", cursor: "pointer" }} onClick={resetForm}>

                      Cancelar

                    </a>

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