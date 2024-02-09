package edu.ucan.sdp2.bancocore.configuration.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JWTUtil {

    @Value("${security.pwd.secret.key}")
    private String chaveSecreta;


    //   Esta função serve para gerar o token pelos parâmetros: UserDetails, HttpServletRequest, time (tempo em minutos)
    public String gerarToken(@NotNull UserDetails user, @NotNull HttpServletRequest request, final int time)
    {
        HashMap<String, Object> claims = new HashMap<String, Object>();
        claims.put("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(time, ChronoUnit.MINUTES)))
                .setIssuer(request.getRequestURL().toString())
                .signWith(this.getChaveAssinada(), SignatureAlgorithm.HS256)
                .compact();
    }

    //   Esta função é um overwrite, serve para  simplificar a função de cima, usando o contexto do springboot para capturar o contexto da requisicao
    public String gerarToken(UserDetails user, final int time)
    {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return gerarToken(user, request, time);
    }

    //   Esta função é um callback que retorna os dados do token

    public <T> T extrairClaim(String token, Function<Claims, T> claimsResolver ){
        final Claims claims = extrairTodosDadosToken(token);
        return claimsResolver.apply(claims);
    }
    //   Esta função serve para extrair o token da requisição efetuada pelo cliente
    public String extrairTokenDaRequisicao(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    //   Esta função serve para extrair o nome de utilizador do token, que é um dos dados (claims) para a criação da mesma
    public String getNomeUtilizador(String token) {
        return extrairClaim(token, Claims::getSubject);
    }


    //   Valida o token a partir do UserDetails.
    public boolean validarToken(UserDetails user, String token){
        final String username = this.getNomeUtilizador(token);
        boolean unexpired = !isTokenExpirado(token);
        return unexpired && Objects.equals(user.getUsername(), username);
    }


    //   Verifica se o token está expirado
    private boolean isTokenExpirado(String token) {
        return extrairDataExpiracao(token).before(Date.from(Instant.now()));
    }


    //    Esta função serve para extrair a data de expiração do token.
    private Date extrairDataExpiracao(String token) {
        return extrairClaim(token, Claims::getExpiration);
    }


//    Esta função serve para extrair todos os dados do token
    public Claims extrairTodosDadosToken(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getChaveAssinada())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //    Esta função serve para assinar (decodificar) os bytes da chave de encriptação usada para gerar o token
    private Key getChaveAssinada() {
        byte[] keyBytes = Decoders.BASE64.decode(chaveSecreta);
    return Keys.hmacShaKeyFor(keyBytes);
    }
}