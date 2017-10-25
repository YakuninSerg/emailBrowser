package emailbrouser;

import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecieveTableModel extends AbstractTableModel {
    private String account;
    private String[] files;

    RecieveTableModel(String account) {
        this.account = account;
        this.files = getMessageFiles(account);
    }

    @Override
    public int getRowCount() {
        if (files == null) return 4;
        return files.length;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (files != null) {
            String fileName = files[rowIndex];
            SimpleDateFormat f = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            PopEmailBeen emailBeen = getEmailFromFile(fileName);
            try {
                Date date;
                date = f.parse(emailBeen.getDate());

                if (columnIndex == 0) return emailBeen.getFrom();
                if (columnIndex == 1) return emailBeen.getThema();
                if (columnIndex == 2) return date;
                if (columnIndex == 3) {
                    if (emailBeen.getAttachedFiles() == null) return " ";
                    else return "!!!";
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) return "От кого";
        if (column == 1) return "Тема сообщения";
        if (column == 2) return "Дата";
        if (column == 3) return "Файлы";
        return null;

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    private String[] getMessageFiles(String account) {
        String[] files;
        String path = SendWindow.PROPERTIES_FOLDER + "//" + account + "//recieved";
        File folder = new File(path);
        files = folder.list((dir, name) -> name.endsWith(".msg"));
        return files;
    }

    PopEmailBeen getEmailFromFile(String file) {
        PopEmailBeen result = null;
        String fileName = SendWindow.PROPERTIES_FOLDER + "//" + account + "//recieved//" + file;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            result = (PopEmailBeen) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    String[] getFiles() {
        return files;
    }
}