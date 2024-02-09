package edu.ucan.sdp2.bancocore.configuration.security;


import edu.ucan.sdp2.bancocore.repositories.UtilizadorRepository;
import edu.ucan.sdp2.bancocore.utils.SessaoRequisicao;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class FiltroAutenticacao extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final UtilizadorRepository repo;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var jwtToken = jwtUtil.extrairTokenDaRequisicao(request);
        if (jwtToken != null) {
            try {
                var username = jwtUtil.getNomeUtilizador(jwtToken);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    var user = repo.findByUsername(username);
                    SessaoRequisicao.utilizador = user;

                    // TODO:
                    if (jwtUtil.validarToken(SessaoRequisicao.utilizador, jwtToken)) {
                        var authToken = new UsernamePasswordAuthenticationToken(
                                user.getUsername(),
                                null,
                                user.getAuthorities()
                        );
                        authToken.setDetails(new WebAuthenticationDetailsSource()
                                .buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }catch (Exception exception) {
                log.error(exception.getMessage());
                // TODO: Include errors to Log Files
            }
        }

        filterChain.doFilter(request, response);
    }

}
