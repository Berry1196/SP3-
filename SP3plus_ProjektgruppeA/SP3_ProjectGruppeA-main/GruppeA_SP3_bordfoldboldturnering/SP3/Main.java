package SP3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    //'#NAME' = the AUTHOR of the code

    //#BERRY - Main method to make the code run
    public static void main(String[] args) {
        Connection c = null;
        DBinfo d = new DBinfo();
        Tournament tour = new Tournament();
        RoundsAndRanklist r = new RoundsAndRanklist();

        try {
            c = DriverManager.getConnection(d.getJdbcUrl(), d.getUsername(), d.getPassword());

            //#BERRY - method to run the table football tournament program
           tour.startTournamentProgram(c);

            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


