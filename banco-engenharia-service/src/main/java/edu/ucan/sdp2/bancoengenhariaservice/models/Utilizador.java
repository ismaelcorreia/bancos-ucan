package edu.ucan.sdp2.bancoengenhariaservice.models;


import edu.ucan.sdp2.bancoengenhariaservice.enums.UserRole;
import edu.ucan.sdp2.bancoengenhariaservice.models.generics.Pessoa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "utilizadores")
public class Utilizador extends Pessoa implements UserDetails {
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String telefone;
    @Column(nullable = false)
    private String palavraPasse;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.ORDINAL)
    private List<UserRole> roles;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public Utilizador(UUID id){
        this.setId(id);
    }
    public Utilizador(UUID id, String username){
        this.setId(id);
        this.setUsername(username);
    }
    public Utilizador(UUID id, String username, String email){
        this.setId(id);
        this.setUsername(username);
        this.setEmail(email);
    }
    public Utilizador(UUID id, String username, String email, String nomeCompleto){
        this.setId(id);
        this.setUsername(username);
        this.setEmail(email);
        this.setNomeCompleto(nomeCompleto);
    }
    public Utilizador(UUID id, String username, String email, String nomeCompleto, String fotografia){
        this.setId(id);
        this.setUsername(username);
        this.setEmail(email);
        this.setNomeCompleto(nomeCompleto);
        this.setFotografia(fotografia);
    }
    public Utilizador(UUID id, String username, String email, String nomeCompleto, String fotografia, List<UserRole> roles){
        this.setId(id);
        this.setUsername(username);
        this.setEmail(email);
        this.setNomeCompleto(nomeCompleto);
        this.setFotografia(fotografia);
        this.setRoles(roles);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role-> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.palavraPasse;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

