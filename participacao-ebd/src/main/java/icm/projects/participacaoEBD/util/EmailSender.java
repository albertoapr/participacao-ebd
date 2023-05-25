package icm.projects.participacaoEBD.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    public static void sendEmail(String[] recipients, String subject, String message,final String username,final String senha) {
        // Configurações do servidor de email do Gmail
        String host = "smtp.gmail.com";
        String port = "587";
       

        // Configurações do JavaMail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Autenticação do remetente
       Authenticator autenticator = new javax.mail.Authenticator() {
    		protected PasswordAuthentication getPasswordAuthentication() {
                	return new PasswordAuthentication(username, senha);
            }
        };
        Session session = Session.getInstance(props, autenticator);

        try {
            // Criação da mensagem
            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(username));
            
            // Adiciona os destinatários
            InternetAddress[] addresses = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                addresses[i] = new InternetAddress(recipients[i]);
            }
            emailMessage.setRecipients(Message.RecipientType.TO, addresses);
            
            // Define o assunto e o conteúdo da mensagem
            emailMessage.setSubject(subject);
            emailMessage.setText(message);
            
            // Envio do email
            Transport.send(emailMessage);
            System.out.println("Email enviado com sucesso!");
        } catch (MessagingException e) {
            System.out.println("Erro ao enviar o email: " + e.getMessage());
        }
    }


}
