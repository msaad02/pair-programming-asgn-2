import exception.InvalidPrimaryKeyException;
import model.Book;
import model.Patron;
import model.BookCollection;
import model.PatronCollection;

import java.util.InputMismatchException;
import java.util.Vector;
import java.util.Properties;
import java.util.Scanner;

public class TestProj1 {
    public static void main(String[] args) {

        // Init BookCollection
        BookCollection bookCollection = new BookCollection();
        PatronCollection patronCollection = new PatronCollection();

        // Init Scanner obj
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Pair Programming 1 Assignment!");


        int userOption = 100; // Will be over-written
        String userInput = "Y";
        boolean printList; //book printer
        boolean patPrintList; //Patron printer
        Vector<Book> bookList = null;
        Vector<Patron> patronList = null;

        while (userInput.equals("Y") || userInput.equals("y")) {

            System.out.println("\nPlease enter one of the following options.");

            // Books
            System.out.println("1) findBooksOlderThanDate");
            System.out.println("2) findBooksNewerThanDate");
            System.out.println("3) findBooksWithTitleLike");
            System.out.println("4) findBooksWithAuthorLike");

            // Patrons
            System.out.println("5) findPatronsOlderThan");
            System.out.println("6) findPatronsYoungerThan");
            System.out.println("7) findPatronsAtZipCode");
            System.out.println("8) findPatronsWithNameLike");

            // Insert into Book/Patron
            System.out.println("9) insertNewBook");
            System.out.println("10) insertNewPatron");

            System.out.print("\nChoice: ");

            try {
                userOption = input.nextInt();
            } catch (InputMismatchException e) {
                userOption = -1;
                System.out.println("ERROR: Only characters are allowed. Please try again.");
                input.nextLine();
            }

            printList = true;
            patPrintList = true;

            if (userOption == 1) { // findBooksOlderThanDate

                System.out.print("\nYou have selected 'findBooksOlderThanDate'. Please enter a year: ");
                userInput = input.next();

                bookList = bookCollection.findBooksOlderThanDate(userInput);

                if (bookList.isEmpty()) {
                    printList = false;
                    System.out.println("No books are older than " + userInput);
                }

                patPrintList = false;

            } else if (userOption == 2) { // findBooksNewerThanDate

                System.out.print("\nYou have selected 'findBooksNewerThanDate'. Please enter a year: ");
                userInput = input.next();

                bookList = bookCollection.findBooksNewerThanDate(userInput);

                if (bookList.isEmpty()) {
                    printList = false;
                    System.out.println("No books are newer than " + userInput);
                }

                patPrintList = false;

            } else if (userOption == 3) { // findBooksWithTitleLike

                System.out.print("\nYou have selected 'findBooksWithTitleLike'. Please enter a phrase: ");
                userInput = input.next();

                bookList = bookCollection.findBooksWithTitleLike(userInput);

                if (bookList.isEmpty()) {
                    printList = false;
                    System.out.println("No titles contain the phrase '" + userInput + "'.");
                }

                patPrintList = false;

            } else if (userOption == 4) { // findBooksWithAuthorLike

                System.out.print("\nYou have selected 'findBooksWithAuthorLike'. Please enter a phrase: ");
                userInput = input.next();

                bookList = bookCollection.findBooksWithAuthorLike(userInput);

                if (bookList.isEmpty()) {
                    printList = false;
                    System.out.println("No authors contain the phrase '" + userInput + "'.");
                }

                patPrintList = false;

            } else if (userOption == 5) { // findPatronsOlderThan

                System.out.print("\nYou have selected 'findPatronsOlderThan'. Please enter a year: ");
                userInput = input.next();

                patronList = patronCollection.findPatronsOlderThan(userInput);

                if (patronList.isEmpty()) {
                    patPrintList = false;
                    System.out.println("No Patrons are older than " + userInput);
                }

                printList = false;

            } else if (userOption == 6) { // findPatronsYoungerThan

                System.out.print("\nYou have selected 'findPatronsYoungerThan'. Please enter a year: ");
                userInput = input.next();

                patronList = patronCollection.findPatronsYoungerThan(userInput);

                if (patronList.isEmpty()) {
                    patPrintList = false;
                    System.out.println("No Patrons are younger than " + userInput);
                }

                printList = false;

            } else if (userOption == 7) { // findPatronsAtZipCode

                System.out.print("\nYou have selected 'findPatronsAtZipCode'. Please enter a zip code: ");
                userInput = input.next();

                patronList = patronCollection.findPatronsAtZipCode(userInput);

                if (patronList.isEmpty()) {
                    patPrintList = false;
                    System.out.println("No Patrons live in " + userInput + " zip code area");
                }

                printList = false;

            } else if (userOption == 8) { // findPatronsWithNameLike

                System.out.print("\nYou have selected 'findPatronsWithNameLike'. Please enter a name: ");
                userInput = input.next();

                patronList = patronCollection.findPatronsWithNameLike(userInput);

                if (patronList.isEmpty()) {
                    patPrintList = false;
                    System.out.println("No Patrons with names like " + userInput);
                }

                printList = false;

            } else if (userOption == 9) { // insertNewBook

                System.out.println("\nYou have selected 'insertNewBook'");

                input.nextLine();
                Properties newBookInfo = new Properties();

                System.out.print("Please enter a new book title: ");
                newBookInfo.setProperty("bookTitle", input.nextLine());

                System.out.print("Please enter a new book Author: ");
                newBookInfo.setProperty("author", input.nextLine());

                System.out.print("Please enter a new Publication Year: ");
                newBookInfo.setProperty("pubYear", input.nextLine());

                Book newBook = new Book(newBookInfo);
                newBook.update();

                printList = false;
                patPrintList = false;

            } else if (userOption == 10) { // insertNewPatron

                System.out.println("\nYou have selected 'insertNewPatron'");

                Properties newPatronInfo = new Properties();
                input.nextLine();

                System.out.print("Please enter a new patron name: ");
                newPatronInfo.setProperty("name", input.nextLine());

                System.out.print("Please enter a new patron address: ");
                newPatronInfo.setProperty("address", input.nextLine());

                System.out.print("Please enter a new patron city: ");
                newPatronInfo.setProperty("city", input.nextLine());

                System.out.print("Please enter a new patron state code: ");
                newPatronInfo.setProperty("stateCode", input.nextLine());

                System.out.print("Please enter a new patron zip code: ");
                newPatronInfo.setProperty("zip", input.nextLine());

                System.out.print("Please enter a new patron email: ");
                newPatronInfo.setProperty("email", input.nextLine());

                System.out.print("Please enter a new patron date of birth: ");
                newPatronInfo.setProperty("dateOfBirth", input.nextLine());

                Patron newPatron = new Patron(newPatronInfo);
                newPatron.update();

                printList = false;
                patPrintList = false;

            } else if (userOption == -1) {
                // SPECIAL CASE. If -1 then just continue with the loop and don't print anything.
                continue;
            } else {
                printList = false;
                patPrintList = false;
                System.out.println("ERROR: " + userOption + " is NOT YET IMPLEMENTED.");
            }


            // Print the bookList?
            if (printList) {
                for (int i = 0; i < bookList.size(); i++) {
                    System.out.println(bookList.elementAt(i));
                }
            }

            // Print the Patron list
            if (patPrintList) {
                for (int i = 0; i < patronList.size(); i++) {
                    System.out.println(patronList.elementAt(i));
                }
            }

            // Prompt user if they would like to run again.
            System.out.print("\nWould you like to run again? Y/n: ");
            userInput = input.next();
        }

        System.out.println("\nBye!");
    }

}

