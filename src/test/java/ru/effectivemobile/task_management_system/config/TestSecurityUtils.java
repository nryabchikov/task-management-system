package ru.effectivemobile.task_management_system.config;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

public class TestSecurityUtils {
    public static RequestPostProcessor mockUser() {
        return request -> {
            Authentication auth = new TestingAuthenticationToken(
                    "test-user",
                    null,
                    "ROLE_ADMIN"
            );
            auth.setAuthenticated(true);
            request.setUserPrincipal(auth);
            return request;
        };
    }
}
