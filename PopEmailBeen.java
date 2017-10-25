package emailbrouser;

import java.io.Serializable;
import java.util.ArrayList;

public class PopEmailBeen implements Serializable {
    private String text;
    private String thema;
    private String date;
    private String from;
    private String to;
    private ArrayList<String> attachedFiles;
    private boolean isNew;
    private int msgId;

    PopEmailBeen(String text, String thema, String date, String from, String to,
                 ArrayList<String> attachedFiles, boolean isNew, int msgId) {
        this.text = text;
        this.thema = thema;
        this.date = date;
        this.from = from;
        this.to = to;
        this.attachedFiles = attachedFiles;
        this.isNew = isNew;
        this.msgId = msgId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    String getThema() {
        return thema;
    }

    public void setThema(String thema) {
        this.thema = thema;
    }

    String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    ArrayList<String> getAttachedFiles() {
        return attachedFiles;
    }

    void setAttachedFiles(ArrayList<String> attachedFiles) {
        this.attachedFiles = new ArrayList<>(attachedFiles);
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }


}
