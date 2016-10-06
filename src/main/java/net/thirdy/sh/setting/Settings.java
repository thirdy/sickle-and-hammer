package net.thirdy.sh.setting;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public final class Settings {

    private final static Logger logger = Logger.getLogger(Settings.class.getName());

    public static final String KEY_JIRA_HOST_NAME = "key_jiraHostName";

    public static final String PARTIAL_KEY_WORKLOG_SHEET = "key_worklogSheet";

    public static Credential getCredential() {
        Preferences prefs = Preferences.userNodeForPackage(Settings.class);

        Credential credential = null;
        final Object object = getObject(prefs, Credential.class.getName());
        credential = object != null ? (Credential) object : Credential.BLANK;

        return credential;
    }

    public static void setCredential(Credential credential) {
        Preferences prefs = Preferences.userNodeForPackage(Settings.class);
        putObject(prefs, Credential.class.getName(), credential);
    }

    public static void setJiraHostName(String jiraHostName) {
        logger.info("Saving jirahostname: " + jiraHostName);
        Preferences prefs = Preferences.userNodeForPackage(Settings.class);
        putObject(prefs, KEY_JIRA_HOST_NAME, jiraHostName);
    }

    public static String getJiraHostName() {
        Preferences prefs = Preferences.userNodeForPackage(Settings.class);
        final Object object = getObject(prefs, KEY_JIRA_HOST_NAME);
        return object != null ? (String) object : "";
    }

    //    public static String getJiraHostName() {
    //        Preferences prefs = Preferences.userNodeForPackage(Settings.class);
    //        final Object object = getObject(prefs, KEY_JIRA_HOST_NAME);
    //        return object != null ? (String) object : "";
    //    }

    //    public static void setLastOpenWorklogSheet(String lastOpenWorklogSheet) {
    //        logger.info("Saving LastOpenWorklogSheet: " + lastOpenWorklogSheet);
    //        Preferences prefs = Preferences.userNodeForPackage(Settings.class);
    //        putObject(prefs, KEY_LAST_OPEN_WORKLOG_SHEET, lastOpenWorklogSheet);
    //    }

    public static <T> T getObject(String key) {
        Preferences prefs = Preferences.userNodeForPackage(Settings.class);
        return getObject(prefs, key);
    }

    public static <T> T getObject(Preferences prefs, String key) {
        T obj = null;
        try {
            obj = (T) PrefObj.getObject(prefs, key);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return obj;
    }

    public static void putObject(String key, Object o) {
        Preferences prefs = Preferences.userNodeForPackage(Settings.class);
        putObject(prefs, key, o);
    }

    public static void putObject(Preferences prefs, String key, Object o) {
        try {
            PrefObj.putObject(prefs, key, o);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}
