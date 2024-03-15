package com.prf.email;

import jakarta.mail.internet.MimeMessage;

import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class ProviderCredentialsMail {
	
	@Value("${reciboo.app.web.login}")
	private String recibooLoginURL;
	
	private final TemplateEngine templateEngine;

	private final JavaMailSender javaMailSender;

	public ProviderCredentialsMail(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
		this.templateEngine = templateEngine;
		this.javaMailSender = javaMailSender;
	}
	
	public void sendCredentialEmail(String email, String usuario, String password) {
		
		Thread sendEmailThread = new Thread(()->{
			
			Context context = new Context();
			context.setVariable("user", usuario);
			context.setVariable("password", password);
			context.setVariable("recibooLoginURL", recibooLoginURL);
			
			String process = templateEngine.process("emails/credentials", context);
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			try {
				helper.setSubject("Reciboo Credentials");
				helper.setText(process, true);
				helper.setTo(email);
				javaMailSender.send(mimeMessage);
			} catch (MessagingException e) {
				throw new RuntimeException("No se han podido enviar el mail : \n" + e.getLocalizedMessage());
			}
			
		});
		
		sendEmailThread.start();
	}
	

}
