package ru.poltoranin.datafaker.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.poltoranin.datafaker.dto.AuthRequestDTO;
import ru.poltoranin.datafaker.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api")
public class AuthenticationController {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public String createToken(@RequestBody AuthRequestDTO authRequest) {
        var auth = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                authRequest.getPassword());

        authenticationManager.authenticate(auth);

        var token = jwtUtils.generateToken(authRequest.getUsername());
        return token;
    }

}
