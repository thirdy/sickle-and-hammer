package net.thirdy.sh.setting;

import java.io.Serializable;

public class Credential implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Credential BLANK = new Credential("", "");

    private String username;
    private String password;

    public Credential() {
        super();
    }

    public Credential(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
