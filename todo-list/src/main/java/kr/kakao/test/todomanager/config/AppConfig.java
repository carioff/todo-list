package kr.kakao.test.todomanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

	@Bean 
	public RestTemplate restTemplate() {
		return new RestTemplate(); // 자동으로 로딩시에 주입된다.
	}
}
