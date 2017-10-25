package emailbrouser;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.Comparator;
import java.util.Date;

public class EmailBrowser extends JFrame {
    private JTable emailTable;
    private JTextPane emailPane;
    private JTree emailTree;


    private EmailBrowser() {
        super("EmailBrowser");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 20));


        MainMenuBar mainMenu = new MainMenuBar();
        setJMenuBar(mainMenu);
        HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
        HTMLDocument document = (HTMLDocument) htmlEditorKit.createDefaultDocument();
        emailPane = new JTextPane(document);


        RecieveTableModel tableModel = new RecieveTableModel("79031587670");
        emailTable = new JTable(tableModel);

        TableRowSorter<RecieveTableModel> sorter = new TableRowSorter<>(tableModel);
        sorter.setComparator(2, (Comparator<Date>) (o1, o2) -> {
            if (o1.equals(o2)) return 0;
            else if (o1.before(o2)) return -1;
            else return 1;


        });
        emailTable.setRowSorter(sorter);

        emailTable.getSelectionModel().addListSelectionListener(e -> {
            int row = emailTable.getSelectedRow();
            System.out.println(row);
            String fileName = tableModel.getFiles()[row];
            PopEmailBeen popEmailBeen = tableModel.getEmailFromFile(fileName);
            System.out.println(popEmailBeen.getText());


        });

        emailTable.getColumn("Тема сообщения").setPreferredWidth(220);


        JScrollPane tableScrollPane = new JScrollPane(emailTable);
        JScrollPane textScrollPane = new JScrollPane(emailPane);


        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false);
        splitPane.setTopComponent(tableScrollPane);
        splitPane.setBottomComponent(textScrollPane);
        splitPane.setDividerLocation(150);
        splitPane.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));

        DefaultTreeCellRenderer rendererBold = new DefaultTreeCellRenderer();
        DefaultTreeCellRenderer rendererPlain = new DefaultTreeCellRenderer();
        rendererBold.setFont(new Font(Font.SERIF, Font.BOLD, 14));
        rendererPlain.setFont(new Font(Font.SERIF, Font.PLAIN, 14));


        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Accounts");
        DefaultMutableTreeNode account1 = new DefaultMutableTreeNode("brokerkem@yandex.ru");
        DefaultMutableTreeNode inputMail = new DefaultMutableTreeNode("Входящие");
        DefaultMutableTreeNode sentMail = new DefaultMutableTreeNode("Отправленные");
        DefaultMutableTreeNode outputMail = new DefaultMutableTreeNode("Исходящие");
        DefaultMutableTreeNode draftMail = new DefaultMutableTreeNode("Черновики");
        DefaultMutableTreeNode deleteMail = new DefaultMutableTreeNode("Удаленные");
        DefaultMutableTreeNode spam = new DefaultMutableTreeNode("Спам");


        account1.add(inputMail);
        account1.add(outputMail);
        account1.add(sentMail);
        account1.add(draftMail);
        account1.add(spam);
        account1.add(deleteMail);


        root.add(account1);


        emailTree = new JTree(root);
        emailTree.setCellRenderer(rendererPlain);


        emailTree.setRootVisible(false);
        emailTree.setEditable(false);
        TreeSelectionModel treeSelectionModel = emailTree.getSelectionModel();
        treeSelectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        JScrollPane treeScrollPane = new JScrollPane(emailTree);
        treeScrollPane.setBackground(Color.white);
        treeScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 0));


        add(treeScrollPane, BorderLayout.WEST);
        add(splitPane, BorderLayout.CENTER);


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmailBrowser());
    }
}
