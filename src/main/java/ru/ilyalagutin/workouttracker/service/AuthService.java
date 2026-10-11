package ru.ilyalagutin.workouttracker.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilyalagutin.workouttracker.dto.auth.AuthResponse;
import ru.ilyalagutin.workouttracker.dto.auth.LoginRequest;
import ru.ilyalagutin.workouttracker.dto.auth.RegisterRequest;
import ru.ilyalagutin.workouttracker.entity.User;
import ru.ilyalagutin.workouttracker.entity.UserRole;
import ru.ilyalagutin.workouttracker.exception.EmailAlreadyExistsException;
import ru.ilyalagutin.workouttracker.exception.InvalidCredentialsException;
import ru.ilyalagutin.workouttracker.repository.UserRepository;
import ru.ilyalagutin.workouttracker.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                UserRole.USER);
        userRepository.save(user);

        return new AuthResponse(jwtService.createToken(user));
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return new AuthResponse(jwtService.createToken(user));
    }
}
