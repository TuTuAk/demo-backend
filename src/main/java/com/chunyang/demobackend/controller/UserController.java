package com.chunyang.demobackend.controller;

import com.chunyang.demobackend.dto.LoginDTO;
import com.chunyang.demobackend.dto.TokenDTO;
import com.chunyang.demobackend.dto.UserDTO;
import com.chunyang.demobackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * create new account
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        UserDTO user = userService.userRegister(userDTO);
        return ResponseEntity.ok(user);
    }

    /**
     * login
     *
     * @param loginDTO
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        TokenDTO tokenDTO = userService.login(loginDTO);
        return ResponseEntity.ok(tokenDTO);
    }

    /**
     * get the login user info
     * @return
     */
    @GetMapping
    public ResponseEntity<UserDTO> findCurrentUserDetail() {
        UserDTO userDTO = userService.getCurrentUserInfo();
        return ResponseEntity.ok(userDTO);
    }

    /**
     * get user info by username or email
     * @return
     */
    @GetMapping("/{identity}")
    public ResponseEntity<UserDTO> findUserDetail(@PathVariable String identity) {
        UserDTO userDTO = userService.getUserInfoByUserIdentity(identity);
        return ResponseEntity.ok(userDTO);
    }
}
