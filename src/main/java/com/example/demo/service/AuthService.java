package com.example.demo.service;

import com.example.demo.dtos.EmailDTO;
import com.example.demo.dtos.NewPasswordDTO;
import com.example.demo.dtos.RequestTokenDTO;
import com.example.demo.entities.PasswordRecover;
import com.example.demo.entities.User;
import com.example.demo.repository.PasswordRecoverRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.exceptions.ResourceNotFound;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    @Value("${email.password-recover.token.minute}")
    private int tokenMinute;

    @Value("${email.password-recover.uri}")
    private String uri;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordRecoverRepository passwordRecoverRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    public void createRecoverToken(@Valid RequestTokenDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail());
        if (user == null){
            throw  new ResourceNotFound("E-mail not found");
        }

        String token = UUID.randomUUID().toString();

        PasswordRecover passwordRecover = new PasswordRecover();
        passwordRecover.setToken(token);
        passwordRecover.setEmail(dto.getEmail());
        passwordRecover.setExpiration(Instant.now().plusSeconds(tokenMinute * 60));

        passwordRecoverRepository.save(passwordRecover);

        String body = "Acesse o link para definir uma nova senha(valido por: " + tokenMinute + "): \n\n" + uri + token;
        emailService.sendMail( new EmailDTO(user.getEmail(),"Recuperação de Senha", body));
    }

    public void saveNewPassword(@Valid NewPasswordDTO dto) {
        List<PasswordRecover> list = passwordRecoverRepository.searchValidToken(dto.getToken(),Instant.now());
        if (list.isEmpty()){
            throw  new ResourceNotFound("Token not found");
        }

        User user = userRepository.findByEmail(list.getFirst().getEmail());
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
    }
}


