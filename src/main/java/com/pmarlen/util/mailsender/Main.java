package com.pmarlen.util.mailsender;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * com.pmarlen.util.mailsender.Main
 * @author alfredo estrada
 */
public class Main {

	public static void main(String[] args) {
		System.err.println("PERFUMERIA MARLEN >> SEND MAIL");
        
        String fromMail    = null;
        String fromPsswd   = null;
        String toMail      = null;
        String subjectMail = null;
        String bodyMail    = null;
        if(args.length != 5){
            System.err.println("usage: java -jar MailSender.jar com.pmarlen.util.mailsender.Main   fromMail fromPsswd  toMail  subjecMail  bodyMail  ");
            System.exit(1);
            //java -jar MailSender.jar "l30@perfumeriamarlen.com.mx" "PM-L30#M4il" "aestrada@perfumeriamarlen.com.mx" "__S" "__B"
        }
		fromMail    = args[0];
        fromPsswd   = args[1];
		toMail      = args[2];
        subjectMail = args[3];
        bodyMail    = args[4];
        
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
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(toMail));

			// Set Subject: header field
			message.setSubject(subjectMail);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			// Now set the actual message
            Date hoy= new Date();
			//message.setText("__"+bodyMail+"_["+sdf.format(hoy)+"]__");
            message.setText(bodyMail);
            System.err.println("Before send....");
			// Send message
			Transport.send(message);

			System.err.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace(System.err);
		}
	}
}
