package com.alan.jwtdemo.controller;

import com.alan.jwtdemo.model.JwtRequestObject;
import com.alan.jwtdemo.model.JwtResponse;
import com.alan.jwtdemo.service.CustomUserDetailsService;
import com.alan.jwtdemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class JwtController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private CustomUserDetailsService customUserDetailService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/generateToken")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequestObject jwtRequest){
        
        //autenticamos el usuario
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword());
        authenticationManager.authenticate(upat);
        
        UserDetails userDetails = customUserDetailService.loadUserByUsername(jwtRequest.getUserName());
        String jwtToken = jwtUtil.generateToken(userDetails);
        
        JwtResponse jwtResponse = new JwtResponse(jwtToken);
        return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.OK);
        
    }
}
