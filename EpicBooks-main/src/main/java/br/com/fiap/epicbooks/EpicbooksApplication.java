package br.com.fiap.epicbooks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@SpringBootApplication
public class EpicbooksApplication implements WebMvcConfigurer {

	private final LocaleChangeInterceptor localInterceptor;

	public EpictaskApplication(LocaleChangeInterceptor localInterceptor) {
		this.localInterceptor = localInterceptor;
	}

	public static void main(String[] args) {
		SpringApplication.run(EpicbooksApplication.class, args);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localInterceptor);
	}

}
