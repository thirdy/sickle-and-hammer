package sandbox;

import java.util.prefs.Preferences;

import net.thirdy.sh.jira.CustomHttpClient;
import net.thirdy.sh.setting.Settings;

import org.apache.http.client.methods.HttpGet;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class RestWorklog {

    @Test
    public void getxWorklogsByAuthorAndIssue() throws Exception {
        int batchSize = 5;
        int offsetBetweenJobSeconds = 5;

        for (int i = 0; i < 100; i++) {
            //            int partitionIndex = (i / batchSize);
            //            int relativeOffsetMinutes = batchInitialOffsetMinutes + (partitionIndex * batchOffsetMinutes);
            int relativeOffsetSeconds = (i % batchSize) * offsetBetweenJobSeconds;
            System.out.println(relativeOffsetSeconds);
        }
    }

    @Test
    public void getWorklogsByAuthorAndIssue() throws Exception {

        //removed senstive info
        //        HttpGet getMethod = new HttpGet(url);
        //        getMethod.addHeader("Content-type", "application/json");
        //        getMethod.addHeader("charset", "utf-8");
        //        getMethod.addHeader("Authorization", "Basic " + new String(base64EncodedUsernamePassword));

        //        final String response = CustomHttpClient.doHttpMethod(getMethod);
        //
        //        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //        JsonParser jp = new JsonParser();
        //        JsonElement je = jp.parse(response);
        //
        //        String json = gson.toJson(je);
        //        System.out.println(json);
    }

    //    @Test
    //    public void x() throws Exception {
    //        Preferences prefs = Preferences.userNodeForPackage(net.thirdy.sh.settings.Settings.class);
    //        System.out.println(Settings.getObject(prefs, "key_lastOpenWorklogSheet"));
    //    }

}
