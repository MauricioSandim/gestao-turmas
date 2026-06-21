package ufla.projeto_es.gestao_turmas.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ufla.projeto_es.gestao_turmas.exception.NotFoundException;
import ufla.projeto_es.gestao_turmas.model.type.RoleEnum;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.repository.UsuarioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public List<Usuario> findAllByRole(RoleEnum role) {
        return usuarioRepository.findAllByRole_Nome(role);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário não encontrado para id: " + id));
    }



}
