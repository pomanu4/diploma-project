package ua.com.company.config;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@EnableWebMvc
@ComponentScan("ua.com.company.*")
@PropertySource("classpath:mailSender.properties")
public class MainConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	@Autowired
	private Environment env;

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Bean
	ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("/WEB-INF/pages/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode("HTML5");

		return resolver;
	}

	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setEnableSpringELCompiler(true);
		templateEngine.setTemplateResolver(templateResolver());

		return templateEngine;
	}

	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		thymeleafViewResolver.setCharacterEncoding("UTF-8");
		thymeleafViewResolver.setContentType("text/html; charset=UTF-8");
		thymeleafViewResolver.setTemplateEngine(templateEngine());

		return thymeleafViewResolver;
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("mll/messagesUA", "mll/messagesEN");
		messageSource.setDefaultEncoding("UTF-8");

		return messageSource;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/style/**").addResourceLocations("WEB-INF/static/css/");
		registry.addResourceHandler("/img/**").addResourceLocations("WEB-INF/images/");
		registry.addResourceHandler("/jscript/**").addResourceLocations("WEB-INF/js/");
	}

	@Bean
	public MultipartResolver multipartResolver() {

		return new StandardServletMultipartResolver();
	}

	@Bean
	public JavaMailSenderImpl sender() {

		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		senderImpl.setHost(env.getProperty("mailSender.host"));
		senderImpl.setPort(env.getProperty("mailSender.port", Integer.class));
		senderImpl.setUsername(env.getProperty("mailSender.gamail.account"));
		senderImpl.setPassword(env.getProperty("mailSender.mailPassword"));
		Properties properties = senderImpl.getJavaMailProperties();
		properties.put(env.getProperty("mailSender.transportProtocol"),
				env.getProperty("mailSender.transportProtocol.value"));
		properties.put(env.getProperty("mailSender.authorisation"), env.getProperty("mailSender.authorisation.value"));
		properties.put(env.getProperty("mailSender.satrttls.enable"),
				env.getProperty("mailSender.satrttls.enable.value"));
		properties.put(env.getProperty("mailSender.debug"), env.getProperty("mailSender.debug.value"));

		return senderImpl;
	}

}
