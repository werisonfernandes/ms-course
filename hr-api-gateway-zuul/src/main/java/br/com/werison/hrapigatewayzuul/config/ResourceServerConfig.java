package br.com.werison.hrapigatewayzuul.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private JwtTokenStore tokenStore;

	private static String[] PUBLIC = { "/hr-oauth/oauth/token" };
	private static String[] OPERATOR = { "/hr-worker/**" };
	private static String[] ADMIN = { "/hr-payroll/**", "/hr-user/**", "/actuator/**", "/hr-worker/actuator/**",
			"/hr-oauth/actuator/**" };

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(PUBLIC).permitAll().antMatchers(HttpMethod.GET, OPERATOR)
				.hasAnyRole("ADMIN", "OPERATOR").antMatchers(ADMIN).hasRole("ADMIN").anyRequest().authenticated();

		http.cors().configurationSource(corsConfigurationSource());
	}

	/*
	 * fetch("http://localhost:8765/hr-worker/workers", { "headers": { "accept":
	 * "*\/*", "accept-language": "en-US,en;q=0.9,pt-BR;q=0.8,pt;q=0.7",
	 * "sec-fetch-dest": "empty", "sec-fetch-mode": "cors", "sec-fetch-site":
	 * "cross-site" }, "referrer": "http://localhost:3000", "referrerPolicy":
	 * "no-referrer-when-downgrade", "body": null, "method": "GET", "mode": "cors",
	 * "credentials": "omit" });
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(Arrays.asList("*"));
		corsConfig.setAllowedMethods(Arrays.asList("PUT", "POST", "GET", "DELETE", "PATCH"));
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);

		return source;
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> filter = new FilterRegistrationBean<>(
				new CorsFilter(corsConfigurationSource()));
		filter.setOrder(Ordered.HIGHEST_PRECEDENCE);

		return filter;
	}
}
