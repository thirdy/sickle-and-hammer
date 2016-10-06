package net.thirdy.sh.jira;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;

public class CustomHttpClient {

    public static <T> T doHttpMethod(HttpUriRequest request, ResponseHandler<T> responseHandler)
            throws ClientProtocolException, IOException {

        ClientConnectionManager cm = new SingleClientConnManager();
        cm.getSchemeRegistry().register(NoCertHttpsScheme.NO_CERT_HTTPS_SCHEME);
        HttpClient httpClient = new DefaultHttpClient(cm);

        HttpResponse response = httpClient.execute(request);
        T t = responseHandler.handleResponse(response);
        cm.shutdown();

        return t;

    }

    public static String doHttpMethod(HttpUriRequest request) throws ClientProtocolException, IOException {
        return doHttpMethod(request, new BasicResponseHandler());
    }

}
