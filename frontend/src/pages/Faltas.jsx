import { useParams } from "react-router-dom";
import Button from "../components/Button";
import { useState, useEffect } from "react";
import Input from "../components/Input";
import api from "../services/api";

function Faltas() {
  const { turmaId } = useParams();

  const [faltas, setFaltas] = useState([]);
  const [view, setView] = useState("list");
  const [matriculaId, setMatriculaId] = useState("");
  const [data, setData] = useState("");
  const [editingId, setEditingId] = useState(null);

  useEffect(() => {
    fetchFaltas();
  }, [turmaId]);

  const fetchFaltas = async () => {
    try {
      const response = await api.get(`/api/v1/turmas/${turmaId}/faltas`);
      setFaltas(response.data);
    } catch (error) {
      console.error(error);
      alert("Falha ao carregar faltas.");
    }
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    try {
      await api.post(`/api/v1/turmas/${turmaId}/faltas/${matriculaId}`, { data });
      resetForm();
      fetchFaltas();
    } catch (error) {
      console.error(error);
      alert("Erro ao lançar falta.");
    }
  };

  const handleEdit = (falta) => {
    setEditingId(falta.id);
    setMatriculaId(falta.matriculaId || falta.alunoId || "");
    setData(falta.data);
    setView("edit");
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await api.put(`/api/v1/turmas/${turmaId}/faltas/${matriculaId}/${editingId}`, { data });
      resetForm();
      fetchFaltas();
    } catch (error) {
      console.error(error);
      alert("Erro ao atualizar falta.");
    }
  };

  const handleDelete = async (mId, id) => {
    if (!window.confirm("Tem certeza que deseja excluir esta falta?")) return;
    try {
      await api.delete(`/api/v1/turmas/${turmaId}/faltas/${mId}/${id}`);
      fetchFaltas();
    } catch (error) {
      console.error(error);
      alert("Erro ao deletar falta.");
    }
  };

  const resetForm = () => {
    setMatriculaId("");
    setData("");
    setEditingId(null);
    setView("list");
  };

  return (
    <div className="Sala">
      {view === "list" && (
        <>
          <div className="ParteSala">
            <h3>👥 Gerenciamento de Faltas</h3>
            <Button className="aAcao" onClick={() => setView("create")}>
              Cadastrar Nova Falta
            </Button>

            <table className="Tabela">
              <thead>
                <tr className="Info">
                  <th className="Coluna">Aluno</th>
                  <th className="Coluna">Dia</th>
                  <th className="LinhaAcao">Ações</th>
                </tr>
              </thead>
              <tbody>
                {faltas.length === 0 ? (
                  <tr>
                    <td colSpan="3" style={{ textAlign: "center", padding: "15px" }}>
                      Nenhuma falta cadastrada para esta turma.
                    </td>
                  </tr>
                ) : (
                  faltas.map((f) => (
                    <tr key={f.id}>
                      <td className="LinhaDado">{f.matriculaId || f.alunoNome || f.alunoId}</td>
                      <td className="LinhaDado">{f.data}</td>
                      <td className="LinhaAcao">
                        <button className="aAcao" onClick={() => handleEdit(f)} style={{ marginRight: "10px" }}>
                          Editar
                        </button>
                        <button className="aAcao" onClick={() => handleDelete(f.matriculaId || f.alunoId, f.id)}>
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

      {(view === "create" || view === "edit") && (
        <>
          <div className="TurmaTodo">
            <h1 className="Titulo">
              {view === "create" ? "Lançar Falta" : "Editar Falta"}
            </h1>

            <form className="Formulario" onSubmit={view === "create" ? handleCreate : handleUpdate}>
              <div className="Input">
                <input
                  className="InputForm"
                  type="text"
                  placeholder="Aluno"
                  value={matriculaId}
                  onChange={(e) => setMatriculaId(e.target.value)}
                  required
                  maxLength={30}
                  disabled={view === "edit"}
                />

                <input
                  className="InputForm"
                  type="text"
                  placeholder="Data"
                  value={data}
                  onChange={(e) => setData(e.target.value)}
                  required
                  maxLength={30}
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