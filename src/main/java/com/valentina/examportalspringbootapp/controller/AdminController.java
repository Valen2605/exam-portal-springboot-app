package com.valentina.examportalspringbootapp.controller;


import com.valentina.examportalspringbootapp.model.AdminEntity;
import com.valentina.examportalspringbootapp.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private AdminRepo adminRepo;

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

}
