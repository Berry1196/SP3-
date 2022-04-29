package SP3;

public class DBinfo {
    private String JdbcUrl;
    private String username;
    private String password;

    //#ISRAA - method to hold database information
    public DBinfo() {
        this.JdbcUrl = "jdbc:mysql://localhost/tournament?" + "autoReconnect=true&useSSL=false";
        this.username = "root";
        this.password = "Chokolade123";
    }

    //#ISRAA - getters to fetch database information using return statements
    public String getJdbcUrl()
    {
        return this.JdbcUrl;
    }
    public String getUsername()
    {
        return this.username;
    }
    public String getPassword()
    {
        return this.password;
    }
}
