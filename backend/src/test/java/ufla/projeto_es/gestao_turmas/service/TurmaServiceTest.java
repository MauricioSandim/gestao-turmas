package ufla.projeto_es.gestao_turmas.service;

import org.junit.jupiter.api.Assertions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.mapstruct.factory.Mappers;

import ufla.projeto_es.gestao_turmas.exception.NotFoundException;
import ufla.projeto_es.gestao_turmas.exception.turma.TurmaComNomesIguaisException;
import ufla.projeto_es.gestao_turmas.mapper.TurmaMapper;
import ufla.projeto_es.gestao_turmas.model.roles.Role;
import ufla.projeto_es.gestao_turmas.model.turma.Turma;
import ufla.projeto_es.gestao_turmas.model.turma.request.CreateTurmaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.turma.request.UpdateTurmaRequestDTO;
import ufla.projeto_es.gestao_turmas.model.type.RoleEnum;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.repository.TurmaRepository;

@ExtendWith(MockitoExtension.class)
class TurmaServiceTest {

        @Mock
        private TurmaRepository turmaRepository;

        private TurmaMapper turmaMapper;

        private TurmaService turmaService;

        @BeforeEach
        void setup() {
                turmaMapper = Mappers.getMapper(TurmaMapper.class);

                turmaService = new TurmaService(turmaRepository, turmaMapper);
        }

        @Test
        void deveCriarTurmaComSucesso() {

                CreateTurmaRequestDTO dto = new CreateTurmaRequestDTO("2°Ano A");

                Usuario usuario = new Usuario();
                usuario.setId(1L);
                Role role = new Role();
                role.setNome(RoleEnum.PROFESSOR);
                usuario.setRole(role);

                when(
                                turmaRepository.existsByNomeAndUsuario_Id(
                                                "2°Ano A",
                                                1L))
                                .thenReturn(false);

                when(turmaRepository.save(any()))
                                .thenAnswer(invocation -> invocation.getArgument(0));

                Turma turmaCriada = turmaService.criar(dto, usuario);

                Assertions.assertNotNull(turmaCriada);

                Assertions.assertEquals(
                                "2°Ano A",
                                turmaCriada.getNome());

                Assertions.assertEquals(
                                usuario,
                                turmaCriada.getUsuario());

                verify(turmaRepository, times(1)).save(any(Turma.class));
        }

        @Test
        void naoDeveCriarTurmaSeUsuarioNaoForProfessor() {

                CreateTurmaRequestDTO dto = new CreateTurmaRequestDTO("2°Ano A");

                Usuario usuario = new Usuario();
                usuario.setId(1L);
                Role role = new Role();
                role.setNome(RoleEnum.ALUNO);
                usuario.setRole(role);

                AccessDeniedException thrown = Assertions.assertThrows(
                                AccessDeniedException.class,
                                () -> turmaService.criar(dto, usuario));

                Assertions.assertEquals("Acesso negado", thrown.getMessage());

                verify(turmaRepository, never()).save(any());
        }

        @Test
        void naoDeveCriarTurmaComNomeExistente() {

                String nome = "2°Ano A";

                CreateTurmaRequestDTO dto = new CreateTurmaRequestDTO(nome);

                Usuario usuario = new Usuario();
                usuario.setId(1L);
                Role role = new Role();
                role.setNome(RoleEnum.PROFESSOR);
                usuario.setRole(role);

                when(turmaRepository.existsByNomeAndUsuario_Id(nome, 1L))
                                .thenReturn(true);

                TurmaComNomesIguaisException thrown = Assertions.assertThrows(
                                TurmaComNomesIguaisException.class, () -> {
                                        turmaService.criar(dto, usuario);
                                });

                Assertions.assertEquals("Já existe uma turma com nome: " + nome, thrown.getMessage());

                verify(turmaRepository, never()).save(any());
        }

        @Test
        void naoDeveAtualizarTurmaInexistente() {

                UpdateTurmaRequestDTO dto = new UpdateTurmaRequestDTO("3°Ano A");

                Role role = new Role();
                role.setNome(RoleEnum.PROFESSOR);

                Usuario usuario = new Usuario();
                usuario.setId(1L);
                usuario.setRole(role);

                long id = 10L;

                when(turmaRepository.findByIdAndUsuario_Id(id, 1L))
                                .thenReturn(Optional.empty());

                NotFoundException thrown = Assertions.assertThrows(NotFoundException.class,
                                () -> turmaService.atualizar(
                                                id,
                                                dto,
                                                usuario));

                Assertions.assertEquals("Turma não encontrada para id: " + id, thrown.getMessage());

                verify(turmaRepository, never()).save(any());
        }

        @Test
        void naoDeveAtualizarTurmaSeUsuarioNaoForProfessor() {

                UpdateTurmaRequestDTO dto = new UpdateTurmaRequestDTO("3°Ano A");

                Role role = new Role();
                role.setNome(RoleEnum.ALUNO);

                long id = 1L;

                Usuario usuario = new Usuario();
                usuario.setId(id);
                usuario.setRole(role);

                AccessDeniedException thrown = Assertions.assertThrows(
                                AccessDeniedException.class,
                                () -> turmaService.atualizar(id, dto, usuario));

                Assertions.assertEquals("Acesso negado", thrown.getMessage());

                verify(turmaRepository, never()).save(any());
        }

        @Test
        void deveDeletarTurmaComSucesso() {

                Role role = new Role();
                role.setNome(RoleEnum.PROFESSOR);

                Usuario usuario = new Usuario();
                usuario.setId(1L);
                usuario.setRole(role);

                long id = 10L;

                Turma turma = new Turma();
                turma.setId(id);
                turma.setUsuario(usuario);
                turma.setMatriculas(List.of());

                when(turmaRepository.findByIdAndUsuario_Id(id, 1L))
                                .thenReturn(Optional.of(turma));

                turmaService.delete(id, usuario);

                verify(turmaRepository, times(1)).findByIdAndUsuario_Id(id, 1L);

                verify(turmaRepository, times(1)).delete(turma);
        }

        @Test
        void naoDeveDeletarTurmaInexistente() {

                Role role = new Role();
                role.setNome(RoleEnum.PROFESSOR);

                Usuario usuario = new Usuario();
                usuario.setId(1L);
                usuario.setRole(role);

                long id = 10L;

                when(turmaRepository.findByIdAndUsuario_Id(id, 1L))
                                .thenReturn(Optional.empty());

                NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> turmaService.delete(
                                id, usuario));

                Assertions.assertEquals("Turma não encontrada para id: " + id, thrown.getMessage());

                verify(turmaRepository, never()).delete(any());
        }

        @Test
        void naoDeveDeletarTurmaSeUsuarioNaoForProfessor() {

                Role role = new Role();
                role.setNome(RoleEnum.ALUNO);

                long id = 1L;

                Usuario usuario = new Usuario();
                usuario.setId(id);
                usuario.setRole(role);

                AccessDeniedException thrown = Assertions.assertThrows(
                                AccessDeniedException.class,
                                () -> turmaService.delete(id, usuario));

                Assertions.assertEquals("Acesso negado", thrown.getMessage());

                verify(turmaRepository, never()).save(any());
        }
}
