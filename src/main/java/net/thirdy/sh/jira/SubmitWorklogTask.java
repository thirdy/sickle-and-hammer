/**
 * 
 */
package net.thirdy.sh.jira;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingWorker;

import net.thirdy.sh.Worklog;
import net.thirdy.sh.setting.Credential;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

/**
 * @author vicente.rivera
 */
public abstract class SubmitWorklogTask extends SwingWorker<Void, Void> {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private List<Worklog> worklogs;
    private String username;
    private String password;
    private String jiraServerHostname;

    public SubmitWorklogTask(List<Worklog> worklogs, Credential credential, String jiraServerHostname) {
        super();
        this.worklogs = worklogs;
        this.username = credential.getUsername();
        this.password = credential.getPassword();
        this.jiraServerHostname = jiraServerHostname;
    }

    public String callJiraRESTWorklogPOST(Worklog worklog) throws Exception {
        byte[] base64EncodedUsernamePassword = Base64
                .encodeBase64(String.valueOf(username + ":" + password).getBytes());

        // TODO, make this more readable, possibly use JSON library

        String commentClause = worklog.getComment() != null ? ", \"comment\":\"" + worklog.getComment() + "\"" : "";

        String url = "https://" + jiraServerHostname + "/rest/api/2/issue/" + worklog.getJiraTicket() + "/worklog";
        HttpPost postMethod = new HttpPost(url);
        postMethod.addHeader("Content-type", "application/json");
        postMethod.addHeader("charset", "utf-8");
        postMethod.addHeader("Authorization", "Basic " + new String(base64EncodedUsernamePassword));

        final String jsonStr = "{\"timeSpent\":\"" + worklog.getTimeSpent() + "\", \"started\":\""
                + worklog.getStartDateTimeFormatAsJiraString() + "\"" + commentClause + "}";
        logger.finest("jsonStr: " + jsonStr);

        StringEntity json = new StringEntity(jsonStr, "application/json", "UTF-8");
        postMethod.setEntity(json);

        String response = CustomHttpClient.doHttpMethod(postMethod);

        return response;
    }

    @Override
    protected Void doInBackground() {
        for (Worklog worklog : worklogs) {
            try {
                callJiraRESTWorklogPOST(worklog);
                logger.info("Sucessfully submitted worklog: " + worklog.toReadableString());
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return null;
    }

    public abstract void done();
}
