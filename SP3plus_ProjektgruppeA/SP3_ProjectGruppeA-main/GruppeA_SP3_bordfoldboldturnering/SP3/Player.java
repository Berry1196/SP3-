package SP3;

import java.sql.*;
import java.util.Scanner;

public class Player {
    public Scanner scan = new Scanner(System.in);
    private String s;
    private final String error = "Noget gik galt, prøv igen";

    //#ISRAA - method to register a player
    public void insertPlayer(Connection c) {
        String playerName = "INSERT INTO players (playername) VALUES (?)";

        try {
            PreparedStatement query = c.prepareStatement(playerName);
            for (int i = 0; i < 100; i++) {
                System.out.println("Tilføj spillerens navn eller q for at gemme og afslutte:");
                s = scan.nextLine();

                if (s.equalsIgnoreCase("q")) {
                    System.out.println("Du vender nu tilbage til menuen");
                    break;
                }
                query.setString(1, s);
                query.executeUpdate();
            }
            query.close();
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }

    //#FELICIA - method to show all registered players
    public void showAllPlayers(Connection c) {
        System.out.println("Liste over alle spillere:");
        String players = "SELECT * FROM players ";

        try {
            PreparedStatement query = c.prepareStatement(players);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getString("idplayer") + ". Navn: " + rs.getString("playername"));
            }
            query.close();

        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }

    //#FELICIA - method to add a player to teamplayers table SQL by ID
    public void addPlayerToTeamplayersTable(Connection c, String teamID) {
        String combine = "INSERT INTO teamplayers (idteam, idplayer) VALUES (?,?)";

        try {
            PreparedStatement query = c.prepareStatement(combine);
            System.out.println("Tast spillerens ID");
            s = scan.nextLine();
            query.setString(2, s);
            query.setString(1, teamID);
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }

    //#ISRAA - method to add a player to a team
    public void addPlayerToTeam(Connection c) {
        String combine = "INSERT INTO teamplayers (idteam, idplayer) VALUES (?,?)";
        showAllPlayers(c);

        try {
            PreparedStatement query = c.prepareStatement(combine);
            System.out.println("Tast holdets ID");
            s = scan.nextLine();
            query.setString(1, s);
            System.out.println("Tast spillerens ID");
            s = scan.nextLine();
            query.setString(2, s);
            query.executeUpdate();
            query.close();

        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }

    //#FELICIA - method to change a team's players
    public void updatePlayerToTeamplayers(Connection c) {
        String updateName = "UPDATE teamplayers SET idplayer = ? WHERE idteam = ?";

        try {
            PreparedStatement query = c.prepareStatement(updateName);
            System.out.println("Tast spillerID på den spiller du ønsker at ændre hold på:");
            s = scan.nextLine();
            query.setString(1, s);
            System.out.println("Tast holdID på det hold du vil tildele spilleren:");
            s = scan.nextLine();
            query.setString(2, s);
            query.executeUpdate();
            query.close();

        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }
}