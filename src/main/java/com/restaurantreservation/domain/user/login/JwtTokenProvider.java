package com.restaurantreservation.domain.user.login;

import com.restaurantreservation.error.exception.user.UserException;
import com.restaurantreservation.error.message.user.JWTTokenExceptionMessage;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.Duration;
import java.util.*;

@Slf4j
@Component
public class JwtTokenProvider {
    /**
     * 일단 테스트 용으로 secretKey , refreshKey 를 여기에 구현,
     * 나중에 gitignore된 properties 에 설정할 필요가 있음.
     */
    private final static String SECRET_KEY = "secretKey";
    private final static String REFRESH_KEY = "refreshKey";
    private final static int EXPIRE_DATE = 30;

    public static String createJwtToken(Long userId, String email, JwtType type) {

        String key = type == JwtType.ACCESS_TOKEN ? SECRET_KEY : REFRESH_KEY;

        return Jwts.builder()
                .setSubject(email)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .claim("userId", userId)
                .setExpiration(createExpireDate())
                .signWith(SignatureAlgorithm.HS256, createSigningKey(key))
                .compact();
    }

    /**
     * 유효한 토큰인지 확인하는 로직
     * 만약 ExpiredJwtException 이 터질 경우
     * 프론트 쪽에서 RefreshToken 으로 다시 AccessToken 을 만들어 달라고 요청을 해야 한다.
     */
    public static void isValidToken(String token, JwtType jwtType) {

        try {
            Claims claimsToken = getClaimsToken(token, jwtType);
            log.info("Access expireTime: {}", claimsToken.getExpiration());
            log.info("Access userId: {}", claimsToken.get("userId"));
        } catch (ExpiredJwtException e) {
            throw new UserException(JWTTokenExceptionMessage.EXPIRED);
        } catch (MalformedJwtException e) {
            throw new UserException(JWTTokenExceptionMessage.MALFORMED);
        } catch (ClaimJwtException e) {
            throw new UserException(JWTTokenExceptionMessage.ClAIM_FAIL);
        } catch (JwtException e) {
            throw new UserException(JWTTokenExceptionMessage.FAIL);
        }
    }

    public static TokenValue getUserIdAndEmail(String token, JwtType jwtType) {
        Claims claimsToken = getClaimsToken(token, jwtType);
        String email = claimsToken.getSubject();
        Long userId = (Long) claimsToken.get("userId");
        return TokenValue.create(userId, email);
    }

    //Date 객체는 쓰지 않는게 좋다.
    //LocalDate or LocalDateTime 를 쓰는것이 좋다.
    //JWT 라이브러리에서 Date 만 제공할때는 .. 흠....
    private static Date createExpireDate() {
        Date now = new Date();
        return new Date(now.getTime() + Duration.ofMinutes(EXPIRE_DATE).toMillis());
    }

    private static Key createSigningKey(String key) {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private static Claims getClaimsToken(String token, JwtType type) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(String.valueOf(type)))
                .parseClaimsJws(token)
                .getBody();
    }

}


