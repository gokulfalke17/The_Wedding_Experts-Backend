package com.twe.theweddingexperts.security;

import com.twe.theweddingexperts.enums.UserRole;
import com.twe.theweddingexperts.model.User;
import com.twe.theweddingexperts.repository.IUserRepository;
import com.twe.theweddingexperts.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final IUserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .email(email)
                            .fullName(oAuth2User.getAttribute("name"))
                            .role(UserRole.CUSTOMER)
                            .build();
                    return userRepository.save(newUser);
                });

        String token = jwtUtil.generateToken(user.getId(), user.getRole().name());

        response.sendRedirect(
                "http://localhost:3000/oauth-success?token=" + token
        );
    }


    private String extractEmail(OAuth2User user) {
        if (user.getAttribute("email") != null) {
            return user.getAttribute("email");
        }
        return user.getAttribute("login") + "@github.com";
    }

    private String extractName(OAuth2User user) {
        return user.getAttribute("name") != null
                ? user.getAttribute("name")
                : user.getAttribute("login");
    }
}