package com.majin.bit.configuration;

import static com.majin.bit.security.SocialType.GOOGLE;
import static com.majin.bit.security.SocialType.KAKAO;
import static com.majin.bit.security.SocialType.NAVER;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import com.majin.bit.security.CustomOAuth2Provider;
import com.majin.bit.service.CustomOAuth2UserService;
import com.majin.bit.service.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MemberService memberServiceImpl;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberServiceImpl).passwordEncoder(passwordEncoder());
	}
	
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.authorizeRequests()
				.antMatchers("/", "/yundo/**", "/search/**", "/home", "/signup", "/idcheck", "/mailChk", "/mail", "/oauth2/**", "/login/**", "/css/**", "/images/**", "/js/**", "/console/**","/favicon.ico/**", "/file_uploader_DEXT","/findpw").permitAll()
				.antMatchers("/google").hasAuthority(GOOGLE.getRoleType())
				.antMatchers("/kakao").hasAuthority(KAKAO.getRoleType())
				.antMatchers("/naver").hasAuthority(NAVER.getRoleType())
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
				.and()
					.formLogin()
						.loginPage("/login")
						.loginProcessingUrl("/login")
						.defaultSuccessUrl("/")
						.usernameParameter("id")
						.passwordParameter("pw")
				.and()
					.oauth2Login().userInfoEndpoint().userService(new CustomOAuth2UserService())
				.and()
					.defaultSuccessUrl("/loginSuccess").
					failureUrl("/loginFailure")
				.and()
					.exceptionHandling()
					.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
				.and()
					.logout()
//						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutUrl("/logout")
						.logoutSuccessUrl("/")
						.invalidateHttpSession(true)
				.and()
					.headers()
					.frameOptions()
					.disable()
				.and()
					.csrf()
					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.and()
					.addFilterAfter(new ExceptionHandlerFilter(), SecurityContextHolderAwareRequestFilter.class);
						
	}

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties,
			@Value("${spring.security.oauth2.client.registration.kakao.client-id}") String kakaoClientId,
			@Value("${spring.security.oauth2.client.registration.kakao.client-secret}") String kakaoClientSecret,
			@Value("${spring.security.oauth2.client.registration.naver.client-id}") String naverClientId,
			@Value("${spring.security.oauth2.client.registration.naver.client-secret}") String naverClientSecret) {
		List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet().stream()
				.map(client -> getRegistration(oAuth2ClientProperties, client)).filter(Objects::nonNull)
				.collect(Collectors.toList());
		registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao").clientId(kakaoClientId)
				.clientSecret(kakaoClientSecret).jwkSetUri("temp").build());
		registrations.add(CustomOAuth2Provider.NAVER.getBuilder("naver").clientId(naverClientId)
				.clientSecret(naverClientSecret).jwkSetUri("temp").build());
		return new InMemoryClientRegistrationRepository(registrations);
	}

	private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) {
		if ("google".equals(client)) {
			OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google");
			return CommonOAuth2Provider.GOOGLE.getBuilder(client).clientId(registration.getClientId())
					.clientSecret(registration.getClientSecret()).scope("email", "profile").build();
		}
		return null;
	}
	
	@Override
	 public void configure(WebSecurity webSecurity) throws Exception {
		 
	  webSecurity.ignoring().antMatchers("/static/**", "/css/**", "/fonts/**", "/js/**", "/less/**", "/scss/**", "/images/**", "/webjars/**", "/resources/**"); 
	 }
	
	@Bean		
	public SpringSecurityDialect springSecurityDialect() {
	    return new SpringSecurityDialect();
	}
	
}
