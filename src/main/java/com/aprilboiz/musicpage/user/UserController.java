package com.aprilboiz.musicpage.user;

import com.aprilboiz.musicpage.person.PersonService;
import com.aprilboiz.musicpage.person.dto.PersonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final PersonService personService;
    private final UserService userService;

    public UserController(PersonService personService, UserService userService) {
        this.personService = personService;
        this.userService = userService;
    }

    @Operation(summary = "Get user information", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information found", content = @Content(schema = @Schema(implementation = PersonResponse.class))),
            @ApiResponse(responseCode = "404", description = "User information not found", content = @Content)
    })
    @GetMapping("/get_info")
    public ResponseEntity<PersonResponse> getUserInfo() {
        return ResponseEntity.ok(personService.findPersonByUser(userService.getCurrentUser()).toDTO());
    }

    @Operation(summary = "Get user information by username, require Admin role", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information found", content = @Content(schema = @Schema(implementation = PersonResponse.class))),
            @ApiResponse(responseCode = "404", description = "User information not found", content = @Content)
    })
    @GetMapping("/get_info/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PersonResponse> getUserInfo(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(personService.findPersonByUser(user).toDTO());
    }
}
