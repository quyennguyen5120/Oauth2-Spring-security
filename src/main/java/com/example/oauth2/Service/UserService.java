package com.example.oauth2.Service;

import com.example.oauth2.Entity.Provider;
import com.example.oauth2.Entity.Role;
import com.example.oauth2.Entity.User;
import com.example.oauth2.Repository.RoleRepository;
import com.example.oauth2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public void checkExistUserOauth(String username, String method){
        User existUser = userRepository.findByUsername(username);
        if(existUser == null){
            User u = new User();

            u.setUsername(username);
            if(method.toUpperCase(Locale.ROOT).equals(Provider.FACEBOOK.toString()))
                u.setProvider(Provider.FACEBOOK);
            else
                u.setProvider(Provider.GOOGLE);
            u.setEnabled(true);
            u = userRepository.save(u);
            u = userRepository.findById(u.getId()).get();
            Role role = roleRepository.findById(1L).get();
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);
            if(u.getRoles() != null && u.getRoles().size() > 0){
                u.getRoles().clear();
            }
            u.setRoles(roleSet);
            u = userRepository.save(u);
        }
    }
}
