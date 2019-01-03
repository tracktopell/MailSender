package com.pmarlen.util.mailsender;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * com.pmarlen.util.mailsender.Main
 *
 * @author alfredo estrada
 */
public class Main {

    public static void main(String[] args) {
        System.err.println("PERFUMERIA MARLEN >> SEND MAIL");

        String fromMail = null;
        String fromPsswd = null;
        String toMailList = null;
        String ccMailList = null;
        String subjectMail = null;
        String bodyMail = null;
        if (args.length != 6) {
            System.err.println("usage: java -jar MailSender.jar com.pmarlen.util.mailsender.Main  fromMail fromPsswd  toMailList  ccMailList subjecMail  bodyMail ");
            System.exit(1);
            //java -jar MailSender.jar "l30@perfumeriamarlen.com.mx" "PM-L30#M4il" "aestrada@perfumeriamarlen.com.mx" "__S" "__B"
        }
        fromMail    = args[0];
        fromPsswd   = args[1];
        toMailList  = args[2];
        ccMailList  = args[3];
        subjectMail = args[4];
        bodyMail    = args[5];

        // Sender's email ID needs to be mentioned
        final String username = fromMail;
        final String password = fromPsswd;
        // Assuming you are sending email from perfumeriamarlen.com.mx
        String host = "perfumeriamarlen.com.mx";
        String port = "26";//"25";
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", port);
        // Get the default Session object.
        Session session = null;

        try {

            System.err.println("before Session created:");

            session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            System.err.println("Session created....");

            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            System.err.println("preparing message....");
            // Set From: header field of the header.
            //message.setFrom(new InternetAddress("Ing. Alfredo Estrada G.<aestrada@perfumeriamarlen.com.mx>"));
            message.setFrom(new InternetAddress(fromMail));

            // Set To: header field of the header.
            String[] toMailListArr=toMailList.split(",");
            for(String toMail:toMailListArr){
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
                System.err.println(" ... adding recipient:"+toMail);
            }
            if (!ccMailList.trim().equalsIgnoreCase("null")){
                // Set CC: header field of the header.
                String[] ccMailListArr=ccMailList.split(",");
                for(String ccMail:ccMailListArr){
                    message.addRecipient(Message.RecipientType.CC, new InternetAddress(ccMail));
                    System.err.println("  ... adding cc:"+ccMail);
                }
            }
            // Set Subject: header field
            message.setSubject(subjectMail);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            // Now set the actual message
            Date hoy = new Date();
            
            //message.setText(bodyMail);
            
            Multipart multipart       = new MimeMultipart();
            BodyPart  messageBodyPart = new MimeBodyPart();
            
            messageBodyPart.setContent("<html><body><p>"+bodyMail+"</p></body></html>", "text/html");
            
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            
            
            System.err.println("Before send....");
            // Send message
            Transport.send(message);

            System.err.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace(System.err);
        }
    }
}
