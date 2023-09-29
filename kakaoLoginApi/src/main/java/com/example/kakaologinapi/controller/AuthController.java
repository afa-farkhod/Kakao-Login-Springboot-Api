package com.example.kakaologinapi.controller;

import com.example.kakaologinapi.entity.KakaoUserInfo;
import com.example.kakaologinapi.service.KakaoApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("${api.path.uri}")
@RequiredArgsConstructor
public class AuthController {

    private final Logger log = Logger.getLogger(AuthController.class.getName());
    private final static String LOGIN_MSG = "Login successful!";
    private final static String AUTH_CODE_MSG = "(Authorization code ";
    private final static String TEST_MSG = "Test successful!";
    private final static String LOGIN_FAIL_MSG = "Login failed!";
    private final static String AUTH_CODE_MSG2 = "Authorization Code is ";

    @Autowired
    private KakaoApi kakaoAPI;

    @GetMapping("/test")
    public String Hello(){
        return TEST_MSG;
    }

    @GetMapping(path="/kakaoLogin")
    public ResponseEntity<String> login(@RequestParam("code") String code, Model model) {
        log.info(AUTH_CODE_MSG2 + code);
        String accessToken = kakaoAPI.getAccessToken(code);
        KakaoUserInfo kakaoUserInfo = null;
        if (accessToken != null && !accessToken.isEmpty()) {
            kakaoUserInfo = kakaoAPI.getUserInfo(accessToken);
            if (kakaoUserInfo != null) {
                return ResponseEntity.ok(LOGIN_MSG + AUTH_CODE_MSG + code + " )");
            }
        }
        model.addAttribute("kakaoUserInfo", kakaoUserInfo);
        return ResponseEntity.badRequest().body(LOGIN_FAIL_MSG);
    }
}
