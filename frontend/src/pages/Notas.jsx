import { useParams } from "react-router-dom";

function Notas () {

    const { nomeTurma } = useParams();

  return (
    <div>
      <h3>👥 Gerenciamento de notas</h3>
      <button>+ Cadastrar Novo Aluno</button>
      
      <table style={{ width: '100%', marginTop: '20px', borderCollapse: 'collapse' }}>
        <thead>
          <tr style={{ background: '#ddd' }}>
            <th style={{ padding: '8px', textAlign: 'left' }}>Matrícula</th>
            <th style={{ padding: '8px', textAlign: 'left' }}>Nome</th>
            <th style={{ padding: '8px', textAlign: 'center' }}>Ações</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td style={{ padding: '8px' }}>20260001</td>
            <td style={{ padding: '8px' }}>Fulano de Tal</td>
            <td style={{ padding: '8px', textAlign: 'center' }}>
              <button>Editar</button> <button style={{ color: 'red' }}>Excluir</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  );

}

export default Notas