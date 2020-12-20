
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

//import javax.mail.PasswordAuthenticator;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class javamailutil {
	
	public static void sendMail(String reciepent, String subject123, String message123) throws MessagingException {
		System.out.println("Prepare to send email");
		//this is to configure the properties of the mail
		//Properties is just a key value to these properties
		Properties properties = new Properties();
		//mail.smtp.auth - is the properties to define whether there is authencation require
		//mail.smtp.starttls.enable - encrytion
		//mail.smtp.host - smtp.gmail.com
		//mail.smtp.port -587
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		String myEmailAcc = "2002object@gmail.com";
		String password = "Ce2002cz";
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myEmailAcc, password);
						
			}
		});
		
		Message message = prepareMessage(session, myEmailAcc, reciepent, subject123, message123);
		
		Transport.send(message);
		System.out.println("Message sent successful");
		
	}
	private static Message prepareMessage(Session session, String myEmailAcc, String reciepent, String subject123, String message123) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myEmailAcc));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(reciepent));
			message.setSubject(subject123);
			message.setText(message123);
			return message;
		}
		catch(Exception ex) {
			Logger.getLogger(javamailutil.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
		
}