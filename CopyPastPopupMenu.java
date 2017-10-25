package emailbrouser;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public class CopyPastPopupMenu extends JPopupMenu {
    private JTextComponent textComponent;
    private JMenuItem copyMenuItem = new JMenuItem("Копировать");
    private JMenuItem cutMenuItem = new JMenuItem("Вырезать");
    private JMenuItem pasteMenuItem = new JMenuItem("Вставить");
    private JMenuItem selectAllMenuItem = new JMenuItem("Выделить все");

    public CopyPastPopupMenu(JTextComponent txtComponent) {
        this.textComponent = txtComponent;
        copyMenuItem.addActionListener(e -> textComponent.copy());
        cutMenuItem.addActionListener(e -> textComponent.cut());
        pasteMenuItem.addActionListener(e -> textComponent.paste());
        selectAllMenuItem.addActionListener(e -> textComponent.selectAll());
        copyMenuItem.setMnemonic('c');
        cutMenuItem.setMnemonic('x');
        pasteMenuItem.setMnemonic('v');
        selectAllMenuItem.setMnemonic('a');
        add(copyMenuItem);
        add(pasteMenuItem);
        add(cutMenuItem);
        add(selectAllMenuItem);

    }

}
