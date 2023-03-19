/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Communication;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 *
 * @author akvnr
 */
public class Email {
    public void Send(String email,String Cont,String sub,String var,int opt)       
    {
      
    String  to =email; 
    String  from = "ponamalarfireworksfactory@gmail.com"; 
    String  password = "gjgpzvbvlqiksbyh"; 
    String  content=Cont;
  Properties prop = new Properties();
  prop.put("mail.smtp.host", "smtp.gmail.com");
  prop.put("mail.smtp.port", "465");
  prop.put("mail.smtp.auth", "true");
  prop.put("mail.smtp.socketFactory.port", "465");
  prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
  Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
   @Override
   protected PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(from, password);
   }
  });
 
  try{
            MimeMessage m =new MimeMessage(session);
            m.setFrom(from);
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            m.setSubject(sub);
            m.setText(content);
            Transport.send(m);
            if(opt==1)
    {
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(content, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
    
   MimeBodyPart attachmentBodyPart = new MimeBodyPart();
     try {
         attachmentBodyPart.attachFile(new File("C:\\Users\\akvnr\\Documents\\Bills\\"+var+".pdf"));
     } catch (IOException ex) {
         
     }
             multipart.addBodyPart(attachmentBodyPart);
            m.setContent(multipart);
            Transport.send(m);
//            JOptionPane.showMessageDialog(null, "Successfully");   
        }
  }
        catch(HeadlessException | MessagingException e)
        {
//            JOptionPane.showMessageDialog(null, "unsuccessfull");
    }
    }
}
