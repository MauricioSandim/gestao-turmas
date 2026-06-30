import { useParams } from "react-router-dom";
import Button from "../components/Button";
import { useState, useEffect } from "react";
import api from "../services/api";

function Faltas() {
  const { turmaId } = useParams();
  const [faltas, setFaltas] = useState([]);
  const [matriculas, setMatriculas] = useState([]); 
  const [horarios, setHorarios] = useState([]); 
  const [view, setView] = useState("list");
  const [editingId, setEditingId] = useState(null);
  const [matriculaId, setMatriculaId] = useState(""); 
  const [data, setData] = useState(""); 
  const [horarioAulaId, setHorarioAulaId] = useState(""); 
  const [idMatricula, setIdMatricula] = useState("");
  const [role, setRole] = useState("");

  useEffect(() => {
    const localRole = localStorage.getItem('role') ? JSON.parse(localStorage.getItem('role')) : "";
    setRole(localRole);

    if (turmaId) {
      if (localRole === "ALUNO") {
        fetchDadosAluno();
      } else {
        fetchMatriculas();
        fetchHorarios();
      }
    }
  }, [turmaId]);

  const fetchDadosAluno = async () => {
    try {
      const response = await api.get('/api/v1/area-aluno');
      const dadosTurma = response.data.find(m => String(m.turma?.id) === String(turmaId));
      if (dadosTurma) {
        setHorarios(dadosTurma.turma?.horariosAula || []);
        const faltasFormatadas = (dadosTurma.faltas || []).map(f => ({
          id: f.id,
          data: f.data,
          horarioAulaId: f.horarioAula?.id,
          matriculaId: dadosTurma.id
        }));
        setFaltas(faltasFormatadas);
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

  const fetchHorarios = async () => {
    try {
      const response = await api.get(`/api/v1/turmas/${turmaId}/horarios-aula`);
      setHorarios(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const formatarDataExibicao = (dataISO) => {
    if (!dataISO) return "-";
    if (dataISO.includes("/")) return dataISO; 
    const [ano, mes, dia] = dataISO.split("-");
    return `${dia}/${mes}/${ano}`;
  };

  const fetchFaltasPorMatricula = async (e) => {
    if (e) e.preventDefault();
    if (!idMatricula) return alert("Por favor, selecione um aluno para buscar.");

    try {
      const response = await api.get(`/api/v1/turmas/${turmaId}/faltas/${idMatricula}`);
      setFaltas(Array.isArray(response.data) ? response.data : [response.data]);
    } catch (error) {
      console.error(error);
      alert("Erro ao buscar faltas para esta matrícula.");
      setFaltas([]);
    }
  };

  const refreshFaltas = async (idParaBuscar) => {
    const alvo = idParaBuscar || idMatricula;
    if (!alvo) return;
    try {
      const response = await api.get(`/api/v1/turmas/${turmaId}/faltas/${alvo}`);
      setFaltas(Array.isArray(response.data) ? response.data : [response.data]);
    } catch (error) {
      console.error(error);
    }
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    if (!matriculaId) return alert("Por favor, selecione um aluno.");
    if (!horarioAulaId) return alert("Por favor, selecione um horário.");

    try {
      await api.post(`/api/v1/turmas/${turmaId}/faltas/${matriculaId}`, { 
        data, 
        horarioAulaId: Number(horarioAulaId) 
      });
      alert("Falta lançada com sucesso!");
      setIdMatricula(matriculaId);
      await refreshFaltas(matriculaId);
      resetForm();
    } catch (error) {
      console.error(error);
      alert("Erro ao lançar falta.");
    }
  };

  const handleEdit = (falta) => {
    setEditingId(falta.id);
    setMatriculaId(falta.matriculaId || idMatricula || "");
    setHorarioAulaId(falta.horarioAulaId || ""); 
    
    if (falta.data && falta.data.includes("/")) {
      const [dia, mes, ano] = falta.data.split("/");
      setData(`${ano}-${mes}-${dia}`);
    } else {
      setData(falta.data || "");
    }
    setView("edit");
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await api.put(`/api/v1/turmas/${turmaId}/faltas/${matriculaId}/${editingId}`, { 
        data, 
        horarioAulaId: Number(horarioAulaId) 
      });
      alert("Falta updated com sucesso!");
      resetForm();
      refreshFaltas();
    } catch (error) {
      console.error(error);
      alert("Erro ao atualizar falta.");
    }
  };

  const handleDelete = async (mId, id) => {
    const matriculaAlvo = mId || idMatricula;
    if (!window.confirm("Tem certeza que deseja excluir esta falta?")) return;
    
    try {
      await api.delete(`/api/v1/turmas/${turmaId}/faltas/${matriculaAlvo}/${id}`);
      alert("Falta excluída!");
      if (faltas.length === 1) {
        setFaltas([]); 
      } else {
        refreshFaltas(matriculaAlvo);
      }
    } catch (error) {
      console.error(error);
      alert("Erro ao deletar falta.");
    }
  };

  const resetForm = () => {
    setMatriculaId("");
    setData("");
    setHorarioAulaId("");
    setEditingId(null);
    setView("list");
  };

  return (
    <div className="Sala">
      {view === "list" && (
        <>
          <div className="ParteSala">
            <h3>👥 Gerenciamento de Faltas</h3>

            {role !== "ALUNO" && (
              <Button className="aAcao" onClick={() => setView("create")}>
                Cadastrar Nova Falta
              </Button>
            )}

            {role !== "ALUNO" && (
              <form className="FormularioSelecao" onSubmit={fetchFaltasPorMatricula}>
                <select
                  className="InputForm"
                  value={idMatricula}
                  onChange={(e) => setIdMatricula(e.target.value)}
                  required
                >
                  <option value="">Selecione um Aluno para buscar as faltas...</option>
                  {matriculas.map((m) => (
                    <option key={m.id} value={m.id}>
                      {m.aluno?.nome} (Matrícula ID: {m.aluno.id})
                    </option>
                  ))}
                </select>
                <button type="submit" className="login-button" style={{ marginTop: "10px" }}>
                  Buscar Faltas
                </button>
              </form>
            )}

            <table className="Tabela">
              <thead>
                <tr className="Info">
                  <th className="Coluna">Aluno</th>
                  <th className="Coluna">Horário da Aula</th>
                  <th className="Coluna">Dia / Data</th>
                  {role !== "ALUNO" && <th className="LinhaAcao">Ações</th>}
                </tr>
              </thead>
              <tbody>
                {faltas.length === 0 ? (
                  <tr>
                    <td colSpan={role === "ALUNO" ? "3" : "4"} style={{ textAlign: "center", padding: "15px" }}>
                      Nenhuma falta listada.
                    </td>
                  </tr>
                ) : (
                  faltas.map((f) => {
                    const correspondenteAluno = matriculas.find(
                      (m) => String(m.id) === String(f.matriculaId || idMatricula)
                    );
                    const correspondenteHorario = horarios.find(
                      (h) => String(h.id) === String(f.horarioAulaId)
                    );
                    const nomeExibicao = role === "ALUNO" 
                      ? (localStorage.getItem('usuario') ? JSON.parse(localStorage.getItem('usuario')) : "")
                      : (correspondenteAluno?.aluno?.nome || `Matrícula: ${f.matriculaId || idMatricula}`);

                    return (
                      <tr key={f.id}>
                        <td className="LinhaDado">{nomeExibicao}</td>
                        <td className="LinhaDado">
                          {correspondenteHorario 
                            ? `${correspondenteHorario.diaSemana} - ${correspondenteHorario.hora}`
                            : `ID do Horário: ${f.horarioAulaId || "-"}`
                          }
                        </td>
                        <td className="LinhaDado">{formatarDataExibicao(f.data)}</td>
                        {role !== "ALUNO" && (
                          <td className="LinhaAcao">
                            <button className="aAcao" onClick={() => handleEdit(f)} style={{ marginRight: "10px" }}>
                              Editar
                            </button>
                            <button 
                              className="aAcao" 
                              onClick={() => handleDelete(f.matriculaId || idMatricula, f.id)}
                            >
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
            <h1 className="Titulo">
              {view === "create" ? "Lançar Falta" : "Editar Falta"}
            </h1>

            <form className="Formulario" onSubmit={view === "create" ? handleCreate : handleUpdate}>
              <div className="Input">
                <select
                  className="InputForm"
                  value={matriculaId}
                  onChange={(e) => setMatriculaId(e.target.value)}
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
                  value={horarioAulaId}
                  onChange={(e) => setHorarioAulaId(e.target.value)}
                  required
                >
                  <option value="">Selecione o Horário da Aula...</option>
                  {horarios.map((h) => (
                    <option key={h.id} value={h.id}>
                      {h.diaSemana} ({h.hora})
                    </option>
                  ))}
                </select>

                <input
                  className="InputForm"
                  type="date"
                  value={data}
                  onChange={(e) => setData(e.target.value)}
                  required
                />
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

export default Faltas;