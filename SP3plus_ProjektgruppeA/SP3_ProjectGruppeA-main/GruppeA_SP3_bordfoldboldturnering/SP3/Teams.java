package SP3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Teams {
    public Scanner scan = new Scanner(System.in);
    public Player p = new Player();
    private String s;
    private final String error = "Noget gik galt, prøv igen";

    //#BERRY - method to add a team to Team table SQL
    public void insertTeams(Connection c) {
        String playerName = "INSERT INTO teams (teamname) VALUES (?)";
        try {
            PreparedStatement query = c.prepareStatement(playerName);
            for (int i = 0; i < 100; i++) {
                System.out.println("Tilføj holdets navn eller q for at gemme og afslutte:");
                s = scan.nextLine();
                if (s.equalsIgnoreCase("q")) {
                    System.out.println("Du har nu afsluttet.");
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

    //#BERRY - method to display all from Teams table SQL by user search
    public void searchForTeamLike(Connection c) {
        String playerName = "SELECT * FROM teams WHERE teamname LIKE  ? ";
        try {
            System.out.println("Søg på et hold:");
            PreparedStatement query = c.prepareStatement(playerName);
            s = "%" + scan.nextLine() + "%";
            query.setString(1, s);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                System.out.println("Navn på hold: " + rs.getString("teamname"));
                System.out.println("ID på hold: " + rs.getString("idteam"));
            }
            query.close();
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }

    //#BERRY - method to show all teams
    public void showAllTeams(Connection c) {
        String teams = "SELECT * FROM teams ";
        System.out.println("Liste over alle hold:");
        try {
            PreparedStatement query = c.prepareStatement(teams);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getString("idteam") + ". Hold: " + rs.getString("teamname"));
            }
            query.close();
        } catch (SQLException e) {
            System.out.println("noget gik galt");
            e.printStackTrace();
        }
    }

    //#BERRY - method to check how many players there are on a team
    public void checkTeamCap(Connection c) {
        String teamName = "";
        String capacity = "SELECT COUNT(*) FROM teamplayers WHERE idteam = ?";
        showAllTeams(c);
        try {
            PreparedStatement query = c.prepareStatement(capacity);
            System.out.println("Hvilket holdID vil du søge på?");

            s = scan.nextLine();
            query.setString(1, s);
            teamName = s;
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                System.out.println("Antal spillere på holdet: " + rs.getString(1));
            }
            query.close();

        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }

        System.out.println("Hvis du vil tilføje en spiller på hold " + getTeamNameFromId(c, teamName) + ", så tast L ellers tast ENTER for at vende tilbagbe til menuen ");
        s = scan.nextLine();

        if (s.equalsIgnoreCase("L")) {
            p.showAllPlayers(c);
            p.addPlayerToTeamplayersTable(c, teamName);
        }
        System.out.println("Din spiller er nu tilføjet, og du vender nu tilbage til menuen");
    }

    //#FELICIA - method to show team ID and teamname
    public void showTeams(Connection c) {
        String allTeams = "SELECT * FROM teams ";
        try {
            PreparedStatement query = c.prepareStatement(allTeams);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getString("idteam") + ", Hold: " + rs.getString("teamname"));
            }
            query.close();
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }

    //#FELICIA - method to get teamname by teamID
    public String getTeamNameFromId(Connection c, String id) {
        String result = "";
        try {
            String teamName = "SELECT teamname FROM teams WHERE idteam = ? ";
            PreparedStatement query = c.prepareStatement(teamName);
            query.setString(1, id);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                result = rs.getString("teamname");
            }
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
        return result;
    }

    //#ISRAA - method to get teamname1
    public String getTeamName1FromRoundsID(Connection c, String id) {
        String result = "";
        try {
            String selectScore = "SELECT team1 FROM rounds WHERE id = ? ";
            PreparedStatement query = c.prepareStatement(selectScore);
            query.setString(1, id);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                result = rs.getString("team1");
            }
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
        return result;
    }

    //#ISRAA - method to get teamname2
    public String getTeamName2FromRoundsID(Connection c, String id) {
        String result = "";
        try {
            String selectScore = "SELECT team2 FROM rounds WHERE id = ? ";
            PreparedStatement query = c.prepareStatement(selectScore);
            query.setString(1, id);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                result = rs.getString("team2");
            }
        } catch (SQLException e) {
            System.out.println("noget gik galt");
            e.printStackTrace();
        }
        return result;
    }

    //#ISRAA - method to get Team1 id
    public String getTeam1FromRoundsID(Connection c, String id) {
        String result = "";
        try {
            String selectScore = "SELECT team1 FROM rounds WHERE id = ? ";
            PreparedStatement query = c.prepareStatement(selectScore);
            query.setString(1, id);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                result = rs.getString("team1");
            }
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
        return result;
    }

    //#ISRAA - method to get Team2 id
    public String getTeam2FromRoundsID(Connection c, String id) {
        String result = "";
        try {
            String selectScore = "SELECT team2 FROM rounds WHERE id = ? ";
            PreparedStatement query = c.prepareStatement(selectScore);
            query.setString(1, id);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                result = rs.getString("team2");
            }
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
        return result;
    }
}