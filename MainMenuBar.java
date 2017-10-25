package emailbrouser;

import javax.swing.*;

public class MainMenuBar extends JMenuBar {
    MainMenuBar() {


        JMenu fileMenu = new JMenu("Файл");
        JMenuItem openFileItem = new JMenuItem("Открыть");
        JMenuItem saveFileItem = new JMenuItem("Сохранить");
        JMenuItem saveFileAsItem = new JMenuItem("Сохранить как...");
        JMenuItem printFileItem = new JMenuItem("Печать");
        JMenuItem exitItem = new JMenuItem("Выход");
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(openFileItem);
        fileMenu.add(saveFileItem);
        fileMenu.add(saveFileAsItem);
        fileMenu.add(printFileItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu messageMenu = new JMenu("Сообщение");
        JMenuItem createMessageItem = new JMenuItem("Новое сообщение");
        createMessageItem.addActionListener(e -> new SendWindow());
        JMenuItem replyMessageItem = new JMenuItem("Ответить");
        JMenuItem resendMessageItem = new JMenuItem("Переслать");
        JMenuItem resendToManyMessageItem = new JMenuItem("Переслать нескольким");

        messageMenu.add(createMessageItem);
        messageMenu.add(replyMessageItem);
        messageMenu.add(resendMessageItem);
        messageMenu.add(resendToManyMessageItem);

        JMenu sendAndRecieveMenu = new JMenu("Отправить и получить");
        JMenuItem sendItem = new JMenuItem("Отправить почту");
        JMenuItem recieveItem = new JMenuItem("Получить почту");

        sendAndRecieveMenu.add(sendItem);
        sendAndRecieveMenu.add(recieveItem);

        JMenu settingsMenu = new JMenu("Настройки");
        JMenuItem accauntItem = new JMenuItem("Учетные записи");
        accauntItem.addActionListener(e -> new AccountWindow());


        JMenuItem safeItem = new JMenuItem("Настройки безопасности");

        settingsMenu.add(accauntItem);
        settingsMenu.add(safeItem);


        add(fileMenu);
        add(messageMenu);
        add(sendAndRecieveMenu);
        add(settingsMenu);


    }
}
