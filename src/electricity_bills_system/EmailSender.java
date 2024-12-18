
package electricity_bills_system;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.io.File;


public class EmailSender {

    public static void sendEmailWithAttachment(String toEmail, String subject, String body, String attachmentPath) {
        String fromEmail = "carlosbackdev@gmail.com";  // Tu dirección de correo
        String password = "ienq evpl rstw hpqf";  // Tu contraseña de correo electric bill

        // Configurar propiedades de conexión SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); 
        properties.put("mail.smtp.port", "587");  
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Crear sesión de correo
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // Crear mensaje de correo
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            // Crear parte para adjuntar archivo
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.attachFile(new File(attachmentPath));

            // Crear multipart y agregar archivo adjunto
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            // Establecer contenido del mensaje
            message.setContent(multipart);

            // Enviar el correo
            Transport.send(message);

            System.out.println("Correo enviado exitosamente a " + toEmail);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Prueba del método
        EmailSender emailSender = new EmailSender();   
        
    }
}
    

