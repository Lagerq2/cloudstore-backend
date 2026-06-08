package systementor.cloudstoreuserservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import systementor.cloudstoreuserservice.model.user.AppUser;
import systementor.cloudstoreuserservice.model.user.dto.AuthResponse;
import systementor.cloudstoreuserservice.model.user.dto.LoginRequest;
import systementor.cloudstoreuserservice.model.user.dto.RegisterRequest;
import systementor.cloudstoreuserservice.repository.user.AppUserRepository;
import systementor.cloudstoreuserservice.security.JwtService;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AppUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;

    @Test
    void testRegisterUser() {
        RegisterRequest request = new RegisterRequest("test", "test@email.com", "password123");

        when(userRepository.existsByEmail("test@email.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(jwtService.generateToken("test@email.com")).thenReturn("token");

        AuthResponse svar = authService.register(request);

        assertNotNull(svar);
        assertEquals("token", svar.token());
        assertEquals("test@email.com", svar.email());

        verify(userRepository, times(1)).save(any(AppUser.class));
    }

    @Test
    void testLoginUser() {
        LoginRequest request = new LoginRequest("test@email.com", "password123");

        when(jwtService.generateToken("test@email.com")).thenReturn("token");

        AuthResponse svar = authService.login(request);

        assertNotNull(svar);
        assertEquals("token", svar.token());
        assertEquals("test@email.com", svar.email());
    }


}