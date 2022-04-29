package SP3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {
    public Scanner scan = new Scanner(System.in);
    public Tournament tour;
    public Teams t = new Teams();
    public Player p = new Player();
    public RoundsAndRanklist r = new RoundsAndRanklist();
    public Delete d = new Delete();
    public ScoreAndPoints sc;
    private final String error = "Noget gik galt, prøv igen";
    private final String enter = "# Tast ";
    private final String to = " for at ";
    private StringBuilder userInput;

    //#FELICIA - method to display the Tournament Program menu
    public void displayProgramMenu(Connection c) {
        tour = new Tournament();
        System.out.println("\n****** MENU ******");
        System.out.println(enter + "T" + to + "tilgå registrerings-menu");
        System.out.println(enter + "L" + to + "tilgå tilføj-menu");
        System.out.println(enter + "Z" + to + "tilgå søge-menu");
        System.out.println(enter + "I" + to + "tilgå slet-menu");
        System.out.println(enter + "U" + to + "se ledige pladser på hold");
        System.out.println(enter + "P" + to + "vise turneringer");
        System.out.println(enter + "S" + to + "kør en runde");
        System.out.println(enter + "R" + to + "vise ranglisten");
        System.out.println(enter + "V" + to + "ændre et holds spillere");
        System.out.println(enter + "Q" + to + "afslutte programmet");

        while (true) {
            userInput = new StringBuilder(scan.nextLine());
            if (userInput.toString().equalsIgnoreCase("T")) {
                registerFromDbMenu(c);
            } else if (userInput.toString().equalsIgnoreCase("L")) {
                addFromDbMenu(c);
            } else if (userInput.toString().equalsIgnoreCase("U")) {
                t.checkTeamCap(c);
            } else if (userInput.toString().equalsIgnoreCase("S")) {
                r.playRounds(c);
            } else if (userInput.toString().equalsIgnoreCase("Z")) {
                searchFromDbMenu(c);
            } else if (userInput.toString().equalsIgnoreCase("V")) {
                p.updatePlayerToTeamplayers(c);
            } else if (userInput.toString().equalsIgnoreCase("P")) {
                tour.displayTournaments(c);
            }  else if (userInput.toString().equalsIgnoreCase("R")) {
                r.displayRanklist(c);
            } else if (userInput.toString().equalsIgnoreCase("I")) {
                deleteFromDbMenu(c);
            } else if (userInput.toString().equalsIgnoreCase("Q")) {
                System.out.println("Du har nu afsluttet");
                break;
            } else {
                System.out.println(error);
            }
            displayProgramMenu(c);
        }
    }

    //#FELICIA - method to show register menu
    public void registerFromDbMenu(Connection c) {
        tour = new Tournament();
        System.out.println("\n****** HVAD VIL DU REGISTRERE? ******");
        System.out.println(enter + "I" + to + "registrere info til turneringen");
        System.out.println(enter + "H" + to + "registrere et hold");
        System.out.println(enter + "S" + to + "registrere spillere");

        userInput = new StringBuilder(scan.nextLine());
        if (userInput.toString().equalsIgnoreCase("I")) {
            tour.insertTournament(c);
        } else if (userInput.toString().equalsIgnoreCase("H")) {
            t.insertTeams(c);
        } else if (userInput.toString().equalsIgnoreCase("S")) {
            p.insertPlayer(c);
        } else {
            System.out.println(error);
        }
    }

    //#FELICIA - method to show delete menu
    public void deleteFromDbMenu(Connection c) {

        try {
            for (int i = 0; i < 100; i++) {
                System.out.println("\n****** HVAD VIL DU SLETTE? ******");
                System.out.println(enter + "A" + to + "slette alt information om turneringen.");
                System.out.println(enter + "R" + to + "slette alt information om kampene");
                System.out.println(enter + "H" + to + "slette alle holdene");
                System.out.println(enter + "Y" + to + "slette alle spillere");
                System.out.println(enter + "K" + to + "slette alle spillere, der er tilknyttet et hold");
                System.out.println(enter + "F" + to + "slette et hold ud fra holdID");
                System.out.println(enter + "P" + to + "slette et hold ud fra holdnavn");
                System.out.println(enter + "T" + to + "slette en spiller fra spillerID");
                System.out.println(enter + "S" + to + "slette en spiller fra et spillernavn");
                System.out.println(enter + "W" + to + "slette en spiller fra et hold");
                System.out.println(enter + "Q" + to + "afslutte programmet");

                String s = scan.nextLine();
                PreparedStatement delete;

                if (s.equalsIgnoreCase("A")) {
                    s = "tournamentinfo";
                    delete = c.prepareStatement("TRUNCATE TABLE " + s);
                    delete.execute();
                    delete.close();
                    System.out.println("Du har nu slettet alt information om turneringen.");
                }
                if (s.equalsIgnoreCase("R")) {
                    s = "rounds";
                    delete = c.prepareStatement("TRUNCATE TABLE " + s);
                    delete.execute();
                    delete.close();
                    System.out.println("Du har nu slettet alt information om kampene.");
                }
                if (s.equalsIgnoreCase("H")) {
                    s = "teams";
                    delete = c.prepareStatement("TRUNCATE TABLE " + s);
                    delete.execute();
                    delete.close();
                    System.out.println("Du har nu slettet alle hold.");
                }
                if (s.equalsIgnoreCase("Y")) {
                    s = "players";
                    delete = c.prepareStatement("TRUNCATE TABLE " + s);
                    delete.execute();
                    delete.close();
                    System.out.println("Du har nu slettet alle spillere.");
                }
                if (s.equalsIgnoreCase("K")) {
                    s = "teamplayers";
                    delete = c.prepareStatement("TRUNCATE TABLE " + s);
                    delete.execute();
                    delete.close();
                    System.out.println("Du har nu slettet alt data fra teamplayers.");
                }
                if (s.equalsIgnoreCase("F")) {
                    d.deleteTeamFromID(c);
                }
                if (s.equalsIgnoreCase("P")) {
                    d.deleteTeamFromName(c);
                }
                if (s.equalsIgnoreCase("T")) {
                    d.deletePlayerFromID(c);
                }
                if (s.equalsIgnoreCase("S")) {
                    d.deletePlayerFromName(c);
                }
                if (s.equalsIgnoreCase("W")) {
                    d.deletePlayerFromTeam(c);
                }
                if (s.equalsIgnoreCase("Q")) {
                    System.out.println("Du har nu afsluttet.");
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println(error);
            e.printStackTrace();
        }
    }

    //#FELICIA - method to show search menu
    public void searchFromDbMenu(Connection c) {
        System.out.println("\n****** HVAD VIL DU SØGE PÅ? ******");
        System.out.println(enter + "H" + to + "søge på et hold ");
        System.out.println(enter + "R" + to + "søge på en kamp");

        userInput = new StringBuilder(scan.nextLine());
        if (userInput.toString().equalsIgnoreCase("H")) {
            t.searchForTeamLike(c);
        }else if (userInput.toString().equalsIgnoreCase("R")) {
            r.searchForRoundLike(c);
        } else {
            System.out.println(error);
        }
    }

    //#FELICIA - method to show add menu
    public void addFromDbMenu(Connection c) {
        sc = new ScoreAndPoints();
        System.out.println("\n****** HVAD VIL DU TILFØJE? ******");
        System.out.println(enter + "R" + to + "tilføje en kamp");
        System.out.println(enter + "P" + to + "tilføje spiller til et hold");
        System.out.println(enter + "T" + to + "tilføje hold til kampe");
        System.out.println(enter + "S" + to + "tilføje score til kampe");

        userInput = new StringBuilder(scan.nextLine());
        if (userInput.toString().equalsIgnoreCase("R")) {
            r.insertRoundsInfo(c);
        } else if (userInput.toString().equalsIgnoreCase("P")) {
            p.addPlayerToTeam(c);
        } else if (userInput.toString().equalsIgnoreCase("T")) {
            sc.addTeamsToRounds(c);
        } else if (userInput.toString().equalsIgnoreCase("S")) {
            sc.updateScoreToRounds(c);
        } else {
            System.out.println(error);
        }
    }
}