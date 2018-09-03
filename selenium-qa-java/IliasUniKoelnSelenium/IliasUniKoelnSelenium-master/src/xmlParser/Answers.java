package xmlParser;

import java.util.List;

/**
 * Created by EPC-Nils on 15.04.2016.
 */
public class Answers {

    private String itemTitle;
    private List<Answers> responses;
    private String varequalRespident;
    private String varequalRespidentinline;
    private String setvarActionInline;

    public boolean isVarequalRespidentNot() {
        return varequalRespidentNot;
    }

    public void setVarequalRespidentNot(boolean varequalRespidentNot) {
        this.varequalRespidentNot = varequalRespidentNot;
    }

    private boolean varequalRespidentNot;

    public Answers(String itemTitle, List<Answers> responses) {

        this.itemTitle = itemTitle;
        this.responses = responses;

    }

    public Answers(String varequalRespident, String varequalRespidentInline, String setvarActionInline, boolean varequalRespidentNot) {
        this.varequalRespident = varequalRespident;
        this.varequalRespidentNot = varequalRespidentNot;
        this.varequalRespidentinline = varequalRespidentInline;
        this.setvarActionInline = setvarActionInline;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public List<Answers> getResponses() {
        return responses;
    }

    public void setResponses(List<Answers> responses) {
        this.responses = responses;
    }

    public String getVarequalRespident() {
        return varequalRespident;
    }

    public void setVarequalRespident(String varequalRespident) {
        this.varequalRespident = varequalRespident;
    }

    public String getVarequalRespidentinline() {
        return varequalRespidentinline;
    }

    public void setVarequalRespidentinline(String varequalRespidentinline) {
        this.varequalRespidentinline = varequalRespidentinline;
    }

    public String getSetvarActionInline() {
        return setvarActionInline;
    }

    public void setSetvarActionInline(String setvarActionInline) {
        this.setvarActionInline = setvarActionInline;
    }


}
