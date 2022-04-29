package SP3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ScoreAndPoints {
    public Scanner scan = new Scanner(System.in);
    public RoundsAndRanklist r = new RoundsAndRanklist();
    public Teams t = new Teams();
    private String s;
    private int firstTeamScore;
    private int secondTeamScore;
    private final String error = "Noget gik galt, prøv igen";

    //#MARTIN - method to add points to teams
    public void addPointToTeams(Connection c, String teamID, int point) {
        String addPoint =  "UPDATE teams SET point = ? WHERE idteam = ?";
        try {
            PreparedStatement query = c.prepareStatement(addPoint);
            query.setInt(1, point);
            query.setString(2, teamID );
            query.executeUpdate();
            query.close();

        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }

    //#ISRAA - method to compare scores of teams and add updated points
    public void compareScore(Connection c, int roundID, int updatedPoint) {
        int point = 2;
        try {
            String score = "SELECT score1, score2, team1, team2 FROM rounds WHERE ID = ?";
            PreparedStatement query = c.prepareStatement(score);
            query.setInt(1, roundID);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                String team1 = rs.getString("team1");
                String team2 = rs.getString("team2");
                int team1Score = rs.getInt("score1");
                int team2Score = rs.getInt("score2");

                if(team1Score > team2Score) {
                    addPointToTeams(c,team1,point + updatedPoint);
                    addPointToTeams(c, team2,0 + updatedPoint);
                }
                else if(team1Score < team2Score) {
                    addPointToTeams(c,team2,point + updatedPoint);
                    addPointToTeams(c, team1,0 + updatedPoint);
                }
            }
            query.close();
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }

    //#MARTIN- method to add which teams should play against each other
    public void addTeamsToRounds(Connection c) {
        r = new RoundsAndRanklist();
        r.displayRounds(c);
        String vs = "UPDATE rounds SET team1 = ?, team2 = ? WHERE id = ?";

        try {
            PreparedStatement query = c.prepareStatement(vs);
            System.out.println("Hvilken kamp vil du tildele hold:");
            System.out.println("Kamp ID:");
            s = scan.nextLine();
            query.setString(3, s);

            System.out.println("Vælg hvilke hold, der skal spille mod hinanden");
            t.showTeams(c);
            System.out.println("Hold1");
            String firstTeam = scan.nextLine();
            query.setString(1, firstTeam);
            System.out.println("Hold2");
            String secondTeam = scan.nextLine();
            query.setString(2, secondTeam);
            System.out.println("Følgende hold skal spille mod hinanden:");

            query.executeUpdate();
            query.close();

            System.out.println(t.getTeamNameFromId(c, firstTeam) + " VS " + t.getTeamNameFromId(c, secondTeam));

            while (s.equalsIgnoreCase("q")) {
                System.out.println("Du vender nu tilbage til menuen");
                break;
            }
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }

    //#BERRY - method to add/update a score to a round
    public void updateScoreToRounds(Connection c) {
        String firstTeamID = "";
        String secondTeamID = "";
        r = new RoundsAndRanklist();
        r.displayRounds(c);
        String score = "UPDATE rounds SET score1 = ?, score2 = ? WHERE ID = ?";

        try {
            PreparedStatement query = c.prepareStatement(score);
            System.out.println("Hvilken kamp vil du tildele score:");
            System.out.println("Kamp ID:");
            s = scan.nextLine();

            query.setString(3, s);
            firstTeamID = t.getTeam1FromRoundsID(c, s);
            secondTeamID = t.getTeam2FromRoundsID(c, s);

            System.out.println(t.getTeamNameFromId(c,firstTeamID) + " score: ");
            firstTeamScore = scan.nextInt();
            query.setInt(1, firstTeamScore);
            System.out.println(t.getTeamNameFromId(c,secondTeamID) + " score: ");
            secondTeamScore = scan.nextInt();
            query.setInt(2, secondTeamScore);

            query.executeUpdate();
            query.close();

            System.out.println("Hold: " + t.getTeamNameFromId(c, t.getTeamName1FromRoundsID(c, s)) + ". Score: " + getScore1FromID(c, s));
            System.out.println("Hold: " + t.getTeamNameFromId(c, t.getTeamName2FromRoundsID(c, s)) + ". Score: " + getScore2FromID(c, s));

            while (s.equalsIgnoreCase("q")) {
                System.out.println("Du vender nu tilbage til menuen");
                break;
            }
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
        insertScoreToTeam(c, firstTeamID, firstTeamScore);
        insertScoreToTeam(c, secondTeamID, secondTeamScore);
    }

    //#BERRY - method to insert score to teamID
    public void insertScoreToTeam(Connection c, String teamID, int scoreFromRounds) {
        String scoreToTeams =  "UPDATE teams SET score = ? WHERE idteam = ?";
        try {
            PreparedStatement query = c.prepareStatement(scoreToTeams);
            query.setInt(1, scoreFromRounds);
            query.setString(2, teamID);
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }

    //#FELICIA - method to get the score from the first team
    public int getScore1FromID(Connection c, String id) {
        int result1 = 0;
        try {
            String selectScore = "SELECT score1 FROM rounds WHERE id = ? ";
            PreparedStatement query = c.prepareStatement(selectScore);
            query.setString(1, id);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                result1 = rs.getInt("score1");
            }
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
        return result1;
    }

    //#FELICIA - method to get the score from the second team
    public int getScore2FromID(Connection c, String id) {
        int result2 = 0;
        try {
            String selectScore = "SELECT score2 FROM rounds WHERE id = ? ";
            PreparedStatement query = c.prepareStatement(selectScore);
            query.setString(1, id);
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                result2 = rs.getInt("score2");
            }
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
        return result2;
    }
}