package com.catchthemoment.controller;

import com.catchthemoment.dto.UserDTO;
import com.catchthemoment.service.UserConfirmMailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserConfirmationMailControllerTest {

    @Mock
    private UserConfirmMailService confirmMailService;


    @InjectMocks
    private UserConfirmationMailController controller;

    @Test
    @DisplayName("test for confirm user account  mail ")
    void should_confirm_email_to_user() {
        UserDTO testUserDTO = mock(UserDTO.class);
        doNothing().when(confirmMailService).sendConfirmationEmail(testUserDTO);
        final ResponseEntity<?> current = controller.confirmUserAccount(testUserDTO);
        assertNotNull(current);
        verify(confirmMailService, times(1)).sendConfirmationEmail(testUserDTO);


    }

}