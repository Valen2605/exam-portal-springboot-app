package com.valentina.examportalspringbootapp.controller;


import com.valentina.examportalspringbootapp.model.AdminEntity;
import com.valentina.examportalspringbootapp.model.UserType;
import com.valentina.examportalspringbootapp.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AuthController {

    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtGenerator jwtGenerator;

    @PostMapping("api/v1/adminRegister")
    public ResponseEntity<String> adminRegister(@RequestBody AdminAuthDto adminAuthDto){
        if(adminRepo.existsByUsername(adminAuthDto.getUsername())){
            return new ResponseEntity<String>("Username is taken!!", HttpStatus.BAD_REQUEST);
        }
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setUsername(adminAuthDto.getUsername());
        adminEntity.setPassword(passwordEncoder.encode(adminAuthDto.getPassword()));

        adminRepo.save(adminEntity);

        return new ResponseEntity<String>("User register successfull!!", HttpStatus.CREATED);
    }

    @PostMapping("api/v1/adminLogin")
    public ResponseEntity<AdminLoginResponseDto> login(@RequestBody AdminAuthDto adminAuthDto){
        System.out.println("adminLogin");

        customUserDetailsService.setUserType(UserType.ADMIN);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(adminAuthDto.getUsername(), adminAuthDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication,UserType.ADMIN.toString());
        AdminLoginResponseDto responseDto = new AdminLoginResponseDto();
        responseDto.setSuccess(true);
        responseDto.setMessage("login successful !!");
        responseDto.setToken(token);
        AdminEntity admin = adminRepo.findByUsername(adminAuthDto.getUsername()).orElseThrow();
        responseDto.setAdmin(admin.getUsername(), admin.getId());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
