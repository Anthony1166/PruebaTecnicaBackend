package com.Anthony.PruebaTecnica.security.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.Anthony.PruebaTecnica.security.auth.dtos.JwtAuthResponse;
import com.Anthony.PruebaTecnica.security.auth.dtos.LoginUser;
import com.Anthony.PruebaTecnica.security.auth.dtos.RegisterUser;
import com.Anthony.PruebaTecnica.security.entities.User;
import com.Anthony.PruebaTecnica.security.entities.repositories.UserRepository;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginUser loginUser) {
        String jwt = authService.authenticate(loginUser.getUsername(), loginUser.getPassword());
        User user = userRepository.findByUsername(loginUser.getUsername()).orElseThrow();
        return ResponseEntity.ok(new JwtAuthResponse(jwt, user.getUsername(), user.getRole().getName()));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterUser registerUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errores = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(java.util.stream.Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            authService.registerUser(registerUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registrado");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/check-auth")
    public ResponseEntity<String> checkAuth() {
        return ResponseEntity.ok().body("Autenticado");
    }
}