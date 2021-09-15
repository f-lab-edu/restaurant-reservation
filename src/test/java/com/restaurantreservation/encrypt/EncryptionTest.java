package com.restaurantreservation.encrypt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 테스트 하기 위해 하는것이고 실무에서는 노출되도 되는지 고민을 해 보자.
 */
class EncryptionTest {

    @Test
    @DisplayName("암호화 테스트")
    void encryptTest() {
        Encryption encryption = new Encryption();

        String rawPassword = "1234";
        String salt = encryption.createSALT();

        String encryptedPassword = encryption.encrypt(rawPassword, salt);

        assertThat(rawPassword).isNotEqualTo(encryptedPassword);
    }

    @Test
    @DisplayName("암호화 일치 테스트(비밀번호 확인용)")
    void encryptCheckTest() {
        Encryption encryption = new Encryption();

        String salt = "aa7ab386ab7b4950";
        String encryptedPassword = "7f39a7e80f1aef36e19e110210b1af725105dfef851263fe72db33cf53711745";

        String rawPassword = "1234";

        String encryptedPWD = encryption.encrypt(rawPassword, salt);
        assertThat(encryptedPWD).isEqualTo(encryptedPassword);
    }
}