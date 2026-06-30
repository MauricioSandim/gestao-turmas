import { useParams } from "react-router-dom";
import Button from "../components/Button";
import { useState } from "react";
import { useEffect } from "react";
import Input from "../components/Input";

function Faltas () {

  const { nomeTurma } = useParams();

  const [turmas, setTurmas] = useState([]); 
  const [view, setView] = useState("list"); 
  const [nome, setNome] = useState("");
  const [editingId, setEditingId] = useState(null);

  // Executa automaticamente quando a tela abre
  useEffect((e) => {

    fetchNotas();
    
  }, []);

  const fetchNotas = async () => {
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

 <div className="Sala">

      {view === "list" && (

        <>

            <div className="ParteSala">
                <h3>👥 Gerenciamento de Faltas</h3>
                <Button className="aAcao" onClick={() => setView("create")}>Cadastrar Novo Faltas</Button>

                <table className="Tabela">
                    <thead>
                    <tr className="Info">
                        <th className="Coluna">Aluno</th>
                        <th className="Coluna">dia</th>
                        <th className="LinhaAcao">Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td className="LinhaDado">20260001</td>
                        <td className="LinhaDado">09</td>
                        <td className="LinhaAcao">
                        <a className="aAcao">Editar</a> <a className="aAcao">Excluir</a>
                        </td>
                    </tr>
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
                  value={nome}
                  onChange={(e) => setNome(e.target.value)}
                  required
                  maxLength={30}
              />

                <input
                  className="InputForm"
                  type="text"
                  placeholder="Data"
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

                </div>

              </>

            )}

    </div>

  );

}

export default Faltas