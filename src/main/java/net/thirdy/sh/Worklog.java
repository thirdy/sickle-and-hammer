/**
 * 
 */
package net.thirdy.sh;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author vicente.rivera
 */
public class Worklog {

    private String jiraTicket;
    private String timeSpent;
    private Date startDateTime;
    private String comment;

    // "2014-06-08T00:00:00.000+0800"
    private static final String JIRA_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.mmmZ";

    /**
     * @param jiraTicket
     * @param timeSpent
     * @param startDateTime
     * @param comment
     */
    public Worklog(String jiraTicket, String timeSpent, Date startDateTime, String comment) {
        super();
        this.jiraTicket = jiraTicket;
        this.timeSpent = formatTimeSpent(timeSpent);
        this.startDateTime = startDateTime;
        this.comment = comment;
    }

    /**
     * Unit test only
     */
    Worklog() {
    }

    /**
     * JIRA uses format of "Xh Ym", if user enters 1h30m, we should parse this into "1h 30m"
     * 
     * @param timeSpent
     * @return
     */
    String formatTimeSpent(String timeSpent) {
        return timeSpent.replace("h", "h ").trim();
    }

    public Worklog(String jiraTicket, String timeSpent, Date startDateTime) {
        this(jiraTicket, timeSpent, startDateTime, null);
    }

    public String getJiraTicket() {
        return jiraTicket;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public String getStartDateTimeFormatAsJiraString() {
        return new SimpleDateFormat(JIRA_DATE_TIME_FORMAT).format(startDateTime);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Worklog [comment=");
        builder.append(comment);
        builder.append(", jiraTicket=");
        builder.append(jiraTicket);
        builder.append(", startDateTime=");
        builder.append(startDateTime);
        builder.append(", startDateTimeFormatAsJiraString=");
        builder.append(getStartDateTimeFormatAsJiraString());
        builder.append(", timeSpent=");
        builder.append(timeSpent);
        builder.append("]");
        return builder.toString();
    }

    public String toReadableString() {
        return jiraTicket + " " + timeSpent + " " + getStartDateTimeFormatAsJiraString().substring(0, 10);
    }

}
