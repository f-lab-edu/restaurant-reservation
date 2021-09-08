package com.restaurantreservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurantreservation.domain.user.UserType;
import com.restaurantreservation.domain.user.UserValue;
import com.restaurantreservation.error.exHandler.CommonExceptionHandler;
import com.restaurantreservation.response.message.user.UserExceptionMessage;
import com.restaurantreservation.response.message.user.UserMessage;
import com.restaurantreservation.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private JacksonTester<UserValue> jsonUserValue;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new CommonExceptionHandler())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("회원가입 테스트 - 성공")
    void canUserJoin() throws Exception {
        UserValue getUserValue =
                new UserValue.Builder("test1523User@Naver.com")
                        .password("1q2w3e4r#")
                        .name("아무개")
                        .phoneNumber("010-0000-0000")
                        .userType(UserType.CUSTOMER)
                        .build();

        MockHttpServletResponse getResponse = mvc.perform(
                post("/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserValue.write(getUserValue)
                                .getJson()
                        )).andReturn().getResponse();

        assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(getResponse.getContentAsString()).isEqualTo(
                new ObjectMapper().writeValueAsString(UserMessage.JOIN_SUCCESS)
        );
    }

    @Test
    @DisplayName("회원가입 테스트 - Email 형식 잘못됬을때")
    void cannotUserJoinTestEmailWrong() throws Exception {
        UserValue getUserValue =
                new UserValue.Builder("test1523User")
                        .password("1q2w3e4r#")
                        .name("아무개")
                        .phoneNumber("010-0000-0000")
                        .userType(UserType.CUSTOMER)
                        .build();

        MockHttpServletResponse getResponse = mvc.perform(
                post("/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserValue.write(getUserValue)
                                .getJson()
                        )).andReturn().getResponse();

        assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(getResponse.getContentAsString()).isEqualTo(
                new ObjectMapper().writeValueAsString(UserExceptionMessage.WRONG_EMAIL)
        );
    }

    @Test
    @DisplayName("회원가입 테스트 - UserType 이 없을 때")
    void cannotUserJoinTestUserTypeWrong() throws Exception {
        UserValue getUserValue =
                new UserValue.Builder("test1523User@Naver.com")
                        .password("1q2w3e4r#")
                        .name("아무개")
                        .phoneNumber("010-0000-0000")
                        .build();

        MockHttpServletResponse getResponse = mvc.perform(
                post("/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserValue.write(getUserValue)
                                .getJson()
                        )).andReturn().getResponse();

        assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(getResponse.getContentAsString()).isEqualTo(
                new ObjectMapper().writeValueAsString(UserExceptionMessage.WRONG_USER_TYPE));
    }
}