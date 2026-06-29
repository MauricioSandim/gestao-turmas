package ufla.projeto_es.gestao_turmas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufla.projeto_es.gestao_turmas.model.type.RoleEnum;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

        Optional<Usuario> findByEmail(String email);

        boolean existsByEmail(String email);

        List<Usuario> findAllByRoleNome(RoleEnum nome);

        Optional<Usuario> findByIdAndRoleNome(Long id, RoleEnum nome);
}
