package com.springweb.web.jwt;


import com.springweb.web.aop.annotation.Trace;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";

    private final String secret;//시크릿 코드
    private final long tokenValidityInMilliseconds;//토큰 만료 기간

    private Key key;//토큰을 암호화하고, 디코딩하는 키


    public TokenProvider(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }



    //InitializingBean을 상속 -> 빈이 생성 되고 secret을 주입
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);//시크릿 키를 BASE64 방식으로 디코딩
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    //권한정보를 이용하여 토큰 생성
    @Trace
    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(",")); //,을 추가해서 권한들을 나열

        //== 토큰 만료시간 ==//
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);//yml 파일에서 설정한 토큰 만료시간


        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)//HS512로 암호화
                .setExpiration(validity)
                .compact();
    }



    //토큰 정보를 이용하여 권한 생성
    @Trace
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts        //토큰에 있는 권한을 디코딩?
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();


        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))//위에서 , 를 사용해서 나눴음
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }






    private final String TOKEN_EXCEPTION = "tokenException";
    //== 토큰의 유효성 검사 ==//
    @Trace
    public boolean validateToken(String token, HttpServletRequest httpServletRequest) throws Exception {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }
        catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            httpServletRequest.setAttribute(TOKEN_EXCEPTION,"잘못된 JWT 서명입니다.");
        }
        catch (ExpiredJwtException e) {
            httpServletRequest.setAttribute(TOKEN_EXCEPTION,"만료된 JWT 토큰입니다.다시 로그인해주세요");
        }
        catch (UnsupportedJwtException e) {
            httpServletRequest.setAttribute(TOKEN_EXCEPTION,"지원되지 않는 JWT 토큰입니다.");
        }
        catch (IllegalArgumentException e) {

            httpServletRequest.setAttribute(TOKEN_EXCEPTION,"JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
