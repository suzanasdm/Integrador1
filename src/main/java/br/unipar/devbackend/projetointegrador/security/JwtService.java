package br.unipar.devbackend.projetointegrador.security;

import br.unipar.devbackend.projetointegrador.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    /*
     * Chave secreta usada para assinar o token.
     *
     * IMPORTANTE:
     * Essa chave precisa ter tamanho suficiente para HS256.
     * Depois podemos mover isso para o application.properties.
     */
    private static final String SECRET_KEY =
            "CYBERSOFT_SECRET_KEY_SUPER_SEGURA_PARA_JWT_2026_MINIMO_32_CARACTERES";

    /*
     * Tempo de expiração do token.
     * Aqui está configurado para 24 horas.
     */
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    public String gerarToken(Usuario usuario) {
        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("id", usuario.getId())
                .claim("nome", usuario.getNome())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getChaveAssinatura())
                .compact();
    }

    public String extrairEmail(String token) {
        return extrairClaims(token).getSubject();
    }

    public boolean tokenValido(String token, Usuario usuario) {
        String email = extrairEmail(token);

        return email.equals(usuario.getEmail()) && !tokenExpirado(token);
    }

    private boolean tokenExpirado(String token) {
        Date dataExpiracao = extrairClaims(token).getExpiration();

        return dataExpiracao.before(new Date());
    }

    private Claims extrairClaims(String token) {
        return Jwts.parser()
                .verifyWith(getChaveAssinatura())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getChaveAssinatura() {
        byte[] keyBytes = SECRET_KEY.getBytes();

        return Keys.hmacShaKeyFor(keyBytes);
    }
}