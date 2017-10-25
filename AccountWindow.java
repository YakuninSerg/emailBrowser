package emailbrouser;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AccountWindow extends JFrame {
    private static final String PATH = SendWindow.PROPERTIES_FOLDER;
    private JList<String> accountList;


    AccountWindow() {
        super("Учетные записи");
        setSize(350, 350);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Box buttonVerticalBox = Box.createVerticalBox();
        Box buttonHorizontalBox = Box.createHorizontalBox();
        Box bigHorizontalBox = Box.createHorizontalBox();
        Box mainBox = Box.createVerticalBox();

        initAccountList();
        JScrollPane scroller = new JScrollPane(accountList);

        Dimension buttonsSize = new Dimension(90, 50);

        JButton addAccountButton = new JButton("Добавить");
        JButton editAccountButton = new JButton("Изменить");
        JButton deleteAccountButton = new JButton(" Удалить ");

        JButton okButton = new JButton("OK");
        JButton exitButton = new JButton("Выход");

        addAccountButton.addActionListener(e -> {
            new AddAccountWindow();
            dispose();


        });
        editAccountButton.addActionListener(e -> {
            int numAccount = accountList.getSelectedIndex();
            if (numAccount == -1) return;
            DefaultListModel listModel = (DefaultListModel) accountList.getModel();
            String account = accountList.getSelectedValue().toString();
            new EditAccountWindow(account);


        });
        deleteAccountButton.addActionListener(e -> {
            int numAccount = accountList.getSelectedIndex();
            if (numAccount == -1) return;
            DefaultListModel listModel = (DefaultListModel) accountList.getModel();
            String account = accountList.getSelectedValue();
            String accountFile = PATH + "//" + account + ".properties";
            int areYouSure = JOptionPane.showConfirmDialog(
                    AccountWindow.this,
                    "Вы действительно хотите удалить аккаунт " + account + " ?",
                    "Удаление аккаунта",
                    JOptionPane.YES_NO_OPTION);
            if (areYouSure == JOptionPane.YES_OPTION) {
                listModel.remove(numAccount);
                try {
                    boolean fileWasDelete = Files.deleteIfExists(Paths.get(accountFile));
                    if (!fileWasDelete) {
                        JOptionPane.showMessageDialog(
                                AccountWindow.this,
                                "Файл учетной записи " + account + " не существует!",
                                "Ошибка",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }

                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(
                            AccountWindow.this,
                            "Ошибка при попытке удалить файл :\n" + accountFile,
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }

        });
        okButton.addActionListener(e -> dispose());
        exitButton.addActionListener(e -> dispose());

        addAccountButton.setPreferredSize(buttonsSize);
        editAccountButton.setPreferredSize(buttonsSize);
        deleteAccountButton.setPreferredSize(buttonsSize);

        addAccountButton.setFocusable(false);
        editAccountButton.setFocusable(false);
        deleteAccountButton.setFocusable(false);
        okButton.setFocusable(false);
        exitButton.setFocusable(false);

        buttonVerticalBox.add(addAccountButton);
        buttonVerticalBox.add(Box.createVerticalGlue());
        buttonVerticalBox.add(editAccountButton);
        buttonVerticalBox.add(Box.createVerticalGlue());
        buttonVerticalBox.add(deleteAccountButton);

        buttonHorizontalBox.add(okButton);
        buttonHorizontalBox.add(Box.createHorizontalGlue());
        buttonHorizontalBox.add(exitButton);

        bigHorizontalBox.add(scroller);
        bigHorizontalBox.add(Box.createHorizontalStrut(10));
        bigHorizontalBox.add(buttonVerticalBox);

        mainBox.add(bigHorizontalBox);
        mainBox.add(Box.createVerticalGlue());
        mainBox.add(buttonHorizontalBox);

        mainBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(mainBox);

        setVisible(true);

    }


    public static void main(String[] args) {
        new AccountWindow();
    }

    private String[] getPropertyFiles() {
        File folder = new File(PATH);
        String[] files = folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".properties");
            }
        });
        return files;
    }

    private void initAccountList() {
        DefaultListModel listModel = new DefaultListModel();
        accountList = new JList<>(listModel);
        for (String propertyName : getPropertyFiles()) {
            String element = propertyName.substring(0, propertyName.lastIndexOf('.'));
            listModel.addElement(element);
        }

    }
}
