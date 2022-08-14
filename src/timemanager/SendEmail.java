package timemanager;
  
import javax.mail.*;  
import javax.mail.internet.*;    
  
import java.util.Properties; 
  
public class SendEmail {  
 public static void main(String email,String Subject,String msg) throws Exception,MessagingException{  
	 
  if(email == ""||Subject == "" || msg == "") throw new Exception();
	 
  String host="smtp.gmail.com";  
  final String user="email"; 
  final String password="password";  
    
  String to=email;  
  
   Properties props = new Properties();
   props.put("mail.smtp.host",host);
   props.put("mail.smtp.port", "587"); 
   props.put("mail.smtp.starttls.enable", "true");
   props.put("mail.smtp.auth", "true");
     
   Session session = Session.getDefaultInstance(props,  
    new javax.mail.Authenticator() {  
      protected PasswordAuthentication getPasswordAuthentication() {  
    return new PasswordAuthentication(user,password);  
      }  
    });  
    
    try {  
     MimeMessage message = new MimeMessage(session);  
     message.setFrom(new InternetAddress(user));  
     message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
     message.setSubject(Subject);
     message.setText(msg);  
        
     Transport.send(message);   
     } catch (MessagingException e) {throw e;}  
 }  
}  
