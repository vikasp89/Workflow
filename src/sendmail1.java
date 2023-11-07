import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class sendmail1 {
  static final Logger LOGGER = LogManager.getLogger(sendmail1.class);
  
  public static void send(ArrayList<String> emailist) throws UnsupportedEncodingException {
    String from = "goyalnarayan99@gmail.com";
    String password = "goyal@123";
    String message1 = "<i></i><br>";
    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.socketFactory.port", "465");
    props.put("mail.smtp.socketFactory.class", 
        "javax.net.ssl.SSLSocketFactory");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.port", "465");
    Session session = Session.getDefaultInstance(props, 
        new Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("goyalnarayan99@gmail.com", "goyal@123");
          }
        });
    try {
      MimeMessage message = new MimeMessage(session);
      message.setFrom((Address)new InternetAddress(from, "Narayan"));
      message.addRecipient(Message.RecipientType.TO, (Address)new InternetAddress("amit.goyal4663@gmail.com"));
      message.setSubject("Hello Alam Sir");
      message.setContent(message1, "text/html");
      Transport.send((Message)message);
    } catch (MessagingException e) {
      LOGGER.info("Error At Searchcase=" + e.getMessage());
      throw new RuntimeException(e);
    } 
  }
}
