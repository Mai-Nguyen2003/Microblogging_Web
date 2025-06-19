package oth.ics.wtp.taDa_backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import oth.ics.wtp.taDa_backend.dtos.AppUserCreateDto;
import oth.ics.wtp.taDa_backend.dtos.AppUserDto;
import oth.ics.wtp.taDa_backend.dtos.AppUserUpdateDto;
import oth.ics.wtp.taDa_backend.entities.AppUser;
import oth.ics.wtp.taDa_backend.services.AppUserService;
import oth.ics.wtp.taDa_backend.services.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;


@RestController
public class AppUserController {
    private final AppUserService userService;
    private final AuthService authService;

    @Autowired
    public AppUserController(AuthService authService, AppUserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public AppUserDto createUser(@RequestBody AppUserCreateDto createUser) {
        return userService.create(createUser);
    }

    @SecurityRequirement(name = "basicAuth")
    @GetMapping(value = "users/{userName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AppUserDto getUser(HttpServletRequest request, @PathVariable("userName") String userName) {
        AppUser user = authService.getAuthenticatedUser(request);
        return userService.get(userName);
    }

    @SecurityRequirement(name = "basicAuth")
    @PutMapping(value = "users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AppUserDto updateUser(HttpServletRequest request, @RequestBody AppUserUpdateDto updateUser) {
        AppUser user = authService.getAuthenticatedUser(request);
        return userService.update(user, updateUser);
    }

    @SecurityRequirement(name = "basicAuth")
    @PostMapping(value = "users/login")
    public AppUserDto logIn(HttpServletRequest request) {
        AppUser user = authService.logIn(request);
        return userService.get(user.getUserName());
    }

    @SecurityRequirement(name = "basicAuth")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(value = "users/logout")
    public void logOut(HttpServletRequest request) {
        authService.logOut(request);
    }

    @SecurityRequirement(name = "basicAuth")
    @GetMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AppUserDto> listUsers() {
        return userService.list();
    }
}
