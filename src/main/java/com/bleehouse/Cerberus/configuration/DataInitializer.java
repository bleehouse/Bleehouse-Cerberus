package com.bleehouse.Cerberus.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bleehouse.Cerberus.domain.entity.User;
import com.bleehouse.Cerberus.repository.UserRepository;

import java.util.List;

@Component
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        // 모든 사용자의 비밀번호가 평문인지 확인하고 암호화
        List<User> users = userRepository.findAll();
        
        for (User user : users) {
            String password = user.getPassword();
            
            // BCrypt로 암호화되지 않은 평문 비밀번호인지 확인
            if (password != null && !password.startsWith("$2a$") && !password.startsWith("$2b$") && !password.startsWith("$2y$")) {
                // 평문 비밀번호를 암호화
                String encodedPassword = passwordEncoder.encode(password);
                user.setPassword(encodedPassword);
                userRepository.save(user);
                
                System.out.println("암호화 완료: 사용자 '" + user.getUsername() + "'의 비밀번호가 암호화되었습니다.");
            }
        }
    }
}