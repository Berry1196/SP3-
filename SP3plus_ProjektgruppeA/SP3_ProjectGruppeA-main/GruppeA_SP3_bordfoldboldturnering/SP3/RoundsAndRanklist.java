package SP3;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RoundsAndRanklist {
    public Scanner scan = new Scanner(System.in);
    public Teams t = new Teams();
    public ScoreAndPoints sc;
    private String s;
    private final String error = "Noget gik galt, prøv igen";

    //#MARTIN - method to insert information about a round (type ex 'kvartfinale', starttime, endTime)
    public void insertRoundsInfo(Connection c) {
        String info = "INSERT INTO rounds (round, startTime, endTime) VALUES (?, ?, ?)";

        try {
            PreparedStatement query = c.prepareStatement(info);
            for (int i = 0; i < 100; i++) {
                System.out.println("Tilføj hvilken slags kamp det er:");
                System.out.println("Tast et bogstav. \n K = Kvartfinale, S = Semifinale og F = Finale" );
                s = scan.nextLine();
                if(s.equalsIgnoreCase("K")) {
                    s = "Kvartfinale";
                }
                if(s.equalsIgnoreCase("S")) {
                    s = "Semifinale";
                }
                if(s.equalsIgnoreCase("F")) {
                    s = "Finale";
                }
                query.setString(1, s);
                System.out.println("Tilføj starttidspunktet for kampen (TT:MM)");
                s = scan.nextLine();
                query.setString(2, s);
                System.out.println("Tilføj sluttidspunktet for kampen (TT:MM)");
                s = scan.nextLine();
                query.setString(3, s);
                query.executeUpdate();
                System.out.println("Tast q for at vende tilbage til menuen eller e, for at tilføje endnu en kamp");
                s = scan.nextLine();

                if (s.equalsIgnoreCase("q")) {
                    System.out.println("Du vender nu tilbage til menuen");
                    break;
                } else if (s.equalsIgnoreCase("e")) {
                    insertRoundsInfo(c);
                    break;
                }
            }
            query.close();
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }

    //#MARTIN - method to search for a round by user input (ex 'like %')
    public void searchForRoundLike(Connection c) {
        String playerName = "SELECT * FROM rounds WHERE round like ?";

        try {
            System.out.println("Søg på en kamp:");
            PreparedStatement query = c.prepareStatement(playerName);
            s = "%" + scan.nextLine() + "%";
            query.setString(1, s);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getString("id")
                        + ". Kamp: " + rs.getString("round")
                        + ".\nStart tid: " + rs.getString("startTime")
                        + ". Slut tid: " + rs.getString("endTime")
                        + "\n" + t.getTeamNameFromId(c, rs.getString("team1"))
                        + " VS " + t.getTeamNameFromId(c, rs.getString("team2")) + "\n");
            }
            query.close();

        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }

    //#MARTIN - method to display all data from Rounds table SQL
    public void displayRounds(Connection c) {
        String round = "SELECT * FROM rounds";

        try {
            PreparedStatement query = c.prepareStatement(round);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("id")
                        + "\nKamp: " + rs.getString("round")
                        + "\nStart tid: " + rs.getString("startTime")
                        + "\nSlut tid: " + rs.getString("endTime") + "\n"
                        +  t.getTeamNameFromId(c,rs.getString("team1"))
                        + " VS " + t.getTeamNameFromId(c,rs.getString("team2")) + "\n");
            }
            query.close();

        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }

    //#BERRY - method to add points to rounds
    public void playRounds(Connection c) {
        System.out.println("Tast et bogstav. \n K = Kvartfinale, S = Semifinale og F = Finale" );
        s = scan.nextLine();
        if(s.equalsIgnoreCase("K")) {
            quarterFinal(c);
            System.out.println("Point for kvartfinalen er nu blevet tildelt");
        }
        if(s.equalsIgnoreCase("S")) {
            semiFinal(c);
            System.out.println("Point for semifinalen er nu blevet tildelt");
        }
        if(s.equalsIgnoreCase("F")) {
            finalRound(c);
            System.out.println("Point for finalen er nu blevet tildelt");
        }
    }

    //#MARTIN- method to run a quarterfinal
    public void quarterFinal(Connection c) {
        sc = new ScoreAndPoints();
        sc.compareScore(c, 1,0);
        sc.compareScore(c, 2, 0);
        sc.compareScore(c, 3, 0);
        sc.compareScore(c, 4, 0);
    }

    //#ISRAA - method to run a semifinal
    public void semiFinal(Connection c) {
        sc = new ScoreAndPoints();
        sc.compareScore(c, 5, 2);
        sc.compareScore(c, 6, 2);
    }

    //#ISRAA - method to run the finalround
    public void finalRound(Connection c) {
        sc = new ScoreAndPoints();
        sc.compareScore(c, 7, 4);
    }

    //#ISRAA - method to make a ranklist
    public void makeRanklist(String teamName, int score, int point) throws FileNotFoundException {
        String indent = "%-20s%-17d%-17d";
        System.out.printf(indent, teamName, score, point);
        System.out.println();
    }

    //#ISRAA - method to display Ranklist
    public void displayRanklist(Connection c) {
        String indent = "%-19s%-16s%-16s\n";
        System.out.println("__________________RANGLISTE_______________");
        String round = "SELECT * FROM teams ORDER BY point DESC";
        try {
            PreparedStatement query = c.prepareStatement(round);
            ResultSet rs = query.executeQuery();
            System.out.printf(indent, "Hold", "Point", "Målscore");
            System.out.println("__________________________________________");
            while (rs.next()) {
                makeRanklist(rs.getString("teamname"), rs.getInt("point"), rs.getInt("score"));
                System.out.println("__________________________________________");
            }
            query.close();
        } catch (SQLException | FileNotFoundException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }
}