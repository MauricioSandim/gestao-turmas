package ufla.projeto_es.gestao_turmas.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ufla.projeto_es.gestao_turmas.configuration.security.JWTConfig;
import ufla.projeto_es.gestao_turmas.exception.BusinessException;
import ufla.projeto_es.gestao_turmas.exception.ValidationConflictException;
import ufla.projeto_es.gestao_turmas.mapper.UsuarioMapper;
import ufla.projeto_es.gestao_turmas.model.roles.Role;
import ufla.projeto_es.gestao_turmas.model.type.RoleEnum;
import ufla.projeto_es.gestao_turmas.model.usuario.Usuario;
import ufla.projeto_es.gestao_turmas.model.usuario.request.LoginRequestDTO;
import ufla.projeto_es.gestao_turmas.model.usuario.request.UsuarioRequestDTO;
import ufla.projeto_es.gestao_turmas.model.usuario.response.LoginResponseDTO;
import ufla.projeto_es.gestao_turmas.model.usuario.response.UsuarioResponseDTO;
import ufla.projeto_es.gestao_turmas.repository.RoleRepository;
import ufla.projeto_es.gestao_turmas.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTConfig tokenService;
    private final UsuarioMapper usuarioMapper;

//    @Value("${api.security.token.recovery-secret}")
//    private String recoverySecret;

    @Value("${api.security.token.recovery-expiration-minutes}")
    private long expirationMinutes;

//    @Value("${api.frontend.url}")
//    private String frontendUrl;

    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(requestDTO.email(), requestDTO.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = this.tokenService.generateToken((Usuario) auth.getPrincipal());
        return new LoginResponseDTO(token);
    }

    @Transactional
    public UsuarioResponseDTO register(UsuarioRequestDTO requestDTO) {

        if (this.usuarioRepository.existsByEmail(requestDTO.email())) {
            throw new ValidationConflictException("E-mail já cadastrado.");
        }

        Usuario usuario = this.usuarioMapper.toEntity(requestDTO);

        String roleSolicitada = requestDTO.role().trim().toUpperCase();
        RoleEnum roleEnum;

        try {
            roleEnum = RoleEnum.valueOf(roleSolicitada);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Role inválida: " + roleSolicitada);
        }

        if (roleEnum == RoleEnum.ADMIN) {
            throw new BusinessException("Não é permitido registrar um usuário com a role: " + roleSolicitada);
        }

        Role role = this.roleRepository.findByNome(roleEnum).orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno: Role " + roleEnum.name() + " não encontrada."));
        usuario.setRole(role);

        String encryptedPassword = this.passwordEncoder.encode(requestDTO.senha());
        usuario.setSenha(encryptedPassword);

        this.usuarioRepository.save(usuario);

        return this.usuarioMapper.toResponse(usuario);
    }
}
