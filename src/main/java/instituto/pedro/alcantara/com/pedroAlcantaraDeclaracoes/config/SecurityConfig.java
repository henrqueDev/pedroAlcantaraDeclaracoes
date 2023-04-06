package instituto.pedro.alcantara.com.pedroAlcantaraDeclaracoes.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@SuppressWarnings("deprecation")
public class SecurityConfig extends WebSecurityConfigurerAdapter {



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors()
				.and()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/instituicoes/**")
				.permitAll()
                .antMatchers(HttpMethod.POST, "/instituicoes/**")
				.permitAll()
                .antMatchers(HttpMethod.GET, "/estudantes/**")
				.permitAll()
                .antMatchers(HttpMethod.POST, "/estudantes/**")
				.permitAll();
	}

}
