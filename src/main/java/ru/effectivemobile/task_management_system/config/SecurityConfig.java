package ru.effectivemobile.task_management_system.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.GET, "/api/v1/tasks").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/tasks/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/tasks").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/tasks").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/tasks/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/tasks/{id}/status").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/tasks/{id}/priority").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/tasks/by-author/{authorId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/tasks/by-performer/{performerId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/comments").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/comments/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/v1/comments").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/comments").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/comments/{id}").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .oauth2Login(Customizer.withDefaults())
                .build();
    }

    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oAuth2UserService() {
        log.info("SecurityConfig.oAuth2UserService: Configuring OAuth2UserService");
        OidcUserService oidcUserService = new OidcUserService();
        log.info("SecurityConfig.oAuth2UserService: OAuth2UserService configured successfully.");
        return userRequest -> {
            OidcUser oidcUser = oidcUserService.loadUser(userRequest);
            log.debug("SecurityConfig.oAuth2UserService: Loaded OidcUser: {}", oidcUser);
            List<GrantedAuthority> authorities =
                    Stream.concat(oidcUser.getAuthorities().stream(),
                                    Optional.ofNullable(oidcUser.getClaimAsStringList("groups"))
                                            .orElseGet(List::of)
                                            .stream()
                                            .filter(role -> role.startsWith("ROLE_"))
                                            .map(SimpleGrantedAuthority::new)
                                            .map(GrantedAuthority.class::cast))
                            .toList();
            log.debug("SecurityConfig.oAuth2UserService: Authorities extracted: {}", authorities);
            return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
        };
    }
}
