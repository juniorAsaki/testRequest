package SecurityTest.example.testRequest.services.impl;

import SecurityTest.example.testRequest.models.Validation;
import SecurityTest.example.testRequest.services.EmailService;
import SecurityTest.example.testRequest.services.dto.ValidationDTO;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

  private JavaMailSender mailSender;

  public EmailServiceImpl(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void envoyer(ValidationDTO validation) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("juniorncho23@gmail.com");
    message.setTo(validation.getAppUser().getEmail());
    message.setSubject("Votre code d'activation");

    String texte = String.format(
            "Bonjour %s, <br /> Votre code d'action est %s; A bientôt",
            validation.getAppUser().getUsername(),
            validation.getCode()
    );
    message.setText(texte);

    mailSender.send(message);
  }
}
