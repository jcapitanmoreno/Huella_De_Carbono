package com.github.jcapitanmoreno.services;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    private final String username = "ayudagamehub@gmail.com";
    private final String password = "ffnt odps fuqc tnbj";

    /**
     * Envía un correo electrónico.
     *
     * @param correoUsuario el correo del usuario.
     * @param asunto el asunto del correo.
     * @param mensaje el mensaje del correo.
     * @return true si el correo fue enviado exitosamente, false en caso contrario.
     * @throws Exception si el correo del usuario está vacío.
     */
    public boolean enviarCorreo(String correoUsuario, String asunto, String mensaje) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoUsuario));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("jcapitanmoreno@gmail.com"));
            message.setSubject(asunto);
            message.setText(mensaje);

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
