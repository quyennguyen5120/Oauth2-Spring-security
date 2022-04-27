package com.example.oauth2.Controller;

import com.example.oauth2.Entity.Role;
import com.example.oauth2.Entity.User;
import com.example.oauth2.Repository.RoleRepository;
import com.example.oauth2.Repository.UserRepository;
import com.example.oauth2.Service.DefaultService;
import com.example.oauth2.Service.MailService;
import com.example.oauth2.WebConfiguration.Auth.CustomOauth2User;
import com.example.oauth2.WebConfiguration.Auth.CustomOauth2UserService;
import com.example.oauth2.WebConfiguration.Auth.CustomOidcGoogle;
import com.example.oauth2.WebConfiguration.Auth.MyUserDetail;
import com.example.oauth2.WebConfiguration.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.proc.SecurityContext;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class Controllerrrrrrrrr {
    @Autowired
    CustomOauth2UserService customOauth2UserService;
    @Autowired
    DefaultService defaultService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JwtProvider jwtProvider;

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
        finally {
            if(oAuth2User_Facebook == null && oauthUser_Google== null){
                MyUserDetail u = (MyUserDetail) authentication.getPrincipal();
                name = u.getUsername();
            }
            name = "Xin chào " + name;
            model.addAttribute("name", name);
            return "index";
        }
//        Long faceBookId = Long.parseLong(oAuth2User.getAttribute("id").toString());
//        String nameFacebook = oAuth2User.getName();
//        String accessToken = oAuth2User.getAccess_token();
    }

    @GetMapping("/add")
    public String addUser(){
        Role r = roleRepository.findById(1L).get();
        Set<Role> roles = new HashSet<>();
        roles.add(r);
        userRepository.save(new User("quyen",bCryptPasswordEncoder.encode("quyen"), true, roles));
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/403")
    public String zxczxcz403(){
        return "403";
    }

    @GetMapping("/send-mail")
    public String forgotPassword(){
        defaultService.sendMail("quyenproxxxxx@gmail.com", "GAFFAFAFAFAFAF", "Chúc mừng bạn đã nhận được 3 tỉ đồngzzzzz!");
        return "";
    }

    @GetMapping("/ForgetPasswordCTL")
    public String forgetPasswordCTL(Model model){
        model.addAttribute("user", new User());
        return "forgetPassword";
    }

    @PostMapping("/forgot-passwordzz")
    public String ForgetPasswordCTL(User user, Model model){
        User user1 = userRepository.findByUsername(user.getUsername());
        if(user1 != null){
            String tokenResetMail = jwtProvider.generateToken_email(user1.getUsername());
            user1.setTokenEmail(tokenResetMail);
            userRepository.save(user1);
            model.addAttribute("token_mail",tokenResetMail);
            defaultService.sendMailResetPassWord("quyenproxxxxx@gmail.com", "Reset password", null, tokenResetMail);
            return "confirmEmail";
        }
        return null;
    }
    @GetMapping("/reset-email/{token_reset}")
    public String resetPassword(@PathVariable("token_reset") String tokenEmail, Model model){
        User u = userRepository.findByTokenEmail(tokenEmail);
        if(u != null){
            model.addAttribute("user", new User());
            model.addAttribute("token_reset", tokenEmail);
            return "formResetPassword";
        }
        return null;
    }
    @PostMapping("/reset-email/ressttttt/{token_reset}")
    public String resset(User user, @PathVariable("token_reset") String token_reset){
        User u = userRepository.findByTokenEmail(token_reset);
        String password = bCryptPasswordEncoder.encode(user.getPassword());
        u.setPassword(password);
        userRepository.save(u);
        return "login";
    }

    @PostMapping("/spam")
    public void spamMail(User user){
        String nameSpam = user.getUsername();
        defaultService.spamMail(nameSpam, "Spam choi choi " , null, "Spam neeeeeeeeeee");
    }

    @GetMapping("/spammmm")
    public String spamView(Model model){
        model.addAttribute("user", new User());
        return "spamEmail";
    }



}
