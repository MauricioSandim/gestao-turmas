import { useParams } from "react-router-dom";
import Button from "../components/Button";
import { useState, useEffect } from "react";
import Input from "../components/Input";
import api from "../services/api";

function Notas() {
  const { turmaId } = useParams();

  // Estados principais
  const [notas, setNotas] = useState([]);
  const [view, setView] = useState("list");
  const [editingId, setEditingId] = useState(null);

  // Campos dos formulários (Padronizados)
  const [idMatricula, setIdMatricula] = useState("");
  const [valor, setValor] = useState("");
  const [avaliacaoId, setAvaliacaoId] = useState("");

  // 1. Busca as notas filtradas pela matrícula digitada
  const fetchNotasPorMatricula = async (e) => {
    if (e) e.preventDefault(); // Impede a página de recarregar no submit do form
    if (!idMatricula) return alert("Por favor, digite o ID da matrícula.");

    try {
      const response = await api.get(`/api/v1/turmas/${turmaId}/notas/${idMatricula}`);
      // Garante que o estado seja sempre uma lista/array para o .map não quebrar
      setNotas(Array.isArray(response.data) ? response.data : [response.data]);
    } catch (error) {
      console.error("Erro ao carregar notas:", error);
      alert("Erro ao buscar notas para esta matrícula.");
    }
  };

  // 2. Lançar nova nota
  const handleCreate = async (e) => {
    e.preventDefault();
    try {
      // Envia para o endpoint correto passando a matrícula na URL e os dados no corpo
      await api.post(`/api/v1/turmas/${turmaId}/notas/${idMatricula}`, {
        valor: parseFloat(valor),
        avaliacaoId: avaliacaoId
      });
      alert("Nota lançada com sucesso!");
      resetForm();
      fetchNotasPorMatricula();
    } catch (error) {
      console.error("Erro ao lançar nota", error);
      alert("Erro ao lançar nota.");
    }
  };

  // 3. Preparar edição
  const handleEdit = (notaExistente) => {
    setEditingId(notaExistente.id);
    setValor(notaExistente.valor);
    setAvaliacaoId(notaExistente.avaliacaoId);
    setView("edit");
  };

  // 4. Atualizar nota existente
  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await api.put(`/api/v1/turmas/${turmaId}/notas/${idMatricula}/${editingId}`, {
        valor: parseFloat(valor),
        avaliacaoId: avaliacaoId
      });
      alert("Nota atualizada com sucesso!");
      resetForm();
      fetchNotasPorMatricula();
    } catch (error) {
      console.error("Erro ao atualizar nota", error);
      alert("Erro ao atualizar nota.");
    }
  };

  // 5. Excluir nota
  const handleDelete = async (mId, id) => {
    if (!window.confirm("Deseja realmente excluir esta nota?")) return;
    try {
      await api.delete(`/api/v1/turmas/${turmaId}/notas/${mId}/${id}`);
      alert("Nota excluída!");
      fetchNotasPorMatricula();
    } catch (error) {
      console.error("Erro ao excluir nota", error);
      alert("Erro ao excluir nota.");
    }
  };

  const resetForm = () => {
    setValor("");
    setAvaliacaoId("");
    setEditingId(null);
    setView("list");
  };

  return (
    <div className="Sala">
      {view === "list" && (
        <>
          <div className="ParteSala">
            <h3>👥 Gerenciamento de Notas</h3>
            <Button className="aAcao" onClick={() => setView("create")}>
              Cadastrar Nova Nota
            </Button>

            {/* FORMULÁRIO DE BUSCA - Corrigido sem rodar direto */}
            <form className="Formulario" onSubmit={fetchNotasPorMatricula}>
              <Input
                onChange={(e) => setIdMatricula(e.target.value)}
                value={idMatricula}
                name="idMatricula"
                type="text"
                id="idMatricula"
                required
                placeholder="Digite o ID da Matrícula para buscar"
              />
              <button type="submit" className="login-button" style={{ marginTop: "10px" }}>
                Buscar Notas
              </button>
            </form>

            <table className="Tabela">
              <thead>
                <tr className="Info">
                  <th className="Coluna">Aluno (Matrícula)</th>
                  <th className="Coluna">Avaliação</th>
                  <th className="Coluna">Nota</th>
                  <th className="LinhaAcao">Ações</th>
                </tr>
              </thead>
              <tbody>
                {notas.length === 0 ? (
                  <tr>
                    <td colSpan="4">Nenhuma nota listada. Digite uma matrícula e busque.</td>
                  </tr>
                ) : (
                  notas.map((n) => (
                    <tr key={n.id}>
                      <td>{n.matriculaId || idMatricula}</td>
                      <td>{n.avaliacaoId}</td>
                      <td>{n.valor}</td>
                      <td>
                        <button className="aAcao" onClick={() => handleEdit(n)} style={{ marginRight: "10px" }}>
                          Editar
                        </button>
                        <button className="aAcao" onClick={() => handleDelete(n.matriculaId || idMatricula, n.id)}>
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
            <h1 className="Titulo">{view === "create" ? "Lançar Nota" : "Editar Nota"}</h1>

            <form className="Formulario" onSubmit={view === "create" ? handleCreate : handleUpdate}>
              <div className="Input">
                {/* Campo Matrícula (obrigatório para saber de quem é a nota) */}
                <Input
                  onChange={(e) => setIdMatricula(e.target.value)}
                  value={idMatricula}
                  name="idMatricula"
                  type="text"
                  required
                  placeholder="ID da Matrícula do Aluno"
                  disabled={view === "edit"} // Na edição costuma-se travar a matrícula
                />

                {/* Campo ID Avaliação */}
                <Input
                  onChange={(e) => setAvaliacaoId(e.target.value)}
                  value={avaliacaoId}
                  name="avaliacaoId"
                  type="text"
                  required
                  placeholder="ID da Avaliação"
                />

                {/* Campo Valor da Nota */}
                <Input
                  onChange={(e) => setValor(e.target.value)}
                  value={valor}
                  name="valor"
                  type="number"
                  step="0.1"
                  required
                  placeholder="Nota (Valor)"
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