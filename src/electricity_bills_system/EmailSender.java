
package electricity_bills_system;

import java.awt.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.*;


public class EmailSender extends JFrame {   
     
   public static void sendEmailWithAttachment(String toEmail, String subject, String body, String attachmentPath,String email_company) {
    String fromEmail = email_company;  // Tu dirección de correo
    String password = "ienq evpl rstw hpqf";  // Tu contraseña de correo

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
        // Crear el mensaje
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);

        // Cuerpo del mensaje
        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText(body);

        // Archivo adjunto
        MimeBodyPart attachmentBodyPart = new MimeBodyPart();
        attachmentBodyPart.attachFile(new File(attachmentPath));

        // Combinar cuerpo y adjunto en un multipart
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textBodyPart);      // Agregar el cuerpo del mensaje
        multipart.addBodyPart(attachmentBodyPart); // Agregar el archivo adjunto

        // Configurar el contenido del mensaje
        message.setContent(multipart);

        // Enviar el mensaje
        Transport.send(message);  

        JOptionPane.showMessageDialog(null, "Correo enviado con éxito!");

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public static void main(String[] args) {
        EmailSender emailSender = new EmailSender();   
        
    }
}
    

