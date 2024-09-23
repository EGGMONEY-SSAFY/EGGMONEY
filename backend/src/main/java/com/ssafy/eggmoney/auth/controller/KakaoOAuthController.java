package com.ssafy.eggmoney.auth.controller;
//
//import com.ssafy.eggmoney.auth.dto.response.TokenResponse;
//import com.ssafy.eggmoney.auth.service.KakaoOAuth2UserService;
//import jakarta.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.view.RedirectView;


//@CrossOrigin(origins = "http://localhost:5173")
//@RequestMapping("/kakao")
//@Controller
public class KakaoOAuthController {

//    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
//    private String kakaoClientId;
//
//    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
//    private String kakaoRedirectUri;
//
//    private final KakaoOAuth2UserService kakaoOAuth2UserService;
//
//    public KakaoOAuthController(KakaoOAuth2UserService kakaoOAuth2UserService){
//        this.kakaoOAuth2UserService = kakaoOAuth2UserService;
//    }
//
//    @GetMapping("/login")
//    public RedirectView redirectToKakaoLogin(){
//        String kakaoLoginUrl = "https://kauth.kakao.com/oauth/authorize"+
//                "?client_id="+kakaoClientId+
//                "&redirect_uri="+kakaoRedirectUri+
//                "&response_type=code";
//        return new RedirectView(kakaoLoginUrl);
//    }
//
//    @GetMapping("/callback")
//    public RedirectView handleKakaoCallback(@RequestParam("code") String code, HttpSession session){
//        TokenResponse tokenResponse = kakaoOAuth2UserService.getAccessToken(code);
//        String accessToken = tokenResponse.getAccessToken();
//        String refreshToken = tokenResponse.getRefreshToken();
//        System.out.println("callback with :" + accessToken+refreshToken);
//        session.setAttribute("accessToken", accessToken);
//        session.setAttribute("refreshToken", refreshToken);
//
//        return new RedirectView("http://localhost:5173/selectRole");
//    }
//
//    @GetMapping("/refresh-token")
//    public String refreshAccessToken(HttpSession session){
//        String refreshToken = (String) session.getAttribute("refreshToken");
//        if(refreshToken != null){
//            TokenResponse newToken = kakaoOAuth2UserService.refreshAccessToken(refreshToken);
//            session.setAttribute("accessToken", newToken.getAccessToken());
//        }
//        return "redirect:";
//    }
//
//    @GetMapping("/logout")
//    public String logout(HttpSession session){
//        String accessToken = (String) session.getAttribute("accessToken");
//        if(accessToken != null){
//            kakaoOAuth2UserService.logout(accessToken);
//            session.invalidate();
//        }
//        return "redirect:/main";
//    }

}
