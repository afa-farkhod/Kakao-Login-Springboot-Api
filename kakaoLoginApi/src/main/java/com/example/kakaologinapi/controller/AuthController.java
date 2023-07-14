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

    @Autowired
    private KakaoApi kakaoAPI;

    @GetMapping("/test")
    public String Hello(){
        return "Test Working";
    }

    @GetMapping(path="/kakaoLogin")
    public ResponseEntity<String> login(@RequestParam("code") String code, Model model) {
        log.info("Authorization Code is " + code);
        String accessToken = kakaoAPI.getAccessToken(code);
        KakaoUserInfo kakaoUserInfo = null;
        if (accessToken != null && !accessToken.isEmpty()) {
            kakaoUserInfo = kakaoAPI.getUserInfo(accessToken);
            if (kakaoUserInfo != null) {
                return ResponseEntity.ok("Login successful! " + "(Authorization code " + code + " )");
            }
        }
        model.addAttribute("kakaoUserInfo", kakaoUserInfo);
        return ResponseEntity.badRequest().body("Login failed!");
    }
}
