package com.ken42;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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

public class SendMail {

    public static void sendEmail() {

        // Recipient's email ID needs to be mentioned.
        String to = "anand.rathi@ken42.com";

        // Sender's email ID needs to be mentioned
        String from = "anandTest2002@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.user", "anandTest2002@gmail.com");
        properties.put("mail.smtp.password", "ccsegdbmljmqpfzg");



        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("anandTest2002@gmail.com", "ccsegdbmljmqpfzg");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);
   
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
   
            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
               InternetAddress.parse(to));
   
            // Set Subject: header field
            message.setSubject("Automation Execution logs");
   
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
   
            // Now set the actual message
            messageBodyPart.setText("Attached is the results file");
   
            // Create a multipar message
            Multipart multipart = new MimeMultipart();
   
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
   
            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "C:\\Users\\Public\\Documents\\PFS_results.log";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
   
            // Send the complete message parts
            message.setContent(multipart);
   
            // Send message
            Transport.send(message);
   
            System.out.println("Sent message successfully....");
     
         } catch (MessagingException e) {
            throw new RuntimeException(e);
         }

    }

}