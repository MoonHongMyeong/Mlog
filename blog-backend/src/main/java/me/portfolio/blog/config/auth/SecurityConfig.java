package me.portfolio.blog.config.auth;

import lombok.RequiredArgsConstructor;
import me.portfolio.blog.domain.user.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v2/**").permitAll()
                //포스트 작성 정도나 권한 가지게 하면 될 것 같고
                .antMatchers("/api/v2/posts/form").hasRole(Role.USER.name())
                //회원 수정, 탈퇴를 제외하면 로그인 시 받아오는 세션을 포스트나 댓글등의 사용자 정보와 비교해서 수정 삭제 기능을 할수 있게
                .antMatchers("/api/v2/user/**").hasAnyRole(Role.USER.name(), Role.GUEST.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}
