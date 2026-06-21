package ufla.projeto_es.gestao_turmas.model.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ufla.projeto_es.gestao_turmas.model.baseEntity.BaseEntity;
import ufla.projeto_es.gestao_turmas.model.roles.Role;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario extends BaseEntity<Long> implements UserDetails {

    private String email;

    private String nome;

    private String senha;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    public String getRoleName() {
        return role.getNome().name();
    }

    @NullMarked
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.getNome().name()));
    }

    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    @NullMarked
    @Override
    public String getUsername() {
        return email;
    }
}
