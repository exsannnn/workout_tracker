package ru.ilyalagutin.workouttracker.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ilyalagutin.workouttracker.dto.auth.AuthResponse;
import ru.ilyalagutin.workouttracker.dto.auth.LoginRequest;
import ru.ilyalagutin.workouttracker.dto.auth.RegisterRequest;
import ru.ilyalagutin.workouttracker.entity.User;
import ru.ilyalagutin.workouttracker.entity.UserRole;
import ru.ilyalagutin.workouttracker.exception.InvalidCredentialsException;
import ru.ilyalagutin.workouttracker.repository.UserRepository;
import ru.ilyalagutin.workouttracker.security.JwtService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerCreatesUserAndReturnsToken() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("user@example.com");
        request.setPassword("password");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded-password");
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(jwtService.createToken(any(User.class))).thenReturn("token");

        AuthResponse response = authService.register(request);

        assertEquals("token", response.getToken());
    }

    @Test
    void loginReturnsTokenForCorrectPassword() {
        LoginRequest request = new LoginRequest();
        request.setEmail("user@example.com");
        request.setPassword("password");
        User user = new User("user@example.com", "encoded-password", UserRole.USER);

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtService.createToken(user)).thenReturn("token");

        AuthResponse response = authService.login(request);

        assertEquals("token", response.getToken());
    }

    @Test
    void loginThrowsExceptionForWrongPassword() {
        LoginRequest request = new LoginRequest();
        request.setEmail("user@example.com");
        request.setPassword("wrong-password");
        User user = new User("user@example.com", "encoded-password", UserRole.USER);

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> authService.login(request));
    }
}
