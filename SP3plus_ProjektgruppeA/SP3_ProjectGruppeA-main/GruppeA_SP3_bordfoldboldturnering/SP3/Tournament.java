package SP3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Tournament {
    public Scanner scan = new Scanner(System.in);
    public Menu m = new Menu();
    private final String error = "Noget gik galt, prøv igen";

    //#FELICIA - method to start the program - other methods could be added later
    public void startTournamentProgram(Connection c) {
        displayTitelAndDescription();
        m.displayProgramMenu(c);
    }

    //#FELICIA - method to display the titel and description of the Tournament Program
    private void displayTitelAndDescription() {
        System.out.println("\n****** BORDFODBOLD TURNERINGS PROGRAM ******");
        System.out.println("Dette er et program, der kan registrere bordfodboldturneringer.");
        System.out.println("Programmet styres af en turneringsleder - i dette tilfælde er det dig :D");
        System.out.println("Når du opretter en turnering registrerer du 8 hold. Hvert hold skal have 5 spillere.");
        System.out.println(">> Udarbejdet af projektGruppeA - Berry, Martin, Israa & Felicia <<");
    }

    //#BERRY - method to insert date, time, location to tournamentInfo table SQL
    public void insertTournament(Connection c) {
        String info = "INSERT INTO tournamentinfo (date, time, location) VALUES (?, ?, ?)";

        try {
            PreparedStatement query = c.prepareStatement(info);
            for (int i = 0; i < 100; i++) {
                System.out.println("Tilføj dato for turneringen (ÅÅÅÅ-MM-DD):");
                String s = scan.nextLine();
                query.setString(1, s);
                System.out.println("Tilføj tidspunktet for turneringen (TT:MM)");
                s = scan.nextLine();
                query.setString(2, s);
                System.out.println("Tilføj lokationen for turneringen");
                s = scan.nextLine();
                query.setString(3, s);
                query.executeUpdate();
                System.out.println("Tast q for at vende tilbage til menuen");
                s = scan.nextLine();

                if (s.equalsIgnoreCase("q")) {
                    System.out.println("Du vender nu tilbage til menuen");
                    break;
                }
            }
            query.close();
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }

    //#BERRY - method to display all from tournamentInfo table SQL
    public void displayTournaments(Connection c) {
        String teamID = "SELECT * FROM tournamentinfo";
        try {
            PreparedStatement query = c.prepareStatement(teamID);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                System.out.println("Dato: " + rs.getString("date")
                        + "\nTid: " + rs.getString("time")
                        + "\nLokation: " + rs.getString("location") + "\n");
            }
            query.close();
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }
}