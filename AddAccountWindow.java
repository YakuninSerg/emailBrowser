package emailbrouser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class AddAccountWindow extends JFrame {
    private String newAccount = null;
    private JComboBox emailComboBox = new JComboBox();

    AddAccountWindow() {
        super("Добавление учетной записи");
        setSize(380, 520);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        Dimension labelSize = new Dimension(120, 20);
        Dimension buttonSize = new Dimension(150, 80);

        Box mainBox = Box.createVerticalBox();
        Box bigUserBox = Box.createVerticalBox();

        Box userBox = Box.createHorizontalBox();
        Box emailBox = Box.createHorizontalBox();
        Box loginBox = Box.createHorizontalBox();
        Box passwordBox = Box.createHorizontalBox();

        Box bigSmtpBox = Box.createVerticalBox();
        Box smtpServerBox = Box.createHorizontalBox();
        Box smtpSaveConnectionBox = Box.createHorizontalBox();
        Box smtpPortBox = Box.createHorizontalBox();

        Box bigPOP3Box = Box.createVerticalBox();
        Box pop3ServerBox = Box.createHorizontalBox();
        Box pop3SaveConnectionBox = Box.createHorizontalBox();
        Box pop3PortBox = Box.createHorizontalBox();

        Box bigImapBox = Box.createVerticalBox();
        Box imapServerBox = Box.createHorizontalBox();
        Box imapSaveConnectionBox = Box.createHorizontalBox();
        Box imapPortBox = Box.createHorizontalBox();

        Box buttonBox = Box.createHorizontalBox();


        JLabel userLabel = new JLabel("Имя пользователя");
        JTextField userTextField = new JTextField();

        JLabel emailLabel = new JLabel("Адрес почты");
        JTextField emailTextField = new JTextField();
        JLabel atLabel = new JLabel("@");

        emailComboBox.setPreferredSize(new Dimension(100, 20));
        initEmailComboBox();
        emailComboBox.setSelectedIndex(-1);
        emailComboBox.setEditable(true);

        JLabel loginLabel = new JLabel("Логин");
        JTextField loginTextField = new JTextField();

        JLabel passwordLabel = new JLabel("Пароль");
        JPasswordField passwordField = new JPasswordField();

        JLabel smtpServerLabel = new JLabel("Адрес сервера");
        JTextField smtpServerTextField = new JTextField();

        JLabel smtpSaveConnectionLabel = new JLabel("Защита соединения");
        JTextField smtpSaveConnectionTextField = new JTextField();

        JLabel smtpPortLabel = new JLabel("Порт");
        JTextField smtpPortTextField = new JTextField();

        JLabel pop3ServerLabel = new JLabel("Адрес сервера");
        JTextField pop3ServerTextField = new JTextField();

        JLabel pop3SaveConnectionLabel = new JLabel("Защита соединения");
        JTextField pop3SaveConnectionTextField = new JTextField();

        JLabel pop3PortLabel = new JLabel("Порт");
        JTextField pop3PortTextField = new JTextField();

        JLabel imapServerLabel = new JLabel("Адрес сервера");
        JTextField imapServerTextField = new JTextField();

        JLabel imapSaveConnectionLabel = new JLabel("Защита соединения");
        JTextField imapSaveConnectionTextField = new JTextField();

        JLabel imapPortLabel = new JLabel("Порт");
        JTextField imapPortTextField = new JTextField();

        JButton saveAccountButton = new JButton("Сохранить");
        JButton cancelButton = new JButton("Отмена");

        saveAccountButton.setFocusable(false);
        cancelButton.setFocusable(false);

        userLabel.setPreferredSize(labelSize);
        emailLabel.setPreferredSize(labelSize);
        loginLabel.setPreferredSize(labelSize);
        passwordLabel.setPreferredSize(labelSize);
        smtpServerLabel.setPreferredSize(labelSize);
        smtpSaveConnectionLabel.setPreferredSize(labelSize);
        smtpPortLabel.setPreferredSize(labelSize);
        pop3ServerLabel.setPreferredSize(labelSize);
        pop3SaveConnectionLabel.setPreferredSize(labelSize);
        pop3PortLabel.setPreferredSize(labelSize);
        imapServerLabel.setPreferredSize(labelSize);
        imapSaveConnectionLabel.setPreferredSize(labelSize);
        imapPortLabel.setPreferredSize(labelSize);
        saveAccountButton.setPreferredSize(buttonSize);
        cancelButton.setPreferredSize(buttonSize);

        userBox.add(userLabel);
        userBox.add(userTextField);

        emailBox.add(emailLabel);
        emailBox.add(emailTextField);
        emailBox.add(atLabel);
        emailBox.add(emailComboBox);

        loginBox.add(loginLabel);
        loginBox.add(loginTextField);

        passwordBox.add(passwordLabel);
        passwordBox.add(passwordField);

        smtpServerBox.add(smtpServerLabel);
        smtpServerBox.add(smtpServerTextField);

        smtpSaveConnectionBox.add(smtpSaveConnectionLabel);
        smtpSaveConnectionBox.add(smtpSaveConnectionTextField);

        smtpPortBox.add(smtpPortLabel);
        smtpPortBox.add(smtpPortTextField);


        pop3ServerBox.add(pop3ServerLabel);
        pop3ServerBox.add(pop3ServerTextField);

        pop3SaveConnectionBox.add(pop3SaveConnectionLabel);
        pop3SaveConnectionBox.add(pop3SaveConnectionTextField);

        pop3PortBox.add(pop3PortLabel);
        pop3PortBox.add(pop3PortTextField);

        imapServerBox.add(imapServerLabel);
        imapServerBox.add(imapServerTextField);

        imapSaveConnectionBox.add(imapSaveConnectionLabel);
        imapSaveConnectionBox.add(imapSaveConnectionTextField);

        imapPortBox.add(imapPortLabel);
        imapPortBox.add(imapPortTextField);

        bigSmtpBox.add(smtpServerBox);
        bigSmtpBox.add(smtpSaveConnectionBox);
        bigSmtpBox.add(smtpPortBox);
        bigSmtpBox.setBorder(BorderFactory.createTitledBorder("SMTP Сервер"));

        bigPOP3Box.add(pop3ServerBox);
        bigPOP3Box.add(pop3SaveConnectionBox);
        bigPOP3Box.add(pop3PortBox);
        bigPOP3Box.setBorder(BorderFactory.createTitledBorder("POP3 Сервер"));

        bigImapBox.add(imapServerBox);
        bigImapBox.add(imapSaveConnectionBox);
        bigImapBox.add(imapPortBox);
        bigImapBox.setBorder(BorderFactory.createTitledBorder("IMAP Сервер"));

        bigUserBox.add(userBox);
        bigUserBox.add(emailBox);
        bigUserBox.add(loginBox);
        bigUserBox.add(passwordBox);
        bigUserBox.setBorder(BorderFactory.createTitledBorder("Учетная запись"));

        buttonBox.add(saveAccountButton);
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.add(cancelButton);

        buttonBox.setPreferredSize(buttonSize);

        mainBox.add(bigUserBox);
        mainBox.add(Box.createVerticalStrut(15));
        mainBox.add(bigSmtpBox);
        mainBox.add(Box.createVerticalStrut(15));
        mainBox.add(bigPOP3Box);
        mainBox.add(Box.createVerticalStrut(15));
        mainBox.add(bigImapBox);
        mainBox.add(Box.createVerticalStrut(15));
        mainBox.add(buttonBox);
        mainBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        emailComboBox.addActionListener(e -> {
            JComboBox comboBox = (JComboBox) e.getSource();
            String filename = comboBox.getSelectedItem().toString() + ".properties";
            Path rootPath = Paths.get(SendWindow.PROPERTIES_FOLDER);
            Path serversPath = rootPath.resolve("serverSettings");
            Path filePath = serversPath.resolve(filename);
            File file = new File(filePath.toString());

            if (file.exists()) {

                try (InputStream is = new FileInputStream(filePath.toString())) {
                    Reader reader = new InputStreamReader(is);
                    Properties propertiesFromFile = new Properties();
                    propertiesFromFile.load(reader);
                    String smtp_host = propertiesFromFile.getProperty("smtp_host");
                    String smtp_security = propertiesFromFile.getProperty("smtp_security");
                    String smtp_port = propertiesFromFile.getProperty("smtp_port");
                    String pop_host = propertiesFromFile.getProperty("pop_host");
                    String pop_security = propertiesFromFile.getProperty("pop_security");
                    String pop_port = propertiesFromFile.getProperty("pop_port");
                    String imap_host = propertiesFromFile.getProperty("imap_host");
                    String imap_secutity = propertiesFromFile.getProperty("imap_secutity");
                    String imap_port = propertiesFromFile.getProperty("imap_port");

                    smtpServerTextField.setText(smtp_host);
                    smtpSaveConnectionTextField.setText(smtp_security);
                    smtpPortTextField.setText(smtp_port);
                    pop3ServerTextField.setText(pop_host);
                    pop3SaveConnectionTextField.setText(pop_security);
                    pop3PortTextField.setText(pop_port);
                    imapServerTextField.setText(imap_host);
                    imapSaveConnectionTextField.setText(imap_secutity);
                    imapPortTextField.setText(imap_port);


                } catch (FileNotFoundException e1) {
                    JOptionPane.showMessageDialog(AddAccountWindow.this,
                            "Файл настроек " + filename + " не найден!",
                            "Ошибка",

                            JOptionPane.ERROR_MESSAGE
                    );
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(AddAccountWindow.this,
                            "Ошибка при чтении файла " + filename,
                            "Ошибка",

                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        saveAccountButton.addActionListener(e -> {
            String userName = userTextField.getText();
            String from = null;
            if (emailComboBox.getSelectedItem() == null) {
                emailComboBox.grabFocus();
            } else {
                from = emailTextField.getText() + "@" + emailComboBox.getSelectedItem().toString();
            }

            String user = loginTextField.getText();
            StringBuilder builder = new StringBuilder();
            for (char c : passwordField.getPassword()) {
                builder.append(c);
            }
            String pass = builder.toString();
            String smtp_host = smtpServerTextField.getText();
            String smtp_security = smtpSaveConnectionTextField.getText();
            String smtp_port = smtpPortTextField.getText();

            String pop_host = pop3ServerTextField.getText();
            String pop_security = pop3SaveConnectionTextField.getText();
            String pop_port = pop3PortTextField.getText();

            String imap_host = imapServerTextField.getText();
            String imap_security = imapSaveConnectionTextField.getText();
            String imap_port = imapPortTextField.getText();

            if (userName.isEmpty()) userTextField.grabFocus();
            else if (emailTextField.getText().isEmpty()) emailTextField.grabFocus();
            else if (user.isEmpty()) loginTextField.grabFocus();
            else if (pass.isEmpty()) passwordField.grabFocus();
            else if (smtp_host.isEmpty()) smtpServerTextField.grabFocus();
            else if (smtp_security.isEmpty()) smtpSaveConnectionTextField.grabFocus();
            else if (smtp_port.isEmpty()) smtpPortTextField.grabFocus();
            else if (pop_host.isEmpty()) pop3ServerTextField.grabFocus();
            else if (pop_security.isEmpty()) pop3SaveConnectionTextField.grabFocus();
            else if (pop_port.isEmpty()) pop3PortTextField.grabFocus();
            else if (imap_host.isEmpty()) imapServerTextField.grabFocus();
            else if (imap_security.isEmpty()) imapSaveConnectionTextField.grabFocus();
            else if (imap_port.isEmpty()) imapPortTextField.grabFocus();

            else {
                String fileName = SendWindow.PROPERTIES_FOLDER + "//" + userName + ".properties";
                File propFile = new File(fileName);
                Properties props = new Properties();
                props.setProperty("user", user);
                props.setProperty("pass", pass);
                props.setProperty("from", from);

                props.setProperty("smtp_host", smtp_host);
                props.setProperty("smtp_security", smtp_security);
                props.setProperty("smtp_port", smtp_port);

                props.setProperty("pop_host", pop_host);
                props.setProperty("pop_security", pop_security);
                props.setProperty("pop_port", pop_port);

                props.setProperty("imap_host", imap_host);
                props.setProperty("imap_security", imap_security);
                props.setProperty("imap_port", imap_port);

                props.setProperty("reply_to", from);

                try (OutputStream out = new FileOutputStream(fileName)) {
                    props.store(out, "Setting for " + userName);
                    JOptionPane.showMessageDialog(AddAccountWindow.this,
                            "Учетная запись " + userName + " добавлена",
                            "Новая учетная запись",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose();

                } catch (FileNotFoundException e1) {
                    JOptionPane.showMessageDialog(AddAccountWindow.this,
                            "Файл настроек " + fileName + " не найден!",
                            "Ошибка",

                            JOptionPane.ERROR_MESSAGE
                    );
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(AddAccountWindow.this,
                            "Ошибка при записи файла " + fileName,
                            "Ошибка",

                            JOptionPane.ERROR_MESSAGE
                    );
                }

            }

        });
        emailTextField.addCaretListener(e -> {
            String text = emailTextField.getText();
            loginTextField.setText(text);
        });
        cancelButton.addActionListener(e -> dispose());


        add(mainBox);
        //Change Focus when Enter kee pressed
        Set forwardKeys = getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
        Set newForwardKeys = new HashSet(forwardKeys);
        newForwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newForwardKeys);

        setVisible(true);

    }


    private String[] getPropertyFiles() {
        Path rootPath = Paths.get(SendWindow.PROPERTIES_FOLDER);
        Path serversPath = rootPath.resolve("serverSettings");
        System.out.println(serversPath);
        File folder = new File(serversPath.toString());
        return folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".properties");
            }
        });
    }

    private void initEmailComboBox() {
        for (String propertyName : getPropertyFiles()) {
            String element = propertyName.substring(0, propertyName.lastIndexOf('.'));
            emailComboBox.addItem(element);
        }
    }


    public static void main(String[] args) {
        new AddAccountWindow();
    }
}
