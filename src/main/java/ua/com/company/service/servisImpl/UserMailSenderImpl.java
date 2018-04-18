package ua.com.company.service.servisImpl;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import ua.com.company.entity.User;
import ua.com.company.service.IuserMailSender;

@Service
@PropertySource("classpath:mailSender.properties")
public class UserMailSenderImpl implements IuserMailSender {

	@Autowired
	private Environment env;
	@Autowired
	private JavaMailSender sender;

	@Override
	public void sendLetter(User user) {

		MimeMessage mimeMessage = sender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

		try {
			messageHelper.setFrom(new InternetAddress(env.getProperty("mailSender.userName")));
			messageHelper.setTo(user.getEmail());
			messageHelper.setText("<h4>hello dear user<h4><br> " + user.getName() + " you have been registred in"
					+ " some suspitient web site with email " + user.getEmail() + " and password " + user.getPassword(),
					true);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		sender.send(mimeMessage);

	}

}
