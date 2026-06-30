import { useParams } from "react-router-dom";
import Button from "../components/Button";
import { useState, useEffect } from "react";
import api from "../services/api";

function Notas() {
  const { turmaId } = useParams();
  const [notas, setNotas] = useState([]);
  const [matriculas, setMatriculas] = useState([]); 
  const [atividades, setAtividades] = useState([]); 
  const [view, setView] = useState("list");
  const [editingId, setEditingId] = useState(null);
  const [idMatricula, setIdMatricula] = useState("");
  const [valor, setValor] = useState("");
  const [avaliacaoId, setAvaliacaoId] = useState("");
  const [role, setRole] = useState("");

  useEffect(() => {
    const localRole = localStorage.getItem('role') ? JSON.parse(localStorage.getItem('role')) : "";
    setRole(localRole);

    if (turmaId) {
      if (localRole === "ALUNO") {
        fetchDadosAluno();
      } else {
        fetchMatriculas();
        fetchAtividades();
      }
    }
  }, [turmaId]);

  const fetchDadosAluno = async () => {
    try {
      const response = await api.get('/api/v1/area-aluno');
      const dadosTurma = response.data.find(m => String(m.turma?.id) === String(turmaId));
      if (dadosTurma) {
        setAtividades(dadosTurma.turma?.atividadesAvaliativas || []);
        const notasFormatadas = (dadosTurma.notas || []).map(n => ({
          id: n.id,
          valor: n.valor,
          atividadeAvaliativaId: n.atividadeAvaliativa?.id,
          matriculaId: dadosTurma.id
        }));
        setNotas(notasFormatadas);
      }
    } catch (error) {
      console.error(error);
    }
  };

  const fetchMatriculas = async () => {
    try {
      const response = await api.get(`/api/v1/turmas/${turmaId}/matricula`);
      setMatriculas(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const fetchAtividades = async () => {
    try {
      const response = await api.get(`/api/v1/turmas/${turmaId}/atividades-avaliativas`);
      setAtividades(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const fetchNotasPorMatricula = async (e) => {
    if (e) e.preventDefault();
    if (!idMatricula) return alert("Por favor, selecione um aluno para buscar.");

    try {
      const response = await api.get(`/api/v1/turmas/${turmaId}/notas/${idMatricula}`);
      setNotas(Array.isArray(response.data) ? response.data : [response.data]);
    } catch (error) {
      console.error(error);
      alert("Erro ao buscar notas para esta matrícula.");
    }
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    const atividadeSelecionada = atividades.find(a => String(a.id) === String(avaliacaoId));
    if (atividadeSelecionada && Number(valor) > atividadeSelecionada.nota) {
      alert(`A nota não pode ser maior que o valor máximo da avaliação (${atividadeSelecionada.nota}).`);
      return;
    }

    try {
      await api.post(`/api/v1/turmas/${turmaId}/notas/${idMatricula}?atividadeAvaliativaId=${avaliacaoId}&valor=${valor}`);
      alert("Nota lançada com sucesso!");
      resetForm();
      fetchNotasPorMatricula();
    } catch (error) {
      console.error(error);
      alert("Erro ao lançar nota.");
    }
  };

  const handleEdit = (notaExistente) => {
    setEditingId(notaExistente.id);
    setValor(notaExistente.valor);
    setAvaliacaoId(notaExistente.atividadeAvaliativaId || notaExistente.avaliacaoId || "");
    setView("edit");
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    const atividadeSelecionada = atividades.find(a => String(a.id) === String(avaliacaoId));
    if (atividadeSelecionada && Number(valor) > atividadeSelecionada.nota) {
      alert(`A nota não pode ser maior que o valor máximo da avaliação (${atividadeSelecionada.nota}).`);
      return;
    }

    try {
      await api.put(`/api/v1/turmas/${turmaId}/notas/${idMatricula}/${editingId}?valor=${valor}`);
      alert("Nota updated com sucesso!");
      resetForm();
      fetchNotasPorMatricula();
    } catch (error) {
      console.error(error);
      alert("Erro ao atualizar nota.");
    }
  };

  const handleDelete = async (mId, id) => {
    if (!window.confirm("Deseja realmente excluir esta nota?")) return;
    try {
      await api.delete(`/api/v1/turmas/${turmaId}/notas/${mId}/${id}`);
      alert("Nota excluída!");
      fetchNotasPorMatricula();
    } catch (error) {
      console.error(error);
      alert("Erro ao excluir nota.");
    }
  };

  const resetForm = () => {
    setValor("");
    setAvaliacaoId("");
    setEditingId(null);
    setView("list");
  };

  const atividadeSelecionada = atividades.find(a => String(a.id) === String(avaliacaoId));
  const maxNota = atividadeSelecionada ? atividadeSelecionada.nota : "";

  return (
    <div className="Sala">
      {view === "list" && (
        <>
          <div className="ParteSala">
            <h3>👥 Gerenciamento de Notas</h3>
            {role !== "ALUNO" && (
              <Button className="aAcao" onClick={() => setView("create")}>
                Cadastrar Nova Nota
              </Button>
            )}

            {role !== "ALUNO" && (
              <form className="FormularioSelecao" onSubmit={fetchNotasPorMatricula}>
                <select
                  className="InputForm"
                  value={idMatricula}
                  onChange={(e) => setIdMatricula(e.target.value)}
                  required
                >
                  <option value="">Selecione um Aluno para buscar as notas...</option>
                  {matriculas.map((m) => (
                    <option key={m.id} value={m.id}>
                      {m.aluno?.nome} (Matrícula ID: {m.aluno.id})
                    </option>
                  ))}
                </select>
                <button type="submit" className="login-button" style={{ marginTop: "10px" }}>
                  Buscar Notas
                </button>
              </form>
            )}

            <table className="Tabela">
              <thead>
                <tr className="Info">
                  <th className="Coluna">Aluno</th>
                  <th className="Coluna">Avaliação</th>
                  <th className="Coluna">Nota</th>
                  {role !== "ALUNO" && <th className="LinhaAcao">Ações</th>}
                </tr>
              </thead>
              <tbody>
                {notas.length === 0 ? (
                  <tr>
                    <td colSpan={role === "ALUNO" ? "3" : "4"} style={{ textAlign: "center", padding: "15px" }}>
                      Nenhuma nota listada.
                    </td>
                  </tr>
                ) : (
                  notas.map((n) => {
                    const dadosMatricula = matriculas.find(m => String(m.id) === String(n.matriculaId || idMatricula));
                    const dadosAtv = atividades.find(a => String(a.id) === String(n.atividadeAvaliativaId));
                    const nomeExibicao = role === "ALUNO"
                      ? (localStorage.getItem('usuario') ? JSON.parse(localStorage.getItem('usuario')) : "")
                      : (dadosMatricula?.aluno?.nome || `Matrícula: ${n.matriculaId || idMatricula}`);

                    return (
                      <tr key={n.id}>
                        <td className="LinhaDado">{nomeExibicao}</td>
                        <td className="LinhaDado">{dadosAtv?.nome || "Avaliação"}</td>
                        <td className="LinhaDado">{n.valor}</td>
                        {role !== "ALUNO" && (
                          <td className="LinhaAcao">
                            <button className="aAcao" onClick={() => handleEdit(n)} style={{ marginRight: "10px" }}>
                              Editar
                            </button>
                            <button className="aAcao" onClick={() => handleDelete(n.matriculaId || idMatricula, n.id)}>
                              Excluir
                            </button>
                          </td>
                        )}
                      </tr>
                    );
                  })
                )}
              </tbody>
            </table>
          </div>
        </>
      )}

      {(view === "create" || view === "edit") && role !== "ALUNO" && (
        <>
          <div className="TurmaTodo">
            <h1 className="Titulo">{view === "create" ? "Lançar Nota" : "Editar Nota"}</h1>

            <form className="Formulario" onSubmit={view === "create" ? handleCreate : handleUpdate}>
              <div className="Input">
                <select
                  className="InputForm"
                  value={idMatricula}
                  onChange={(e) => setIdMatricula(e.target.value)}
                  required
                  disabled={view === "edit"} 
                >
                  <option value="">Selecione o Aluno...</option>
                  {matriculas.map((m) => (
                    <option key={m.id} value={m.id}>
                      {m.aluno?.nome} (Matrícula: {m.aluno.id})
                    </option>
                  ))}
                </select>

                <select
                  className="InputForm"
                  value={avaliacaoId}
                  onChange={(e) => setAvaliacaoId(e.target.value)}
                  required
                >
                  <option value="">Selecione a Avaliação...</option>
                  {atividades.map((atv) => (
                    <option key={atv.id} value={atv.id}>
                      {atv.nome} (Vale: {atv.nota})
                    </option>
                  ))}
                </select>

                <input
                  className="InputForm"
                  type="number"
                  max={maxNota}
                  step="0.01"
                  min="0"
                  placeholder={maxNota ? `Nota Obtida (Máx: ${maxNota})` : "Nota Obtida (Valor)"}
                  value={valor}
                  onChange={(e) => setValor(e.target.value)}
                  required
                />
              </div>

              <div className="ContinuaTodo">
                <button type="submit" className="login-button">
                  {view === "create" ? "Lançar" : "Salvar Alterações"}
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

export default Notas;