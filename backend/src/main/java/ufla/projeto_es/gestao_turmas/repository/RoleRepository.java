package ufla.projeto_es.gestao_turmas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufla.projeto_es.gestao_turmas.model.roles.Role;
import ufla.projeto_es.gestao_turmas.model.type.RoleEnum;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


    Optional<Role> findByNome(RoleEnum roleName);
}
