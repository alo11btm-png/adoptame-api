package mx.edu.unpa.adoptame.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String basePath = System.getProperty("user.dir") + "/src/main/resources/static/images/";
		registry.addResourceHandler("/images/**")
				.addResourceLocations("file:" + basePath);
		registry.addResourceHandler("/uploads/**")
				.addResourceLocations("file:" + basePath);
	}
}
