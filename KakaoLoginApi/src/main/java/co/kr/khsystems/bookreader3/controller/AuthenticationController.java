package co.kr.khsystems.bookreader3.controller;

import co.kr.khsystems.bookreader3.entity.KakaoUserInfo;
import co.kr.khsystems.bookreader3.request.AuthenticationRequest;
import co.kr.khsystems.bookreader3.request.RegisterRequest;
import co.kr.khsystems.bookreader3.response.AuthenticationResponse;
import co.kr.khsystems.bookreader3.service.AuthenticationService;
import co.kr.khsystems.bookreader3.service.KakaoAPI;
import co.kr.khsystems.bookreader3.validator.AuthenticationRequestValidator;
import co.kr.khsystems.bookreader3.validator.RegisterRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.path.uri}")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final AuthenticationRequestValidator authenticationRequestValidator;
    private final RegisterRequestValidator registerRequestValidator;
    private final Logger log = Logger.getLogger(AuthenticationController.class.getName());

    @Autowired
    private KakaoAPI kakaoAPI;

    @InitBinder("authenticationRequest")
    protected void initAuthenticationRequestBinder(WebDataBinder binder) {
        binder.addValidators(authenticationRequestValidator);
    }

    @InitBinder("registerRequest")
    protected void initRegisterRequestBinder(WebDataBinder binder){
        binder.addValidators(registerRequestValidator);
    }

    @GetMapping("/test")
    public String Hello(){
        return "Deployment Test";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new AuthenticationResponse(errors));
        }
        try{
            return ResponseEntity.ok(service.register(request));
        }catch(IllegalArgumentException e){
            List<String> errors = new ArrayList<>();
            errors.add(e.getMessage());
            return ResponseEntity.badRequest().body(new AuthenticationResponse(errors));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody AuthenticationRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new AuthenticationResponse(errors));
        }
        try {
            return ResponseEntity.ok(service.authenticate(request));
        } catch (IllegalArgumentException e) {
            List<String> errors = new ArrayList<>();
            errors.add(e.getMessage());
            return ResponseEntity.badRequest().body(new AuthenticationResponse(errors));
        }
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

