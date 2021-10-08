package com.restaurantreservation.domain.user.login;

import com.restaurantreservation.error.exception.user.UserException;
import com.restaurantreservation.error.message.user.JWTTokenExceptionMessage;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenProvider {
    /**
     * 일단 테스트 용으로 secretKey , refreshKey 를 여기에 구현,
     * 나중에 gitignore된 properties 에 설정할 필요가 있음.
     */
    private final static String SECRET_KEY = "secretKey";
    private final static String REFRESH_KEY = "refreshKey";
    private final static int EXPIRE_DATE = 1000 * 60 * 30;

    public static String createJwtToken(Long userId, String memberId, JwtType type) {

        String key = type == JwtType.ACCESS_TOKEN ? SECRET_KEY : REFRESH_KEY;

        return Jwts.builder()
                .setSubject(memberId)
                .setHeader(createHeader(type))
                .setClaims(createClaims(userId))
                .setExpiration(createExpireDate())
                .signWith(SignatureAlgorithm.HS256, createSigningKey(key))
                .compact();
    }

    public static void isValidToken(String token, JwtType jwtType) {

        try {
            Claims claimsFormToken = getClaimsToken(token, jwtType);
            log.info("Access expireTime: {}", claimsFormToken.getExpiration());
            log.info("Access userId: {}", claimsFormToken.get("userId"));
        } catch (ExpiredJwtException e) {
            /**
             * 이 경우에 이 값을 return 하면 프론트에서 refresh token 을 넣을채로 access token 을 발금해달라고 요청해야 한다.
             */
            throw new UserException(JWTTokenExceptionMessage.EXPIRED);
        } catch (MalformedJwtException e) {
            throw new UserException(JWTTokenExceptionMessage.MALFORMED);
        } catch (ClaimJwtException e) {
            throw new UserException(JWTTokenExceptionMessage.ClAIM_FAIL);
        } catch (JwtException e) {
            throw new UserException(JWTTokenExceptionMessage.FAIL);
        } catch (NullPointerException e) {
            throw new UserException(JWTTokenExceptionMessage.NULL);
        }
    }

    private static Date createExpireDate() {
        return new Date(System.currentTimeMillis() + (long) JwtTokenProvider.EXPIRE_DATE);
    }

    private static Map<String, Object> createHeader(JwtType type) {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", type);
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    private static Map<String, Object> createClaims(long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        return claims;
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


