package emailbrouser.actions;

import javax.swing.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import java.awt.event.ActionEvent;

public class ChangeFontSizeAction extends StyledEditorKit.StyledTextAction {

    public ChangeFontSizeAction() {
        super(StyleConstants.FontSize.toString());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JEditorPane editor = getEditor(actionEvent);
        JComboBox comboBox = (JComboBox) actionEvent.getSource();
        String sizeStr = comboBox.getSelectedItem().toString();
        int size = Integer.parseInt(sizeStr);
        if (editor != null) {
            MutableAttributeSet mutableAttributeSet = getStyledEditorKit(editor).getInputAttributes();
            SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
            StyleConstants.setFontSize(simpleAttributeSet, size);
            setCharacterAttributes(editor, simpleAttributeSet, false);
        }
    }
}