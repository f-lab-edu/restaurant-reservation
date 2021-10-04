package com.restaurantreservation.service;

import com.restaurantreservation.domain.user.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
public class UserAuthService {

    //AUTH_SECRET_KEY - 나중에 노출되어서는 안되는 키 , 지금은 테스트 용으로 적고 나중에 파일을 나눠서 관리하고 gitIgnore 에 추가하자
    private static final String AUTH_SECRET_KEY = "test123";
    private static final int EXPIRE_PERIOD_MINUTE = 30;
    private static final String ISSUER = "KHJ";

    public String createToken(UserEntity userEntity) {
        Date nowDate = new Date();

        // HS256 방식으로 암호화 방식 설정
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 타입 지정
                .setIssuer(ISSUER) //
                .setIssuedAt(nowDate) // 토큰 발급 시간
                .setExpiration(new Date(nowDate.getTime() + Duration.ofMinutes(EXPIRE_PERIOD_MINUTE).toMillis())) // 발급시간 + 만료 기간
                .claim("id", userEntity.getId()) // 비공개 클레임 추가
                .claim("email", userEntity.getEmail())
                .signWith(SignatureAlgorithm.HS256, AUTH_SECRET_KEY) // auth_secret_key 추가
                .compact();
    }

    /**
     * UnsupportedJwtException : 예상하는 형식과 다른 형식, 구성의 JWT 일 때
     * MalformedJwtException : JWT 가 올바르게 구성되지 않았을 때
     * ExpiredJwtException : JWT 를 생성할 때 지정한 유효기간이 초과되었을 때
     * SignatureException : JWT 의 기존 서명을 확인하지 못했을 때
     * IllegalArgumentException
     * 의 예외가 발생할 수 있고 이거에 대한 처리가 추가로 필요하다.
     */

    /**
     * 이 경우 무사히 Return 되었다면 유효한 형식의 token 인것이 확인되므로
     * 그 다음 작업을 진행하면 된다.
     *
     * 아니라면 Access token 이냐 refresh token 이냐에 따라 또 많이 다르므로
     * 처리를 각각 해주어야 한다.
     * + refresh token 은 어떤 방식으로 처리할 것인지에 대해서도 고민 필요
     */
    public Claims checkJWTToken(String authorizationHeader) {
        validationAuthorizationHeader(authorizationHeader); // header 가 Bearer 로 시작하는지 검사
        String token = extractToken(authorizationHeader); // Bearer 제외한 나머지 단어만 반환

        return Jwts.parser()
                .setSigningKey(AUTH_SECRET_KEY) // 우리가 들고있는 시크릿 키가 맞는지 확인
                .parseClaimsJws(token) // 해석할 토큰을 String 형태로 반환한다.
                .getBody();
    }

    private void validationAuthorizationHeader(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new IllegalArgumentException();
        }
    }

    private String extractToken(String authorizationHeader) {
        return authorizationHeader.substring("Bearer ".length());
    }
}
