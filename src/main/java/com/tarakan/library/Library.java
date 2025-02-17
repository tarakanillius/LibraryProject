package com.tarakan.library;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Library {
    private List<Book> books = new ArrayList<>();
    private static final String FILE_NAME = "books.json";
    ObjectMapper objectMapper = new ObjectMapper();
    static Scanner scanner = new Scanner(System.in);
    Map<Integer, Book> bookMap = new HashMap<>();

    public Library() {
        loadBooksFromFile();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveBooksToFile();
            System.out.println("\nBooks saved on exit.");
        }));
    }

    private void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }
        System.out.println(Arrays.toString(books.toArray()));
    }

    private void changeBookStatus() {
        System.out.print("Enter book ID: ");
        int bookIdToChange = getValidIntInput(0, books.size() - 1);
        Book book = bookMap.get(bookIdToChange);
        if (book != null) {
            System.out.print("Enter new status (0 - available, 1 - unavailable, 2 - reserved): ");
            int newStatus = getValidStatus();
            book.setStatus(newStatus);
            System.out.println("Book status updated successfully!");
        } else {
            System.out.println("Book not found.");
        }
    }

    private void addNewBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter publication year: ");
        int year = getValidIntInput(0, java.time.LocalDate.now().getYear());
        System.out.print("Enter quantity: ");
        int quantity = getValidIntInput(0, 999999);
        System.out.print("Enter price: ");
        double price = getValidDoubleInput();
        Book newBook = new Book(this.getBooks().size(), title, author, year, quantity, price, 0);
        System.out.println("Book added successfully!");
        books.add(newBook);
    }

    private void removeBook() {
        System.out.print("Enter book ID to remove: ");
        int bookIdToRemove = getValidIntInput(0, books.size()-1);
        System.out.println("Book removed successfully!");
        books.removeIf(book -> book.getId() == bookIdToRemove);
    }

    private void searchBooks() {
        System.out.print("Enter keyword to search (title/author): ");
        String keyword = scanner.nextLine();
        List<Book> searchResults = books.stream()
                .filter(book -> book.getTitle().contains(keyword) || book.getAuthor().contains(keyword))
                .toList();
        if (!searchResults.isEmpty()) {
            searchResults.forEach(System.out::println);
        } else {
            System.out.println("No books found matching the keyword.");
        }
    }

    public void start(){
        while (true) {
            System.out.print("""
                    \nChoose an option:
                    1. Display All Books
                    2. Change Book Status
                    3. Add Book
                    4. Remove Book
                    5. Search Books
                    6. Exit
                    Enter your choice:\s""");

            int choice = getValidIntInput(1, 6);
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayBooks();
                    break;
                case 2:
                    changeBookStatus();
                    break;
                case 3:
                    addNewBook();
                    break;
                case 4:
                    removeBook();
                    break;
                case 5:
                    searchBooks();
                    break;
                case 6:
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private static int getValidIntInput(int min, int max) {
        int input;
        while (true) {
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                if (input >= min && input <= max) {
                    break;
                } else {
                    System.out.print("Please enter a number between " + min + " and " + max + ": ");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
        }
        return input;
    }

    private static double getValidDoubleInput() {
        double input;
        while (true) {
            if (scanner.hasNextDouble()) {
                input = scanner.nextDouble();
                if (input >= (double) 0 && input <= (double) 999999) {
                    break;
                } else {
                    System.out.print("Please enter a number between " + (double) 0 + " and " + (double) 999999 + ": ");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
        }
        return input;
    }

    private static int getValidStatus() {
        int status;
        do {
            status = getValidIntInput(0,3);
            if (status < 0 || status > 2) {
                System.out.print("Invalid status. Please enter 0, 1, or 2: ");
            }
        } while (status < 0 || status > 2);
        return status;
    }

    private void loadBooksFromFile() {
        try {
            books = objectMapper.readValue(new File(FILE_NAME), objectMapper.getTypeFactory().constructCollectionType(List.class, Book.class));
        } catch (IOException e) {
            System.out.println("Error loading books from file: " + e.getMessage());
        }
    }

    private List<Book> getBooks() {
        return books;
    }

    private void saveBooksToFile() {
        try {
            objectMapper.writeValue(new File(FILE_NAME), books);
        } catch (IOException e) {
            System.out.println("Error saving books to file: " + e.getMessage());
        }
    }
}
