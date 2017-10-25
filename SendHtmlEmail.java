package emailbrouser;

import com.sun.mail.smtp.SMTPSendFailedException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.io.*;
import java.util.List;
import java.util.Properties;

public class SendHtmlEmail {
    private Message message = null;
    private String fromName;
    private List<String> toList;
    private String them;
    private List<File> fileList;
    private String text;

    public SendHtmlEmail(String from, List<String> toList, String them, List<File> fileList, String text) {
        this.fromName = from;
        this.toList = toList;
        this.them = them;
        this.fileList = fileList;
        this.text = text;

        String propertyFile = SendWindow.PROPERTIES_FOLDER + "//" + fromName + ".properties";

        try (InputStream is = new FileInputStream(propertyFile)) {
            Reader reader = new InputStreamReader(is);
            Properties propertiesFromFile = new Properties();
            propertiesFromFile.load(reader);
            String smtp_host = propertiesFromFile.getProperty("smtp_host");
            String smtp_port = propertiesFromFile.getProperty("smtp_port");
            String user = propertiesFromFile.getProperty("user");
            String pass = propertiesFromFile.getProperty("pass");
            String replyTo = propertiesFromFile.getProperty("reply_to");
            String emailfrom = propertiesFromFile.getProperty("from");


            // Настройка SMTP SSL
            Properties properties = new Properties();
            properties.put("mail.smtp.host", smtp_host);
            properties.put("mail.smtp.port", smtp_port);
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory"
            );
            try {
                Authenticator auth = new EmailAuthenticator(user, pass);
                Session session = Session.getInstance(properties, auth);
                session.setDebug(false);

                InternetAddress email_from = new InternetAddress(emailfrom);

                InternetAddress reply_to = (replyTo != null) ?
                        new InternetAddress(replyTo) : null;
                message = new MimeMessage(session);
                message.setFrom(email_from);


                for (String to : toList) {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                }

                message.setSubject(them);
                if (reply_to != null)
                    message.setReplyTo(new Address[]{reply_to});
            } catch (AddressException e) {

                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                        "Ошибка в адресе",
                        "Ошибка!",
                        JOptionPane.ERROR_MESSAGE
                );
            } catch (MessagingException e) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                        "Ошибка при создании сообщения",
                        "Ошибка!",
                        JOptionPane.ERROR_MESSAGE
                );
            }


        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "Файл учетной записи " + from + " не найден!",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (IOException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "Ошибка при попытке прочесть файл " + propertyFile,
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
            );
        }

    }

    boolean sendMessage() {
        boolean result = false;
        try {
            // Содержимое сообщения
            Multipart multipart = new MimeMultipart();
            // Текст сообщения
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(text, "text/html");
            multipart.addBodyPart(bodyPart);
            if (fileList != null && !fileList.isEmpty()) {
                for (File file : fileList) {
                    multipart.addBodyPart(createFileAttachment(file));
                }
            }
            message.setContent(multipart);
            Transport.send(message);
            result = true;


        } catch (AuthenticationFailedException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "Неправильно указан логин или пароль!",
                    "Ошибка авторизации",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (SMTPSendFailedException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "Неправильно указан e-mail получателя!!!",
                    "Недействительный адрес",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "При отправке сообщения возникла ошибка!",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "При прикреплении файла произощла ошибка!",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return result;
    }

    private MimeBodyPart createFileAttachment(File file) throws MessagingException, IOException {
        // Создание MimeBodyPart
        MimeBodyPart mbp = new MimeBodyPart();

        // Определение файла в качестве контента
        FileDataSource fds = new FileDataSource(file);
        mbp.setDataHandler(new DataHandler(fds));
        String englishFileName = LanguageUpdate.changeRussianOnEnglish(file.getName());
        mbp.setFileName(englishFileName);
        return mbp;
    }
}
