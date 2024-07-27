package com.aprilboiz.musicpage;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserTestController {
    @Operation(summary = "Get user", tags = {"Test API"})
    @GetMapping("/user")
    public String getUser() {
        return "User method called";
    }

    @Operation(summary = "Get admin", tags = {"Test API"})
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdmin() {
        return "Admin method called";
    }
}
