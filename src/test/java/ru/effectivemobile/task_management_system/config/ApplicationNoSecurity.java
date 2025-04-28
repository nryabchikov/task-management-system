package ru.effectivemobile.task_management_system.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.effectivemobile.task_management_system.service.TaskAccessService;

import java.util.UUID;

@Configuration
@Profile("test")
@EnableMethodSecurity(prePostEnabled = false)
public class ApplicationNoSecurity {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/**"));
    }

    @Bean
    public TaskAccessService taskAccessService() {
        return new TaskAccessService() {
            @Override
            public boolean isPerformer(UUID taskId, String userId) {
                return true;
            }

            @Override
            public boolean isAdmin(Authentication authentication) {
                return true;
            }
        };
    }
}
