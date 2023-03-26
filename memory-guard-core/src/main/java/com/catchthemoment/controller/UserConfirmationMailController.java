package com.catchthemoment.controller;

import com.catchthemoment.dto.UserDTO;
import com.catchthemoment.service.UserConfirmMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserConfirmationMailController {

    private final UserConfirmMailService mailService;

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST},
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> confirmUserAccount(@RequestBody @NotNull UserDTO requestUser) {
        mailService.sendConfirmationEmail(requestUser);
        return ResponseEntity.ok().build();
    }
}
