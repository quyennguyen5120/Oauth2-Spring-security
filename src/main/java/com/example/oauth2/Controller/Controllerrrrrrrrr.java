package com.example.oauth2.Controller;

import com.example.oauth2.WebConfiguration.Auth.CustomOauth2User;
import com.example.oauth2.WebConfiguration.Auth.CustomOauth2UserService;
import com.example.oauth2.WebConfiguration.Auth.CustomOidcGoogle;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.proc.SecurityContext;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Controller
public class Controllerrrrrrrrr {
    @Autowired
    CustomOauth2UserService customOauth2UserService;

    @GetMapping("/list")
    @Secured("ROLE_USER")
    public String getList(Model model, HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOauth2User oAuth2User_Facebook = null;
        DefaultOidcUser oauthUser_Google = null;
        String name = "";
        try{
            oAuth2User_Facebook = (CustomOauth2User) authentication.getPrincipal();
            name = oAuth2User_Facebook.getName();
        }
        catch (Exception e){
            oauthUser_Google= (DefaultOidcUser) authentication.getPrincipal();
            name = oauthUser_Google.getFullName();
        }
        name = "Xin chào " + name;
        model.addAttribute("name", name);

//        Long faceBookId = Long.parseLong(oAuth2User.getAttribute("id").toString());
//        String nameFacebook = oAuth2User.getName();
//        String accessToken = oAuth2User.getAccess_token();
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/403")
    public String zxczxcz403(){
        return "403";
    }
}
