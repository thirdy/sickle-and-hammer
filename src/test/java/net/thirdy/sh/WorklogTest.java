/**
 * 
 */
package net.thirdy.sh;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author vicente.rivera
 */
public class WorklogTest {

    @Test
    public void test_formatting_of_timespent_1h() throws Exception {
        Worklog worklog = new Worklog();
        assertThat(worklog.formatTimeSpent("1h"), is("1h"));
    }

    @Test
    public void test_formatting_of_timespent_1h30m() throws Exception {
        Worklog worklog = new Worklog();
        assertThat(worklog.formatTimeSpent("1h30m"), is("1h 30m"));
    }
}
