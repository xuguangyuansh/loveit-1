package cn.net.model.login;

public class LoginBean {
    private String id;
    private String token;
    private String userId;
    private boolean newUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                ", newUser=" + newUser +
                '}';
    }
}
