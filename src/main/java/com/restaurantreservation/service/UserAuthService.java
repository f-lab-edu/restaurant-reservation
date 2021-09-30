package com.restaurantreservation.service;

import com.restaurantreservation.domain.user.UserEntity;
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

    public void validToken(String token){

    }
}
