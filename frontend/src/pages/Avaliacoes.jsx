import { useParams } from "react-router-dom";
import Button from "../components/Button";
import { useState, useEffect } from "react";
import Input from "../components/Input";
import api from "../services/api";

function Avaliacoes() {
  // Captura o ID da turma enviado pela URL da rota
  const { turmaId } = useParams();

  const [atividades, setAtividades] = useState([]); 
  const [view, setView] = useState("list"); 
  
  // Estados separados para cada campo do formulário
  const [nome, setNome] = useState("");
  const [nota, setNota] = useState("");
  const [dataProva, setDataProva] = useState("");
  const [editingId, setEditingId] = useState(null);

  // Executa automaticamente quando a tela abre ou a turma muda
  useEffect(() => {
    fetchAtividades();
  }, [turmaId]);

  const fetchAtividades = async () => {
    try {
      const response = await api.get(`/api/v1/turmas/${turmaId}/atividades-avaliativas`); 
      setAtividades(response.data);
    } catch (error) {
      console.error("Erro detalhado do backend:", error.response);
      alert("Falha ao carregar avaliações.");
    }
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    try {
      await api.post(`/api/v1/turmas/${turmaId}/atividades-avaliativas`, { 
        nome, 
        nota, 
        dataProva 
      });
      resetForm();
      fetchAtividades();
    } catch (error) {
      console.error("Erro ao criar avaliação", error);
      alert("Erro ao criar avaliação.");
    }
  };

  const handleEdit = (atividade) => {
    setEditingId(atividade.id);
    setNome(atividade.nome);
    setNota(atividade.nota || atividade.valor || "");
    setDataProva(atividade.dataProva || atividade.data || "");
    setView("edit");
  };

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await api.put(`/api/v1/turmas/${turmaId}/atividades-avaliativas/${editingId}`, { 
        nome, 
        nota, 
        dataProva 
      });
      resetForm();
      fetchAtividades();
    } catch (error) {
      console.error("Erro ao atualizar avaliação", error);
      alert("Erro ao atualizar avaliação.");
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Tem certeza que deseja excluir esta avaliação?")) return;
    try {
      await api.delete(`/api/v1/turmas/${turmaId}/atividades-avaliativas/${id}`);
      fetchAtividades();
    } catch (error) {
      console.error("Erro ao deletar avaliação", error);
      alert("Erro ao deletar avaliação.");
    }
  };

  const resetForm = () => {
    setNome("");
    setNota("");
    setDataProva("");
    setEditingId(null);
    setView("list");
  };

  return (
    <div className="Sala">
      {view === "list" && (
        <>
          <div className="ParteSala">
            <h3>👥 Gerenciamento de Avaliações</h3>
            <Button className="aAcao" onClick={() => setView("create")}>
              Cadastrar Nova Avaliação
            </Button>

            <table className="Tabela">
              <thead>
                <tr className="Info">
                  <th className="Coluna">Avaliação</th>
                  <th className="Coluna">Nota Máxima</th>
                  <th className="Coluna">Data da Prova</th>
                  <th className="LinhaAcao">Ações</th>
                </tr>
              </thead>
              <tbody>
                {atividades.length === 0 ? (
                  <tr>
                    <td colSpan="4" style={{ textAlign: "center", padding: "15px" }}>
                      Nenhuma avaliação cadastrada para esta turma.
                    </td>
                  </tr>
                ) : (
                  atividades.map((atv) => (
                    <tr key={atv.id}>
                      <td className="LinhaDado">{atv.nome}</td>
                      <td className="LinhaDado">{atv.nota || atv.valor}</td>
                      <td className="LinhaDado">{atv.dataProva || atv.data}</td>
                      <td className="LinhaAcao">
                        <button className="aAcao" onClick={() => handleEdit(atv)} style={{ marginRight: "10px" }}>
                          Editar
                        </button>
                        <button className="aAcao" onClick={() => handleDelete(atv.id)}>
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
              {view === "create" ? "Nova Avaliação" : "Editar Avaliação"}
            </h1>

            <form className="Formulario" onSubmit={view === "create" ? handleCreate : handleUpdate}>
              <div className="Input">
                <input
                  className="InputForm"
                  type="text"
                  placeholder="Nome da Prova"
                  value={nome}
                  onChange={(e) => setNome(e.target.value)}
                  required
                  maxLength={30}
                />

                <input
                  className="InputForm"
                  type="text"
                  placeholder="Valor Máximo"
                  value={nota}
                  onChange={(e) => setNota(e.target.value)}
                  required
                  maxLength={30}
                />

                <input
                  className="InputForm"
                  type="text"
                  placeholder="Data da Prova"
                  value={dataProva}
                  onChange={(e) => setDataProva(e.target.value)}
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

export default Avaliacoes;