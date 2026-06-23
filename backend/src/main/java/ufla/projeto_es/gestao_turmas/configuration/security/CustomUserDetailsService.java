package ufla.projeto_es.gestao_turmas.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ufla.projeto_es.gestao_turmas.exception.NotFoundException;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.repository.UsuarioRepository;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = this.usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado para " + email));
        return usuario;
    }
}