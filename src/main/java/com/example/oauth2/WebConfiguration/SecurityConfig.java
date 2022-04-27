package com.example.oauth2.WebConfiguration;

import com.example.oauth2.Entity.Provider;
import com.example.oauth2.Service.ServiceImpl.UserDetailsServiceImpl;
import com.example.oauth2.Service.UserService;
import com.example.oauth2.WebConfiguration.Auth.CustomOauth2User;
import com.example.oauth2.WebConfiguration.Auth.CustomOauth2UserService;
import com.example.oauth2.WebConfiguration.Auth.CustomOidcGoogle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomOauth2UserService customOauth2UserService;

    @Autowired
    UserService userService;

    @Bean
    public UserDetailsServiceImpl userDetailsServiceImpl(){
        return new UserDetailsServiceImpl() ;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder());
//        provider.setUserDetailsService(userDetailsServiceImpl());
//        return provider;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/login", "/add", "/ForgetPasswordCTL","/forgot-passwordzz", "/reset-email/**", "/spammmm", "/spam").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/list")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(customOauth2UserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        CustomOauth2User oAuth2User_Facebook = null;
                        DefaultOidcUser oauthUser_Google = null;
                        String email = "";
                        String method = "";
                        try{
                            oAuth2User_Facebook = (CustomOauth2User) authentication.getPrincipal();
                            email = oAuth2User_Facebook.getAttribute("email");
                            method =  Provider.FACEBOOK.toString();
                        }
                        catch (Exception e){
                            oauthUser_Google= (DefaultOidcUser) authentication.getPrincipal();
                            email = oauthUser_Google.getAttribute("email");
                            method = Provider.GOOGLE.toString();
                        }
                        userService.checkExistUserOauth(email, method);
                        response.sendRedirect("/list");
                    }
                })
                .and()
                .logout()
                .logoutSuccessUrl("/login").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
    }
}
