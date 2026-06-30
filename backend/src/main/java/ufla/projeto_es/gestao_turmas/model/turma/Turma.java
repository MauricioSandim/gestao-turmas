package ufla.projeto_es.gestao_turmas.model.turma;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ufla.projeto_es.gestao_turmas.exception.NotFoundException;
import ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva.AtividadeAvaliativa;
import ufla.projeto_es.gestao_turmas.model.atividadeAvalitiva.request.UpdateAtividadeAvaliativaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.baseEntity.OwnedEntity;
import ufla.projeto_es.gestao_turmas.model.horarioAula.HorarioAula;
import ufla.projeto_es.gestao_turmas.model.matricula.Matricula;
import ufla.projeto_es.gestao_turmas.model.type.RoleEnum;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "turma")
public class Turma extends OwnedEntity<Long> {

    @Column(name = "nome", length = 255, nullable = false)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // ATIVIDADE AVALIATIVA
    @OneToMany(mappedBy = "turma", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderColumn(name = "position")
    private List<AtividadeAvaliativa> atividadesAvaliativas;

    public void addAtividadeAvaliativa(AtividadeAvaliativa atividadeAvaliativa) {
        atividadesAvaliativas.forEach((e) -> {
            if (e.getNome().equals(atividadeAvaliativa.getNome()))
                throw new IllegalArgumentException("Atividade avaliativa já cadastrado para turma de id:" + this.getId());
        });

        atividadeAvaliativa.setTurma(this);

        atividadesAvaliativas.add(atividadeAvaliativa);
    }

    public void removeAtividadeAvaliativa(Long id) {

        AtividadeAvaliativa atividadeAvaliativa = atividadesAvaliativas.stream().filter((e) -> Objects.equals(e.getId(), id)).findFirst().orElseThrow(() -> new NotFoundException("Atividade avaliativa com id: " + id + " não existente na turma com id: " + this.getId()));

        if (!atividadeAvaliativa.getNotas().isEmpty())
            throw new IllegalArgumentException("Não é possível remover atividade avaliativa que possui notas cadastradas");

        atividadesAvaliativas.remove(atividadeAvaliativa);
    }

    public void updateAtividadeAvaliativa(Long id, UpdateAtividadeAvaliativaRequestDTO requestDTO) {
        atividadesAvaliativas.forEach((e) -> {
            if (e.getNome().equals(requestDTO.nome()) && !Objects.equals(id, e.getId()))
                throw new IllegalArgumentException("Atividade avaliativa com nome : " + requestDTO.nome() + "já cadastrado para turma de id:" + this.getId());
        });

        AtividadeAvaliativa atividadeAvaliativaAtual = atividadesAvaliativas.stream().filter((e) -> Objects.equals(e.getId(), id)).findFirst().orElseThrow(() -> new NotFoundException("Atividade avaliativa com id: " + id + " não existente na turma com id: " + this.getId()));

        if (!atividadeAvaliativaAtual.getNotas().isEmpty())
            throw new IllegalArgumentException("Não é possível remover atividade avaliativa que possui notas cadastradas");

        atividadeAvaliativaAtual.setNome(requestDTO.nome());
        atividadeAvaliativaAtual.setNota(requestDTO.nota());
    }

    // HORARIO AULA
    @OneToMany(mappedBy = "turma", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderColumn(name = "position")
    private List<HorarioAula> horariosAula;

    public void addHorarioAula(HorarioAula horarioAula) {
        horariosAula.forEach((e) -> {
            if (e.getHora().equals(horarioAula.getHora()) && e.getDiaSemana() == horarioAula.getDiaSemana())
                throw new IllegalArgumentException("Horario aula já cadastrado para turma de id:" + this.getId());
        });

        horarioAula.setTurma(this);

        horariosAula.add(horarioAula);
    }

    public void removeHorarioAula(Long id) {
        HorarioAula horarioAula = horariosAula.stream().filter((e) -> Objects.equals(e.getId(), id)).findFirst().orElseThrow(() -> new NotFoundException("Horario aula com id: " + id + " não existente na turma com id: " + this.getId()));

        if (!horarioAula.getFalta().isEmpty())
            throw new IllegalArgumentException("Não é possível remover horário de aula que possui faltas cadastradas");

        horariosAula.remove(horarioAula);
    }

    public void updateHorarioAula(Long id, HorarioAula horarioAula) {
        horariosAula.forEach((e) -> {
            if (e.getHora().equals(horarioAula.getHora()) && e.getDiaSemana() == horarioAula.getDiaSemana())
                throw new IllegalArgumentException("Horario aula já cadastrado para turma de id:" + this.getId());
        });

        HorarioAula horarioAulaAtual = horariosAula.stream().filter((e) -> Objects.equals(e.getId(), id)).findFirst().orElseThrow(() -> new NotFoundException("Horario aula com id: " + id + " não existente na turma com id: " + this.getId()));

        if (!horarioAulaAtual.getFalta().isEmpty())
            throw new IllegalArgumentException("Não é possível modificar horário de aula que possui faltas cadastradas");

        horarioAulaAtual.setHora(horarioAula.getHora());
        horarioAulaAtual.setDiaSemana(horarioAula.getDiaSemana());
    }

    // MATRICULA
    @OneToMany(mappedBy = "turma", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderColumn(name = "position")
    private List<Matricula> matriculas;

    public List<Usuario> getAlunos() {
        return matriculas.stream().map(Matricula::getAluno).toList();
    }

    public Matricula addAluno(Usuario aluno) {
        if (!aluno.getRoleName().equals(RoleEnum.ALUNO.name())) {
            throw new RuntimeException();
        }

        Matricula matricula = new Matricula(aluno, this);

        matriculas.add(matricula);

        return matricula;
    }

    public void removeAluno(Usuario aluno) {
        Matricula matricula = matriculas.stream().filter((e) -> Objects.equals(e.getAluno().getId(), aluno.getId())).findFirst().orElseThrow(() -> new NotFoundException("Aluno com id: " + aluno.getId() + " não matriculado na turma com id: " + this.getId()));

        if (!matricula.getNotas().isEmpty() || !matricula.getFaltas().isEmpty()) {
            throw new IllegalArgumentException("Não é possível remover o aluno da turma, pois ele possui notas ou faltas");
        }

        matriculas.remove(matricula);
    }
}
