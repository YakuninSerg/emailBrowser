package emailbrouser;

import emailbrouser.actions.ChangeFontAction;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.util.Vector;

public class EditTextToolbar extends JToolBar {

    private Color fontColor;

    private JTextPane textPane;

    private HTMLDocument document = new HTMLDocument();

    private JToggleButton boldButton = new JToggleButton();
    private JToggleButton italicButton = new JToggleButton("");
    private JToggleButton underLineButton = new JToggleButton("") {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawLine(7, getHeight() - 7, getWidth() - 7, getHeight() - 7);

        }
    };
    private JLabel fontLabel = new JLabel("Шрифт  ");
    private JComboBox fontsCombobox = allAvailableFontsComboBox();
    private JComboBox fontSizeCombobox = getFontSizeCombobox();
    private JColorChooser fontColorChooser = new JColorChooser();
    private JToggleButton leftAlignButton = new JToggleButton() {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            int lineGap = (getHeight() - 15) / 4;
            int widthGap = (getWidth() - 10) / 4;
            int x0 = 2;
            int y0 = 7;

            g.drawLine(x0, y0, x0 + 3 * widthGap, y0);
            g.drawLine(x0, y0 + lineGap, x0 + 2 * widthGap, y0 + lineGap);
            g.drawLine(x0, y0 + 2 * lineGap, x0 + widthGap, y0 + 2 * lineGap);
            g.drawLine(x0, y0 + 3 * lineGap, x0 + 2 * widthGap, y0 + 3 * lineGap);
            g.drawLine(x0, y0 + 4 * lineGap, x0 + 3 * widthGap, y0 + 4 * lineGap);
        }
    };
    private JToggleButton centerAlignButton = new JToggleButton() {
        @Override
        public void paint(Graphics g) {
            super.paint(g);

            int lineGap = (getHeight() - 15) / 4;
            int widthGap = (getWidth() - 10) / 6;
            int x0 = 6;
            int y0 = 7;

            g.drawLine(x0, y0, getWidth() - x0 - 1, y0);
            g.drawLine(x0 + widthGap, y0 + lineGap, getWidth() - (x0 + widthGap + 1), y0 + lineGap);
            g.drawLine(x0 + 2 * widthGap, y0 + 2 * lineGap, getWidth() - (x0 + 2 * widthGap + 1), y0 + 2 * lineGap);
            g.drawLine(x0 + widthGap, y0 + 3 * lineGap, getWidth() - (x0 + widthGap + 1), y0 + 3 * lineGap);
            g.drawLine(x0, y0 + 4 * lineGap, getWidth() - x0 - 1, y0 + 4 * lineGap);
        }
    };
    private JToggleButton rightAlignButton = new JToggleButton() {
        @Override
        public void paint(Graphics g) {
            super.paint(g);

            int lineGap = (getHeight() - 15) / 4;
            int widthGap = (getWidth() - 10) / 4;
            int x0 = 2;
            int y0 = 7;

            g.drawLine(x0 + 2 * widthGap - 2, y0, getWidth() - 5, y0);
            g.drawLine(x0 + 3 * widthGap - 2, y0 + lineGap, getWidth() - 5, y0 + lineGap);
            g.drawLine(x0 + 4 * widthGap - 2, y0 + 2 * lineGap, getWidth() - 5, y0 + 2 * lineGap);
            g.drawLine(x0 + 3 * widthGap - 2, y0 + 3 * lineGap, getWidth() - 5, y0 + 3 * lineGap);
            g.drawLine(x0 + 2 * widthGap - 2, y0 + 4 * lineGap, getWidth() - 5, y0 + 4 * lineGap);
        }
    };

    private JButton colorButton = new JButton("      ") {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(fontColor);
            g.fillRect(5, 5, getWidth() - 11, getHeight() - 10);
        }
    };


    private ButtonGroup groupOfAlignButton = new ButtonGroup();


    public EditTextToolbar(JTextPane txtPane) {
        this.textPane = txtPane;
        textPane.setComponentPopupMenu(new CopyPastPopupMenu(textPane));
        textPane.setContentType("text/html");

        // HTMLEditorKit htmlKit = new HTMLEditorKit();
        //HTMLDocument document = (HTMLDocument) htmlKit.createDefaultDocument();//создаем новый HTML документ по умолчанию
        textPane.setDocument(document);
        textPane.addCaretListener(e -> setButtonsForStyle());

        MutableAttributeSet attributeSet = textPane.getInputAttributes();
        StyleConstants.setForeground(attributeSet, Color.BLACK);
        StyleConstants.setFontFamily(attributeSet, "Times New Roman");
        StyleConstants.setFontSize(attributeSet, 18);


        setFloatable(false);
        fontColor = Color.BLACK;
        groupOfAlignButton.add(leftAlignButton);
        groupOfAlignButton.add(centerAlignButton);
        groupOfAlignButton.add(rightAlignButton);
        boldButton.setFocusable(false);
        italicButton.setFocusable(false);
        underLineButton.setFocusable(false);
        leftAlignButton.setFocusable(false);
        centerAlignButton.setFocusable(false);
        rightAlignButton.setFocusable(false);
        colorButton.setFocusable(false);

        boldButton.setFont(new Font("Serif", Font.BOLD, 14));
        italicButton.setFont(new Font("Serif", Font.ITALIC, 14));
        underLineButton.setFont(new Font("Serif", Font.PLAIN, 14));

        boldButton.setHideActionText(true);
        italicButton.setHideActionText(true);
        underLineButton.setHideActionText(true);
        leftAlignButton.setHideActionText(true);
        centerAlignButton.setHideActionText(true);
        rightAlignButton.setHideActionText(true);

        boldButton.setAction(new StyledEditorKit.BoldAction());
        italicButton.setAction(new StyledEditorKit.ItalicAction());
        underLineButton.setAction(new StyledEditorKit.UnderlineAction());
        leftAlignButton.setAction(new StyledEditorKit.AlignmentAction("По левому краю", StyleConstants.ALIGN_LEFT));
        centerAlignButton.setAction(new StyledEditorKit.AlignmentAction("По центру", StyleConstants.ALIGN_CENTER));
        rightAlignButton.setAction(new StyledEditorKit.AlignmentAction("По правому краю", StyleConstants.ALIGN_RIGHT));

        boldButton.setText(" Ж ");
        italicButton.setText(" К ");
        underLineButton.setText(" Ч ");
        leftAlignButton.setText("      ");
        centerAlignButton.setText("      ");
        rightAlignButton.setText("      ");

        boldButton.setMnemonic('b');
        italicButton.setMnemonic('i');
        underLineButton.setMnemonic('u');


        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(EditTextToolbar.this, "Цвет", fontColor);
            if (newColor != null) fontColor = newColor;
            Action action = new StyledEditorKit.ForegroundAction(fontColor.toString(), fontColor);
            action.actionPerformed(e);


        });


        add(fontLabel);
        add(fontsCombobox);
        add(boldButton);
        add(italicButton);
        add(underLineButton);
        add(fontSizeCombobox);

        //add(fontColor);
        add(leftAlignButton);
        add(centerAlignButton);
        add(rightAlignButton);
        add(colorButton);


    }

    private JComboBox allAvailableFontsComboBox() {


        GraphicsEnvironment gEnv = GraphicsEnvironment

                .getLocalGraphicsEnvironment();

        String envfonts[] = gEnv.getAvailableFontFamilyNames();

        Vector vector = new Vector();

        for (String envfont : envfonts) {

            vector.addElement(envfont);

        }

        JComboBox result = new JComboBox(vector);
        result.setSelectedItem("Times New Roman");
        result.addActionListener(e -> {

            Action action = new ChangeFontAction(textPane);
            action.actionPerformed(e);
        });


        result.setFocusable(false);
        return result;


    }

    private JComboBox getFontSizeCombobox() {

        String[] fontSizes = {"8", "10", "12", "14", "18", "24", "36"};
        JComboBox result = new JComboBox(fontSizes);
        result.setFocusable(false);
        result.addActionListener(e -> {
            JComboBox comboBox = (JComboBox) e.getSource();
            String sizeStr = comboBox.getSelectedItem().toString();
            int size = Integer.parseInt(sizeStr);
            Action action = new StyledEditorKit.FontSizeAction(sizeStr, size);
            action.actionPerformed(e);
        });
        result.setSelectedItem("18");
        return result;
    }

    private void setButtonsForStyle() {
        if (document == null) return;

        MutableAttributeSet inputAttributes = textPane.getInputAttributes();
        AttributeSet currentAttributes = textPane.getCharacterAttributes();


        if (currentAttributes != null && inputAttributes != null) {

            boldButton.setSelected(StyleConstants.isBold(inputAttributes));
            italicButton.setSelected(StyleConstants.isItalic(inputAttributes));
            underLineButton.setSelected(StyleConstants.isUnderline(inputAttributes));

            boolean isRightAlignment = StyleConstants.getAlignment(currentAttributes) == StyleConstants.ALIGN_RIGHT;
            boolean isLeftAlignment = StyleConstants.getAlignment(currentAttributes) == StyleConstants.ALIGN_LEFT;
            boolean isCenterAlignment = StyleConstants.getAlignment(currentAttributes) == StyleConstants.ALIGN_CENTER;

            rightAlignButton.setSelected(isRightAlignment);
            leftAlignButton.setSelected(isLeftAlignment);
            centerAlignButton.setSelected(isCenterAlignment);

            String font = StyleConstants.getFontFamily(inputAttributes);
            fontsCombobox.setSelectedItem(font);
            int size = StyleConstants.getFontSize(inputAttributes);
            fontSizeCombobox.setSelectedItem(Integer.toString(size));

            fontColor = StyleConstants.getForeground(inputAttributes);
            colorButton.validate();
            colorButton.repaint();
        }
    }


}
