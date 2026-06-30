import { useParams } from "react-router-dom";
import Button from "../components/Button";
import { useState, useEffect } from "react";
import api from "../services/api";

function Matriculas() {
  const { turmaId } = useParams(); 

  const [matriculas, setMatriculas] = useState([]); 
  const [alunos, setAlunos] = useState([]); 
  const [view, setView] = useState("list"); 
  const [alunoId, setAlunoId] = useState("");

  useEffect(() => {
    fetchMatriculas();
    fetchAlunos();
  }, [turmaId]);

  const fetchMatriculas = async () => {
    try {
      const response = await api.get(`/api/v1/turmas/${turmaId}/matricula`); 
      setMatriculas(response.data);
    } catch (error) {
      console.error("Erro detalhado do backend:", error.response);
      alert("Falha ao carregar matrículas.");
    }
  };

  const fetchAlunos = async () => {
    try {
      const response = await api.get("/api/v1/aluno");
      setAlunos(response.data);
    } catch (error) {
      console.error("Erro ao buscar lista de alunos:", error);
    }
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    if (!alunoId) {
      alert("Por favor, selecione um aluno.");
      return;
    }

    try {
    
      await api.post(`/api/v1/turmas/${turmaId}/matricula?alunoId=${alunoId}`);

      alert("Matrícula realizada com sucesso!");
      resetForm();
      fetchMatriculas();
    } catch (error) {
      console.error("Erro ao matricular aluno:", error.response);
      alert("Erro ao realizar matrícula. Verifique o console do backend.");
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Tem certeza que deseja remover esta matrícula?")) return;
    
    try {
      await api.delete(`/api/v1/turmas/${turmaId}/matricula?alunoId=${id}`);

      alert("Matrícula removida com sucesso!");
      fetchMatriculas();
    } catch (error) {
      console.error("Erro ao deletar matrícula:", error.response);
      alert("Erro ao remover matrícula.");
    }
  };

  const resetForm = () => {
    setAlunoId("");
    setView("list");
  };

  return (
    <div className="Sala">
      {view === "list" && (
        <>
          <div className="ParteSala">
            <h3>👥 Gerenciamento de Matrículas</h3>
            <Button className="aAcao" onClick={() => setView("create")}>
              Cadastrar Nova Matrícula
            </Button>

            <table className="Tabela">
              <thead>
                <tr className="Info">
                  <th className="Coluna">Aluno (Número de Matrícula)</th>
                  <th className="LinhaAcao">Ações</th>
                </tr>
              </thead>
              <tbody>
                {matriculas.length === 0 ? (
                  <tr>
                    <td colSpan="2" style={{ textAlign: "center", padding: "15px" }}>
                      Nenhum aluno matriculado nesta turma.
                    </td>
                  </tr>
                ) : (
                  matriculas.map((m) => (
                    <tr key={m.id}>
                 
                      <td className="LinhaDado">
                        {m.aluno ? `${m.aluno.nome} (Matrícula: ${m.aluno.id})` : `Matrícula ID: ${m.id}`}
                      </td>
                      <td className="LinhaAcao">
                      
                        <button className="aAcao" onClick={() => handleDelete(m.id)}>
                          Excluir
                        </button>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </>
      )}

      {view === "create" && (
        <>
          <div className="TurmaTodo">
            <h1 className="Titulo">Matricular Aluno</h1>

            <form className="Formulario" onSubmit={handleCreate}>
              <div className="Input">
                <select
                  className="InputForm"
                  value={alunoId}
                  onChange={(e) => setAlunoId(e.target.value)}
                  required
                >
                  <option value="">Selecione um Aluno...</option>
                  {alunos.map((aluno) => (
                    <option key={aluno.id} value={aluno.id}>
                      {aluno.nome} (ID: {aluno.id})
                    </option>
                  ))}
                </select>
              </div>

              <div className="ContinuaTodo">
                <button type="submit" className="login-button">
                  Salvar
                </button>
                <button type="button" className="login-button" onClick={resetForm}>
                  Cancelar
                </button>
              </div>
            </form>
          </div>
        </>
      )}
    </div>
  );
}

export default Matriculas;