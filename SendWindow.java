package emailbrouser;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SendWindow extends JFrame {
    static final String PROPERTIES_FOLDER = "C://emailBrowser";
    private DefaultComboBoxModel<String> toItems = new DefaultComboBoxModel<>();

    private GridBagLayout gbLayout = new GridBagLayout();
    private JComboBox fromComboBox = new JComboBox();
    private JComboBox toComboBox = new JComboBox(toItems);
    private JTextField themField = new JTextField();
    private JTextPane messagePane = new JTextPane();


    private JPanel addingFilePanel = new JPanel();
    private JPanel mainPanel = new JPanel();

    private JScrollPane addFileScroll = new JScrollPane(addingFilePanel);


    private List<JButton> deleteFileButtons = new LinkedList<>();
    private List<JLabel> addFileLabels = new LinkedList<>();
    private List<File> attachedFiles = new LinkedList<>();


    public SendWindow() {
        super("Новое сообщение");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setMaximumSize(new Dimension(1600, 800));

        Font fontForComponents = new Font("Serif", Font.PLAIN, 15);

        JLabel fromLabel = new JLabel("От кого ");
        JLabel toLabel = new JLabel("Кому");
        JLabel themLabel = new JLabel("ТЕМА");

        themLabel.setFont(fontForComponents);
        toLabel.setFont(fontForComponents);
        fromLabel.setFont(fontForComponents);
        themField.setFont(fontForComponents);

        addFileScroll.setAutoscrolls(true);

        messagePane.setComponentPopupMenu(new CopyPastPopupMenu(messagePane));
        themField.setComponentPopupMenu(new CopyPastPopupMenu(themField));

        JButton sendButton = new JButton("Отправить");
        sendButton.setFocusPainted(false);
        JButton cancelButton = new JButton("Отмена");
        cancelButton.setFocusPainted(false);
        JButton addFileButton = new JButton("Прикрепить файл");
        addFileButton.setFocusPainted(false);


        sendButton.setPreferredSize(new Dimension(150, 50));
        cancelButton.setPreferredSize(new Dimension(150, 50));

        cancelButton.addActionListener(e -> dispose());
        sendButton.addActionListener((ActionEvent e) -> {
            List<String> emails = getEmails();
            if (emails == null) {
                JOptionPane.showMessageDialog(
                        SendWindow.this,
                        "Не указано ни одного адреса получателя!",
                        "Ошибка - Пустой адрес", JOptionPane.ERROR_MESSAGE);
            } else if (!isAllEmailsCorrect()) {
                JOptionPane.showMessageDialog(
                        SendWindow.this,
                        "Cледующие адреса введены с ошибками: \n" + getIncorrectEmails(),
                        "Ошибка в имени адресов", JOptionPane.ERROR_MESSAGE);
            } else {

                String text = getPlainText();
                String them = themField.getText();
                String from = fromComboBox.getSelectedItem().toString();
                SendHtmlEmail htmlEmail = new SendHtmlEmail(from, emails, them, attachedFiles, text);


                Thread sendEmailThread = new Thread(new Runnable() {


                    @Override
                    public void run() {
                        boolean isEmailSend = htmlEmail.sendMessage();
                        if (isEmailSend)
                            JOptionPane.showMessageDialog(
                                    SendWindow.this,
                                    "Сообщение успешно отправлено!",
                                    "Отправка сообщения",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        else JOptionPane.showMessageDialog(
                                SendWindow.this,
                                "Сообщение не удалось отправить",
                                "Отправка сообщения",
                                JOptionPane.WARNING_MESSAGE);
                    }
                });
                sendEmailThread.start();
                try (FileWriter writer = new FileWriter(SendWindow.PROPERTIES_FOLDER + "//recently.txt", true);
                     BufferedWriter bfw = new BufferedWriter(writer)) {
                    for (String email : emails) {
                        boolean hasToComboboxRecentAddress = false;
                        for (int i = 0; i < toItems.getSize(); i++) {
                            if (toItems.getElementAt(i).equals(email)) {
                                hasToComboboxRecentAddress = true;
                                break;
                            }
                        }
                        if (!hasToComboboxRecentAddress) {
                            bfw.write(email + "\r\n");
                            toItems.addElement(email);
                        }
                    }
                    bfw.flush();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }


            }


        });
        addFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GridBagConstraints gbc = new GridBagConstraints();
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                int fileChooseResult = fileChooser.showOpenDialog(SendWindow.this);
                if (fileChooseResult == JFileChooser.APPROVE_OPTION) {
                    for (File file : fileChooser.getSelectedFiles()) {
                        if (!attachedFiles.contains(file)) {
                            attachedFiles.add(file);
                            gbc.gridx = GridBagConstraints.RELATIVE;
                            gbc.gridy = GridBagConstraints.RELATIVE;
                            gbc.anchor = GridBagConstraints.EAST;
                            gbc.gridwidth = GridBagConstraints.RELATIVE;
                            gbc.weightx = 10;
                            gbc.insets = new Insets(0, 0, 0, 10);

                            JLabel attachedFileLabel = new JLabel(file.getName());
                            attachedFileLabel.setFont(new Font("Serif", Font.BOLD, 14));
                            attachedFileLabel.setForeground(Color.BLUE);
                            gbLayout.setConstraints(attachedFileLabel, gbc);
                            addFileLabels.add(attachedFileLabel);
                            addingFilePanel.add(attachedFileLabel);


                            JButton deleteFileButton = new JButton() {
                                @Override
                                public void paint(Graphics g) {
                                    super.paint(g);
                                    g.setColor(Color.BLUE);
                                    setHorizontalAlignment(SwingConstants.CENTER);
                                    setVerticalAlignment(SwingConstants.CENTER);
                                    g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                                    g.drawLine(0, 0, getWidth() - 1, getHeight() - 1);
                                    g.drawLine(0, getHeight() - 1, getWidth() - 1, 0);
                                }
                            };
                            deleteFileButton.setPreferredSize(new Dimension(10, 10));
                            deleteFileButton.setFocusable(false);
                            deleteFileButton.addActionListener(e12 -> {
                                JButton pressedButton = (JButton) e12.getSource();
                                int indexOfElement = deleteFileButtons.indexOf(pressedButton);
                                JLabel deletingLabel = addFileLabels.get(indexOfElement);
                                addingFilePanel.remove(pressedButton);
                                addingFilePanel.remove(deletingLabel);
                                deleteFileButtons.remove(indexOfElement);
                                addFileLabels.remove(indexOfElement);
                                attachedFiles.remove(indexOfElement);

                                addFileScroll.validate();
                                addFileScroll.repaint();
                                SendWindow.this.validate();


                            });

                            gbc.gridx = GridBagConstraints.RELATIVE;
                            gbc.weightx = 0;
                            gbc.gridwidth = GridBagConstraints.REMAINDER;
                            gbc.anchor = GridBagConstraints.NORTHEAST;

                            gbLayout.setConstraints(deleteFileButton, gbc);

                            deleteFileButtons.add(deleteFileButton);
                            addingFilePanel.add(deleteFileButton);
                        } else {
                            JOptionPane.showMessageDialog(
                                    SendWindow.this,
                                    "Файл " + file.getName() + " уже прикреплен!",
                                    "Прикрепление файлов", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                    SendWindow.this.validate();

                }

            }
        });

        addingFilePanel.setLayout(gbLayout);


        mainPanel.setLayout(gbLayout);
        initFromComboBox();
        fromComboBox.setEditable(false);
        initToComboBox();
        toComboBox.setSelectedIndex(-1);
        toComboBox.setEditable(true);

        JScrollPane scrollMsgPane = new JScrollPane(messagePane);
        scrollMsgPane.setPreferredSize(new Dimension(10, 10));

        JSplitPane messageAndfilePane = new JSplitPane();


        addFileScroll.setPreferredSize(new Dimension(10, 100));


        addComponentToXY(0, 0, 1, 1, 0, 0, GridBagConstraints.NONE, fromLabel);
        alignComponent(fromLabel, GridBagConstraints.NORTHWEST);
        addComponentToXY(1, 0, 5, 1, 2, 0, GridBagConstraints.HORIZONTAL, fromComboBox);
        setBottomGap(fromComboBox, 10);
        addComponentToXY(0, 1, 1, 1, 0, 0, GridBagConstraints.NONE, toLabel);
        alignComponent(toLabel, GridBagConstraints.NORTHWEST);
        addComponentToXY(1, 1, 5, 1, 2, 0, GridBagConstraints.HORIZONTAL, toComboBox);
        setBottomGap(toComboBox, 20);
        addComponentToXY(0, 3, 1, 1, 0, 0, GridBagConstraints.NONE, themLabel);
        alignComponent(themLabel, GridBagConstraints.NORTHWEST);
        addComponentToXY(1, 3, 5, 1, 2, 0, GridBagConstraints.HORIZONTAL, themField);
        setBottomGap(themField, 10);
        JToolBar editTextToolbar = new EditTextToolbar(messagePane);
        addComponentToXY(0, 4, 6, 1, 2, 0, GridBagConstraints.HORIZONTAL, editTextToolbar);
        addComponentToXY(0, 5, 6, 8, 2, 4, GridBagConstraints.BOTH, scrollMsgPane);
        addComponentToXY(2, 13, 1, 1, 1, 0, GridBagConstraints.NONE, addFileButton);
        setBottomGap(addFileButton, 10);
        alignComponent(addFileButton, GridBagConstraints.EAST);
        addComponentToXY(0, 14, 6, 3, 1, 0, GridBagConstraints.HORIZONTAL, addFileScroll);
        setBottomGap(addingFilePanel, 10);
        addComponentToXY(0, 18, 2, 1, 0, 0, GridBagConstraints.NONE, sendButton);
        alignComponent(sendButton, GridBagConstraints.WEST);
        addComponentToXY(2, 18, 2, 1, 0, 0, GridBagConstraints.NONE, cancelButton);
        alignComponent(cancelButton, GridBagConstraints.EAST);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        setContentPane(mainPanel);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    private void addComponentToXY(int x, int y, int width, int height, int weightX, int weightY, int fill, JComponent component) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = fill;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = weightX;
        gbc.weighty = weightY;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbLayout.setConstraints(component, gbc);
        mainPanel.add(component);
    }

    private void alignComponent(JComponent component, int alignment) {
        GridBagConstraints gbc = gbLayout.getConstraints(component);
        gbc.anchor = alignment;
        gbLayout.setConstraints(component, gbc);
    }

    private void setBottomGap(JComponent component, int gap) {
        GridBagConstraints gbc = gbLayout.getConstraints(component);
        gbc.insets = new Insets(0, 0, gap, 0);
        gbLayout.setConstraints(component, gbc);
    }

    private List<String> getEmails() {
        ArrayList<String> emails = new ArrayList<>();
        Object selectedItem = toComboBox.getSelectedItem();
        if (selectedItem == null) return null;
        String text = toComboBox.getSelectedItem().toString();
        text = text.trim();
        if (text.equals("")) return null;
        String[] emailArray = text.split("([\\s,;:]+)");

        Collections.addAll(emails, emailArray);

        if (emails.isEmpty()) return null;
        else return emails;
    }

    private List<String> getIncorrectEmails() {
        List<String> emails = getEmails();
        if (emails != null) {
            ArrayList<String> incorrectEmails = new ArrayList<>();
            for (String email : emails) {
                if (!isEmailCorrect(email)) incorrectEmails.add(email);
            }
            if (!incorrectEmails.isEmpty()) return incorrectEmails;

        }
        return null;
    }

    private boolean isEmailCorrect(String email) {

        return email.matches("^([\\w\\d-_]+)@([\\w\\d-_]+)\\.([\\w\\d-_]+)");
    }

    private boolean isAllEmailsCorrect() {
        return getIncorrectEmails() == null;

    }

    private String getPlainText() {
        String result = null;//строка результата
        HTMLDocument document = (HTMLDocument) messagePane.getDocument();
        try (StringWriter stringWriter = new StringWriter()) {//Буфер для записи строки
            HTMLEditorKit htmlEditorKit = new HTMLEditorKit();//Штука для работы с HTML текстом
            try {
                htmlEditorKit.write(stringWriter, document, 0, document.getLength());//запись в StringWriter документа от
                //начала и до конца
                result = stringWriter.toString();//получение резальтата
            } catch (IOException | BadLocationException e) {
                JOptionPane.showMessageDialog(SendWindow.this,
                        e.getMessage(),
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE

                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(SendWindow.this,
                    e.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE

            );
        }
        //возвращаем строку
        return result;
    }

    private String[] getPropertyFiles() {
        File folder = new File(PROPERTIES_FOLDER);
        return folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".properties");
            }
        });
    }

    private void initFromComboBox() {
        for (String propertyName : getPropertyFiles()) {
            String element = propertyName.substring(0, propertyName.lastIndexOf('.'));
            fromComboBox.addItem(element);
        }
    }

    private void initToComboBox() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SendWindow.PROPERTIES_FOLDER + "//recently.txt"))) {
            while (reader.ready()) {
                String recentAddress = reader.readLine();
                toItems.addElement(recentAddress);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SendWindow();
    }
}
