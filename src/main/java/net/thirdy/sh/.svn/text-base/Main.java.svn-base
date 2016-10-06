package net.thirdy.sh;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import net.thirdy.sh.setting.Credential;
import net.thirdy.sh.setting.Settings;

import org.joda.time.DateTime;

/**
 * Hammer and Sickle icon by Vandekieft
 */

/**
 * @author vicente.rivera
 */
public class Main {

    public static final String PROJECT_ROOT_PACKAGE = "net.thirdy.sh";
    public static final String PROJECT_NAME = "Sickle and Hammer 0.0.4";
    public static final String COMMON_DATE_FORMAT = "yyyy-MM-dd";

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public Main() {

        Credential credential = Settings.getCredential();
        String jiraHostName = Settings.getJiraHostName();

        DateTime currentDateTime = new DateTime();

        setupLookAndFeel();

        final MainFrame mainFrame = new MainFrame(credential, jiraHostName, currentDateTime);
        mainFrame.setSize(880, 750);
        // position mainFrame to center of the screen
        mainFrame.setLocationRelativeTo(null);

        // configure logging
        Logger.getLogger(PROJECT_ROOT_PACKAGE).setLevel(Level.FINEST);
        Logger rootLogger = Logger.getLogger(PROJECT_ROOT_PACKAGE);
        rootLogger.addHandler(new JTextAreaLogger() {

            @Override
            public JTextArea getJTextArea() {
                return mainFrame.getConsoleTA();
            }
        });

        mainFrame.setup();
        mainFrame.showGUI();
        logger.info("Application started. Date today: " + new Date());
    }

    private void setupLookAndFeel() {
        LookAndFeelInfo[] installedLookAndFeels = UIManager.getInstalledLookAndFeels();
        for (LookAndFeelInfo lookAndFeelInfo : installedLookAndFeels) {
            if ("Nimbus".equals(lookAndFeelInfo.getName())) {
                try {
                    UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Failed to setup Nimbus look and feel", e);
                }
                break;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // TODO, study swing threading and possibly implement
        //        Main main = new Main();
        new Main();
    }

    private static abstract class JTextAreaLogger extends Handler {

        @Override
        public void close() throws SecurityException {
            // TODO Auto-generated method stub

        }

        @Override
        public void flush() {
            // TODO Auto-generated method stub

        }

        @Override
        public void publish(LogRecord record) {
            StringWriter text = new StringWriter();
            PrintWriter out = new PrintWriter(text);
            out.printf("[%s] %s", record.getLevel(), record.getMessage());
            //            out.printf("[%s]: %s.%s -> %s", record.getLevel(), record.getSourceClassName(), record
            //                    .getSourceMethodName(), record.getMessage());
            if (record.getThrown() != null) {
                //                out.println();
                record.getThrown().printStackTrace(out);
            }
            out.println();
            getJTextArea().append(text.toString());
        }

        public abstract JTextArea getJTextArea();

    }
}
