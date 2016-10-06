package net.thirdy.sh.jira;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Logger;

import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;

public class NoCertHttpsScheme {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public static final Scheme NO_CERT_HTTPS_SCHEME = newScheme();

    private static final Scheme newScheme() {
        // SSL workaround thanks to SO question 2703161
        SSLSocketFactory sf;
        try {
            sf = new SSLSocketFactory(new TrustStrategy() {

                public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                    return true;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Scheme httpsScheme = new Scheme("https", 443, sf);
        return httpsScheme;
    }

}
