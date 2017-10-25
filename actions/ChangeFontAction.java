package emailbrouser.actions;

import javax.swing.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import java.awt.event.ActionEvent;

public class ChangeFontAction extends StyledEditorKit.StyledTextAction {
    private JEditorPane editor;

    public ChangeFontAction(JTextPane editor) {
        super(StyleConstants.Family.toString());
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JComboBox comboBox = (JComboBox) actionEvent.getSource();
        String font = comboBox.getSelectedItem().toString();
        StyledEditorKit kit = getStyledEditorKit(editor);
        MutableAttributeSet attr = kit.getInputAttributes();
        SimpleAttributeSet sas = new SimpleAttributeSet();
        StyleConstants.setFontFamily(sas, font);
        setCharacterAttributes(editor, sas, false);


    }
}
