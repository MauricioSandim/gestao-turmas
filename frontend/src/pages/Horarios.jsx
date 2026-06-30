import { useParams } from "react-router-dom";
import Button from "../components/Button";
import { useState, useEffect } from "react";
import api from "../services/api";

function Horarios() {
  const { turmaId } = useParams();
  const [horarios, setHorarios] = useState([]);
  const [view, setView] = useState("list");
  const [hora, setHora] = useState("");
  const [diaSemana, setDiaSemana] = useState("SEGUNDA");
  const [editingId, setEditingId] = useState(null);
  const [role, setRole] = useState("");

  useEffect(() => {
    const localRole = localStorage.getItem('role') ? JSON.parse(localStorage.getItem('role')) : "";
    setRole(localRole);

    if (turmaId) {
      if (localRole === "ALUNO") {
        fetchDadosAluno();
      } else {
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
      }
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
      alert("Falha ao carregar horários.");
    }
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    try {
      await api.post(`/api/v1/turmas/${turmaId}/horarios-aula`, { hora, diaSemana });
      resetForm();
      fetchHorarios();
    } catch (error) {
      console.error(error);
      alert("Erro ao criar horário.");
    }
  };

  const handleEdit = (horario) => {
    setEditingId(horario.id);
    setHora(horario.hora);
    setDiaSemana(horario.diaSemana);
    setView("edit");
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await api.put(`/api/v1/turmas/${turmaId}/horarios-aula/${editingId}`, { hora, diaSemana });
      resetForm();
      fetchHorarios();
    } catch (error) {
      console.error(error);
      alert("Erro ao atualizar horário.");
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Tem certeza que deseja excluir este horário?")) return;
    try {
      await api.delete(`/api/v1/turmas/${turmaId}/horarios-aula/${id}`);
      fetchHorarios();
    } catch (error) {
      console.error(error);
      alert("Erro ao deletar horário.");
    }
  };

  const resetForm = () => {
    setHora("");
    setDiaSemana("SEGUNDA");
    setEditingId(null);
    setView("list");
  };

  return (
    <div className="Sala">
      {view === "list" && (
        <>
          <div className="ParteSala">
            <h3>👥 Gerenciamento de Horários</h3>
            {role !== "ALUNO" && (
              <Button className="aAcao" onClick={() => setView("create")}>
                Cadastrar Novo Horário
              </Button>
            )}

            <table className="Tabela">
              <thead>
                <tr className="Info">
                  <th className="Coluna">Dia da Semana</th>
                  <th className="Coluna">Hora</th>
                  {role !== "ALUNO" && <th className="LinhaAcao">Ações</th>}
                </tr>
              </thead>
              <tbody>
                {horarios.length === 0 ? (
                  <tr>
                    <td colSpan={role === "ALUNO" ? "2" : "3"} style={{ textAlign: "center", padding: "15px" }}>
                      Nenhum horário cadastrado para esta turma.
                    </td>
                  </tr>
                ) : (
                  horarios.map((h) => (
                    <tr key={h.id}>
                      <td className="LinhaDado">{h.diaSemana}</td>
                      <td className="LinhaDado">{h.hora}</td>
                      {role !== "ALUNO" && (
                        <td className="LinhaAcao">
                          <button className="aAcao" onClick={() => handleEdit(h)} style={{ marginRight: "10px" }}>
                            Editar
                          </button>
                          <button className="aAcao" onClick={() => handleDelete(h.id)}>
                            Excluir
                          </button>
                        </td>
                      )}
                    </tr>
                  ))
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
              {view === "create" ? "Novo Horário" : "Editar Horário"}
            </h1>

            <form className="Formulario" onSubmit={view === "create" ? handleCreate : handleUpdate}>
              <div className="Input">
                <input
                  className="InputForm"
                  type="text"
                  placeholder="Hora"
                  value={hora}
                  onChange={(e) => setHora(e.target.value)}
                  required
                  maxLength={30}
                />

                <select
                  className="InputForm"
                  value={diaSemana}
                  onChange={(e) => setDiaSemana(e.target.value)}
                >
                  <option value="SEGUNDA">Segunda</option>
                  <option value="TERÇA">Terça</option>
                  <option value="QUARTA">Quarta</option>
                  <option value="QUINTA">Quinta</option>
                  <option value="SEXTA">Sexta</option>
                  <option value="SABADO">Sábado</option>
                  <option value="DOMINGO">Domingo</option>
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

export default Horarios;