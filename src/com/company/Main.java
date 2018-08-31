package com.company;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    private static final String BESTAND = "films.txt";

    public static void main(String[] args) throws IOException {
        ArrayList<Film> films = new ArrayList<>();
        HashSet<Genre> datagenre = new HashSet<>();
        HashSet<Country> datacountry = new HashSet<>();
        HashSet<director> datadirector = new HashSet<>();

        try (Scanner reader = new Scanner(new BufferedReader(new FileReader(BESTAND)))) {
            while (reader.hasNextLine()) {
                String rec = reader.nextLine();

                String title = rec.substring(rec.indexOf("<title>") + 7, rec.indexOf("<genre>"));
                String genre = rec.substring(rec.indexOf("<genre>") + 7, rec.indexOf("<date>"));
                String rd = rec.substring(rec.indexOf("<date>") + 6, rec.indexOf("<country>"));
                LocalDate releaseDate;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                if (rd.isEmpty())
                    releaseDate = null;
                else
                    releaseDate = LocalDate.parse(rd, formatter);

                String country = rec.substring(rec.indexOf("<country>") + 9, rec.indexOf("<director>"));
                String director = rec.substring(rec.indexOf("<director>") + 10, rec.length());

                Film film = new Film(title, genre, releaseDate, country, director);
                films.add(film);
                datagenre.add(new Genre(genre));
                datacountry.add(new Country(country));
                datadirector.add(new director(director));
            }
        }
        Scanner scanner = new Scanner(System.in);
        String opermenu = "1.New 2.List 3.Delete 4.Save 5.Return";
        String user;
        String greeting = getGreeting();

        System.out.print("Please enter your name: ");
        user = scanner.nextLine();

        System.out.printf("\t\tGood %s %s%n", greeting, user);

        int optie = 0;
        System.out.print("\t\t\t\t\t\t\t\t\t\tChoose your option: ");

        showMainMenu();

        while (optie != 5) {
            optie = scanner.nextInt();
            if (optie == 1) {
                optie = 0;
                while (optie != 5) {
                    if (optie == 1) { // New film
                        Film film = getFilmData();
                        if (film != null) {
                            films.add(film);
                        }
                    }
                    if (optie == 2) { // List films
                        for (int i = 0; i < films.size(); i++) {
                            Film f = films.get(i);
                            System.out.printf("       Title: %s%n       Genre: %s%nRelease date: %s%n     Country: %s%n    Director: %s%n%n", f.getTitle(), f.getGenre(), f.getReleaseDate(), f.getCountry(), f.getDirector());
                        }
                    }
                    if (optie == 4) { // Save file
                        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(BESTAND)))) {
                            for (int i = 0; i < films.size(); i++) {
                                Film f = films.get(i);
                                writer.printf("<title>%s<genre>%s<date>%s<country>%s<director>%s%n", f.getTitle(), f.getGenre(), f.getReleaseDate(), f.getCountry(), f.getDirector());
                                System.out.println("File is saved");
                            }
                        }
                    }

                    System.out.printf("%n\t\t\tSubmenu Film%n");
                    System.out.println(opermenu);
                    optie = scanner.nextInt();
                }
                optie = 0;
                showMainMenu();
            }
            if (optie == 2) {
                optie = 0;

                while (optie != 5) {
                    if (optie == 2) { // List genres
                        System.out.printf("%n Existing Genres:%n");
                        for (Genre genre : datagenre) {
                            System.out.printf(" + %s%n", genre.getGenre());
                        }
                    }

                    System.out.printf("%n%n\t\t\tSubmenu Genre%n");
                    System.out.println(opermenu);
                    optie = scanner.nextInt();
                }
                optie = 0;
                showMainMenu();
            }
            if (optie == 3) {
                optie = 0;

                while (optie != 5) {
                    if (optie == 2) { // List countries
                        System.out.printf("%n Origin:%n");
                        for (Country country : datacountry) {
                            System.out.printf(" + %s%n", country.getCountry());
                        }
                    }

                    System.out.printf("%n%n\t\t\tSubmenu Country%n");
                    System.out.println(opermenu);
                    optie = scanner.nextInt();
                }
                optie = 0;
                showMainMenu();
            }
            if (optie == 4) {
                optie = 0;

                while (optie != 5) {
                    if (optie == 2) { // List directors
                        System.out.printf("%n Directors:%n");
                        for (director director : datadirector) {
                            System.out.printf(" + %s%n", director.getDirector());
                        }
                    }

                    System.out.printf("%n%n\t\t\tSubmenu Director%n");
                    System.out.println(opermenu);
                    optie = scanner.nextInt();
                }
                optie = 0;
                showMainMenu();
            }
        }
        System.out.printf("%n%nThank you for using this program. Till next time.");
    }

    private static Film getFilmData() { // Option 1 - New
        Scanner scanner = new Scanner(System.in);
        String title;
        String genre;
        LocalDate releaseDate;
        String country;
        String director;
        String opermenu = "                      4.Save&Return 5.Return without saving ";
        int optie = 0;

        System.out.println("Entering film data ");
        System.out.print("\tTitel: ");
        title = scanner.nextLine();
        System.out.print("\tGenre: ");
        genre = scanner.nextLine();
        System.out.print("\tRelease date (dd-mm-yyyy): ");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String t = scanner.nextLine();
        if (t.isEmpty())
            releaseDate = null;
        else
            releaseDate = LocalDate.parse(t, formatter);

        System.out.print("\tCountry: ");
        country = scanner.nextLine();
        System.out.print("\tDirector: ");
        director = scanner.nextLine();

        System.out.print(opermenu);
        while (optie != 4 && optie != 5) {
            optie = scanner.nextInt();
        }
        if (optie == 4) {
            Film f = new Film(title, genre, releaseDate, country, director);
            return f;
        }
        System.out.print(" Exiting without saving data");
        return null;
    }

    static void showMainMenu() {
        String[] mainmenu = {"1.Film", "2.Genre", "3.Country", "4.Director", "5.Exit"};
        for (int i = 0; i < mainmenu.length; i++) {
            System.out.printf("%n\t\t\t\t\t %s%n", mainmenu[i]);
        }
    }

    static String getGreeting() {
        String greeting;
        LocalDateTime dagUur = LocalDateTime.now();
        if (dagUur.getHour() > 5 && dagUur.getHour() < 12) {
            greeting = "Morning";
        } else if (dagUur.getHour() >= 12 && dagUur.getHour() < 18) {
            greeting = "Evening";
        } else greeting = "Night";
        return greeting;
    }
}

class Film {
    private String title;
    private String genre;
    private LocalDate releaseDate;
    private String country;
    private String director;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Film(String title, String genre, LocalDate releaseDate, String country, String director) {
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.country = country;
        this.director = director;
    }
}