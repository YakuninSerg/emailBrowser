package emailbrouser;

import javax.mail.*;
import javax.mail.internet.MimeUtility;
import javax.mail.search.FlagTerm;
import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RecieveHtmlEmails {
    private static int countOfRemainingMessage;
    private static final String ENCODING = "UTF-8";
    private static String acc;
    private static String user;
    private static String pass;
    private static String smtp_host;
    private static String smtp_port;
    private static String pop_host;
    private static String pop_port;

    private static void recieveMessage(String account) {

        acc = account;
        LinkedList<PopEmailBeen> listMessages = new LinkedList<>();
        Properties propsFromFile = new Properties();
        String fileName = SendWindow.PROPERTIES_FOLDER + "//" + account + ".properties";
        try (InputStream is = new FileInputStream(fileName)) {
            propsFromFile.load(is);
            user = propsFromFile.getProperty("user");
            pass = propsFromFile.getProperty("pass");
            smtp_host = propsFromFile.getProperty("smtp_host");
            smtp_port = propsFromFile.getProperty("smtp_port");
            pop_host = propsFromFile.getProperty("pop_host");
            pop_port = propsFromFile.getProperty("pop_port");

            Authenticator auth = new EmailAuthenticator(user, pass);

            Properties props = new Properties();
            props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.pop3.socketFactory.fallback", "false");
            props.put("mail.pop3.socketFactory.port", pop_port);
            props.put("mail.pop3.port", pop_port);
            props.put("mail.pop3.host", pop_host);
            props.put("mail.pop3.user", user);
            props.put("mail.store.protocol", "pop3");
            props.put("mail.user", user);
            props.put("mail.host", pop_host);
            props.put("mail.debug", "false");
            props.put("mail.store.protocol", "pop3");
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.pop3.auth", "true");
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.pop3.ssl.enable", "true");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.mime.charset", ENCODING);


            Session session = Session.getInstance(props, auth);
            URLName url = new URLName("pop3", pop_host, Integer.parseInt(pop_port), "",
                    user, pass);
            Store store = session.getStore(url);
            store.connect();
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message[] unSeenMessages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            // Message[] answeredMessages = inbox.search(new FlagTerm(new Flags(Flags.Flag.ANSWERED), true));
            //Message[] recentMessages = inbox.search(new FlagTerm(new Flags(Flags.Flag.RECENT), true));
            // Message[] draftMessages = inbox.search(new FlagTerm(new Flags(Flags.Flag.DRAFT), true));

            try {
                loadMsgs(unSeenMessages);
            } catch (MessagingException e) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                        "Не удалось получить сообщение",
                        "Ошибка!",
                        JOptionPane.ERROR_MESSAGE
                );
                e.printStackTrace();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                        "Неизвестная ошибка!",
                        "Ошибка!",
                        JOptionPane.ERROR_MESSAGE
                );
                e.printStackTrace();
            }

            inbox.close(true);
            store.close();


        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "Файл настроек аккаунта " + account + "не найден!",
                    "Ошибка!",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "Ошибка при чтении файла:\n" + fileName,
                    "Ошибка!",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

        } catch (NoSuchProviderException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "Указанный провайдер не найден",
                    "Ошибка!",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

        } catch (AuthenticationFailedException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "Неправильно указан логин или пароль!",
                    "Ошибка авторизации",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    private static void loadMsgs(Message[] messages) throws MessagingException, IOException {

        ArrayList<String> attachments = new ArrayList<>();
        SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        countOfRemainingMessage = messages.length;
        for (Message inMessage : messages) {
            inMessage.setFlag(Flags.Flag.DELETED, true);
            System.out.println(countOfRemainingMessage);
            countOfRemainingMessage--;
            attachments.clear();

            if (inMessage.isMimeType("text/html")) {//Если письмо простое а не составное
                String text;
                try {
                    text = inMessage.getContent().toString();
                } catch (UnsupportedEncodingException e) {
                    text = "Ошибка при чтении письма";

                }

                PopEmailBeen message = new PopEmailBeen(
                        text,
                        MimeUtility.decodeText(inMessage.getSubject()),
                        f.format(inMessage.getSentDate()),
                        inMessage.getFrom()[0].toString(),
                        null,
                        attachments,
                        false,
                        inMessage.getMessageNumber()

                );
                saveMessage(message);


            } else if (inMessage.isMimeType("multipart/*")) {
                Multipart mp = (Multipart) inMessage.getContent();
                String thema = MimeUtility.decodeText(inMessage.getSubject());
                String dateString = f.format(inMessage.getSentDate());
                String from = inMessage.getFrom()[0].toString();
                int number = inMessage.getMessageNumber();

                PopEmailBeen message = new PopEmailBeen(
                        "Без текста",
                        thema,
                        dateString,
                        from,
                        null,
                        attachments,
                        false,
                        number);

                for (int i = 0; i < mp.getCount(); i++) {
                    Part part = mp.getBodyPart(i);
                    if ((part.getFileName() == null || part.getFileName().isEmpty()) && part.isMimeType("text/html")) {
                        String text;
                        try {
                            text = part.getContent().toString();
                        } catch (UnsupportedEncodingException e) {
                            text = "Ошибка при чтении письма";

                        }
                        message.setText(text);

                    } else if (part.getFileName() != null || !Objects.equals(part.getFileName(), "")) {
                        if ((part.getDisposition() != null) && (part.getDisposition().equals(Part.ATTACHMENT))) {
                            attachments.add(saveFile(MimeUtility.decodeText(part.getFileName()), part.getInputStream()));

                        }
                    }
                }
                message.setAttachedFiles(attachments);
                saveMessage(message);


            }


        }


    }


    private static String saveFile(String filename, InputStream input) {
        String dir = SendWindow.PROPERTIES_FOLDER + "//" + acc + "//unseenFiles";
        File directory = new File(dir);
        if (!directory.exists()) directory.mkdirs();
        String path = dir + "//" + filename;
        File file = new File(path);
        try {
            byte[] attachment = new byte[input.available()];
            input.read(attachment);
            FileOutputStream out = new FileOutputStream(file);
            out.write(attachment);
            input.close();
            out.close();
            return path;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    private static void saveMessage(PopEmailBeen popEmailBeen) {
        if (popEmailBeen != null) {
            Date date;
            String dir = SendWindow.PROPERTIES_FOLDER + "//" + acc + "//recieved";
            File directory = new File(dir);
            if (!directory.exists()) directory.mkdirs();
            SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            try {
                date = f.parse(popEmailBeen.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
                return;
            }
            SimpleDateFormat newFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
            String dateToFile = newFormat.format(date);
            String path = dir + "//" + "letter_" + dateToFile + ".msg";
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {

                out.writeObject(popEmailBeen);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        RecieveHtmlEmails.recieveMessage("79031587670");

    }

}