package com.restaurantreservation.encrypt;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Component
public class Encryption {
    /**
     * SHA-256 으로 단방향 암호화를 하는데
     * 이 때 8글자 RANDOM 한 SALT 를 추가하여 소팅(salting)
     */
    private static final int SALT_SIZE = 8;

    // 비밀번호 해싱
    // 지금은 패스워드를 String 으로 넘기지만 실무 등에서는 처음 넘길때도
    // byte 변환 + 추가 작업 을 이용하여서 넘겨야 된다
    public String encrypt(String password, String salt) {
        MessageDigest md;    // SHA-256 해시함수를 사용
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("암호화 알고리즘이 존재하지 않습니다.", e);
        }
        //key stretching
        //키 스트레칭 추가 일단 11번만 하자
        for (int i = 0; i < 11; i++) {
            String temp = password + salt;
            md.update(temp.getBytes());
        }

        return byteToString(md.digest());
    }

    // SALT 값 생성
    public String createSALT(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] temp = new byte[SALT_SIZE];
        secureRandom.nextBytes(temp);

        return byteToString(temp);
    }

    // 바이트 값을 16진수로 변경해준다
    private String byteToString(byte[] temp) {
        StringBuilder sb = new StringBuilder();
        for (byte a : temp) {
            sb.append(String.format("%02x", a));
        }
        return sb.toString();
    }
}
