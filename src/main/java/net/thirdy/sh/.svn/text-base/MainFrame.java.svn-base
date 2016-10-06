/**
 * 
 */
package net.thirdy.sh;

import static net.thirdy.sh.Main.*;
import static org.apache.commons.lang.StringUtils.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.thirdy.sh.jira.SubmitWorklogTask;
import net.thirdy.sh.setting.Credential;
import net.thirdy.sh.setting.Settings;
import net.thirdy.sh.swing.WrapLayout;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import com.toedter.calendar.JCalendar;

/**
 * @author vicente.rivera
 */
public class MainFrame extends JFrame {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final long serialVersionUID = 1L;

    private JTextArea worklogTA;
    private JTextArea consoleTA;
    private JTextField usernameTF;
    private JPasswordField passwordTF;
    private JTextField jiraServerHostnameTF;
    //    private JTextField worklogDateTF;
    private JCalendar calendar;

    private DateTime currentDateTime;
    private Credential credential;
    private String jiraHostName;

    public MainFrame(Credential credential, String jiraHostName, DateTime currentDateTime) {
        super(Main.PROJECT_NAME);
        this.credential = credential;
        this.jiraHostName = jiraHostName;
        this.currentDateTime = currentDateTime;
    }

    public void setup() {
        URL iconURL = getClass().getResource("/sh.png");
        ImageIcon icon = new ImageIcon(iconURL);
        setIconImage(icon.getImage());

        consoleTA = new JTextArea(15, 5);
        consoleTA.setBackground(new Color(210, 210, 210));
        consoleTA.setEditable(false);

        worklogTA = new JTextArea();
        worklogTA.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setCurrentWorklogSheet(currentDateTime);

        usernameTF = new JTextField(credential.getUsername(), 15);
        passwordTF = new JPasswordField(credential.getPassword(), 15);
        jiraServerHostnameTF = new JTextField(jiraHostName, 15);
        //        worklogDateTF = new JTextField(currentDateTime.toString(COMMON_DATE_FORMAT), 15);

        calendar = new JCalendar(currentDateTime.toDate());
        //        calendar.setPreferredSize(new Dimension(380, 10));
        calendar.setWeekOfYearVisible(false);
        calendar.setTodayButtonVisible(true);
        final PropertyChangeListener currentDateChangeListener = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                saveCurrentWorklogSheet();
                final DateTime dateTime = new DateTime(calendar.getDate());
                MainFrame.this.currentDateTime = dateTime;
                logger.info("Changed date via calendar chooser. New date is now: "
                        + dateTime.toString(COMMON_DATE_FORMAT));
                setCurrentWorklogSheet(dateTime);
            }
        };
        calendar.getDayChooser().addPropertyChangeListener("day", currentDateChangeListener);

        JButton rememberMe = new JButton("remember me");
        rememberMe.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane
                        .showConfirmDialog(MainFrame.this,
                                "Warning, this will save sensitive info in a plain text file or in the windoze registry. Proceed?");
                if (JOptionPane.YES_OPTION == confirm) {
                    Settings.setCredential(getCredentialFromUI());
                }
            }
        });

        //        final JProgressBar progressBar = new JProgressBar()
        final JButton submitWorklog = new JButton("Do my worklog!");
        submitWorklog.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                logger.finest(submitWorklog.getText() + " called");

                //                DateTime worklogDate = null;
                //                try {
                //                    worklogDate = DateTimeFormat.forPattern(COMMON_DATE_FORMAT).parseDateTime(worklogDateTF.getText());
                //                    MainFrame.this.currentDateTime = worklogDate;
                //                } catch (Exception ex) {
                //                    logger.log(Level.SEVERE, ex.getMessage(), ex);
                //                    return;
                //                }

                List<Worklog> worklogs = parse(worklogTA.getText(), MainFrame.this.currentDateTime);
                logger.finest("Worklogs parsed: " + worklogs);

                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                submitWorklog.setEnabled(false);
                worklogTA.setEnabled(false);
                SubmitWorklogTask task = new SubmitWorklogTask(worklogs, getCredentialFromUI(), jiraServerHostnameTF
                        .getText()) {

                    @Override
                    public void done() {
                        logger.finest("SubmitWorklogTask done");
                        saveCurrentWorklogSheet();
                        setCursor(null);
                        submitWorklog.setEnabled(true);
                        worklogTA.setEnabled(true);
                    }

                };
                task.execute();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                logger.info("windowClosed");
                Settings.setJiraHostName(jiraServerHostnameTF.getText());
                saveCurrentWorklogSheet();
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel northPanel = new JPanel(new WrapLayout());
        northPanel.add(new JLabel("username"));
        northPanel.add(usernameTF);
        northPanel.add(new JLabel("password"));
        northPanel.add(passwordTF);
        northPanel.add(new JLabel("jira hostname"));
        northPanel.add(jiraServerHostnameTF);
        //        northPanel.add(new JLabel("Worklog date (yyyy-MM-dd)"));
        //        northPanel.add(worklogDateTF);
        northPanel.add(rememberMe);
        centerPanel.add(northPanel, BorderLayout.NORTH);
        centerPanel.add(calendar, BorderLayout.EAST);
        centerPanel.add(worklogTA, BorderLayout.CENTER);

        centerPanel.add(submitWorklog, BorderLayout.SOUTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        JPanel consolePanel = new JPanel(new BorderLayout());
        consolePanel.setBorder(BorderFactory.createTitledBorder("Logs"));
        consolePanel.add(new JScrollPane(consoleTA));
        mainPanel.add(consolePanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    private void setCurrentWorklogSheet(DateTime currentDateTime) {

        final String key = Settings.PARTIAL_KEY_WORKLOG_SHEET + currentDateTime.toString(COMMON_DATE_FORMAT);
        logger.info("Loading worklog sheet using key: " + key);
        String worklogSheet = Settings.getObject(key);
        //        logger.info("Loaded worklog sheet: " + worklogSheet);

        if (worklogSheet != null) {
            worklogTA.setText(worklogSheet);
        } else {
            String instructions = "// Put your worklog here using format <jira ticket> <time spent> [<comment>]"
                    + NEW_LINE + "// Note that there should be no whitespaces for <time spent>" + NEW_LINE
                    + "// e.g. JIRA-150 1h30m" + NEW_LINE + NEW_LINE;
            worklogTA.setText(instructions);
        }

    }

    protected Credential getCredentialFromUI() {
        return new Credential(usernameTF.getText(), new String(passwordTF.getPassword()));
    }

    public void showGUI() {
        setVisible(true);
    }

    private static List<Worklog> parse(String str, DateTime worklogDate) {
        Date dateTimeNow = worklogDate.toDate();
        List<Worklog> worklogs = new LinkedList<Worklog>();
        if (isNotBlank(str)) {
            String[] rawWorklogs = split(str, NEW_LINE);
            for (String rawWorklog : rawWorklogs) {
                if (isNotBlank(rawWorklog) && !rawWorklog.startsWith("//") && !rawWorklog.startsWith("--")) {
                    String[] worklogTokens = StringUtils.split(rawWorklog);
                    if (worklogTokens.length >= 3) {
                        worklogs.add(new Worklog(worklogTokens[0], worklogTokens[1], dateTimeNow, trim(substringAfter(
                                rawWorklog, worklogTokens[1]))));
                    } else if (worklogTokens.length == 2) {
                        worklogs.add(new Worklog(worklogTokens[0], worklogTokens[1], dateTimeNow));
                    }
                }
            }
        }
        return worklogs;
    }

    public JTextArea getConsoleTA() {
        return consoleTA;
    }

    private void saveCurrentWorklogSheet() {
        String key = Settings.PARTIAL_KEY_WORKLOG_SHEET + MainFrame.this.currentDateTime.toString(COMMON_DATE_FORMAT);
        logger.log(Level.INFO, String.format("Saving worklog sheet, key (%s)", key));
        Settings.putObject(key, worklogTA.getText());
    }

}
