package com.aprilboiz.musicpage.auth;

import com.aprilboiz.musicpage.auth.dto.LoginRequest;
import com.aprilboiz.musicpage.auth.dto.LoginResponse;
import com.aprilboiz.musicpage.auth.dto.RegisterRequest;
import com.aprilboiz.musicpage.jwt.JwtHelper;
import com.aprilboiz.musicpage.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService,
                          JwtHelper jwtHelper) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtHelper = jwtHelper;
    }

    @Operation(summary = "Register a new user", tags = {"Auth"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "404", description = "Role is not found"),
            @ApiResponse(responseCode = "409", description = "Username already exists")
    })
    @PostMapping("/register")
    public ResponseEntity<Void> signup(@Valid @RequestBody RegisterRequest request){
        userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Login", tags = {"Auth"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid username or password", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied: Invalid token type or token expired", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        );

        Authentication auth = authenticationManager.authenticate(authToken);
        String token = jwtHelper.generateToken((UserDetails) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
