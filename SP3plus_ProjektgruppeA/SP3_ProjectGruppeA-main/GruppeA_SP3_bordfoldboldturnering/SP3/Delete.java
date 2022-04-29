package SP3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Delete {
    public Scanner scan = new Scanner(System.in);
    private String error = "Noget gik galt, prøv igen";

    //#BERRY - method to delete a team by id
    public void deleteTeamFromID(Connection c) {
        String deleteName = "DELETE FROM teams WHERE idteam = ?";

        try {
            PreparedStatement query = c.prepareStatement(deleteName);
            System.out.println("Tast id'et på det hold, du vil slette, q for at gemme og afslutte:");
            int id = scan.nextInt();
            query.setInt(1, id);
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }

    //#MARTIN - method to delete a team by name
    public void deleteTeamFromName(Connection c) {
        String deleteName = "DELETE FROM teams WHERE teamname = ?";

        try {
            PreparedStatement query = c.prepareStatement(deleteName);
            System.out.println("Tast navnet på det hold, du vil slette, q for at gemme og afslutte:");
            String name = scan.nextLine();

            query.setString(1, name);
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }

    //#MARTIN - method to delete a player by id
    public void deletePlayerFromID(Connection c) {
        String deleteName = "DELETE FROM players WHERE idplayer = ?";

        try {
            PreparedStatement query = c.prepareStatement(deleteName);
            System.out.println("Tast id'et på den spiller, du vil slette, q for at gemme og afslutte:");
            int id = scan.nextInt();
            query.setInt(1, id);
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }

    //#ISRAA- method to delete a player by name
    public void deletePlayerFromName(Connection c) {
        String deleteName = "DELETE FROM players WHERE playername = ?";

        try {
            PreparedStatement query = c.prepareStatement(deleteName);
            System.out.println("Tast navnet på den spiller, du vil slette, q for at gemme og afslutte:");
            String name = scan.nextLine();
            query.setString(1, name);
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }

    //#BERRY - method to delete a player from a team by entering id
    public void deletePlayerFromTeam(Connection c) {
        String deleteName = "DELETE FROM teamplayers WHERE idplayer = ?";

        try {
            PreparedStatement query = c.prepareStatement(deleteName);
            System.out.println("Tast id'et på den spiller, du vil slette, q for at gemme og afslutte:");
            int id = scan.nextInt();
            query.setInt(1, id);
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }
}
