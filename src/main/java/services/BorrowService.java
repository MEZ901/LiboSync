package src.main.java.services;

import src.main.java.LibroSyncApp;
import src.main.java.models.Member;
import src.main.java.repository.Model;
import src.main.java.utilities.DisplayTable;

import java.text.SimpleDateFormat;
import java.util.*;

public class BorrowService {
    Model model = new Model("reservation");
    LibraryService libraryService = new LibraryService();
    MemberService memberService = new MemberService();

    public void borrowBook() {
        Scanner scanner = new Scanner(System.in);
        Map<String, Object> bookCriteria = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        List<Map<String, Object>> book = libraryService.findBook(bookCriteria);
        if (book.isEmpty()) {
            return;
        }

        DisplayTable.displayBooks(book);

        int choice;

        System.out.println("\n1. Continue with existing member");
        System.out.println("2. Create new member");

        do {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    List<Map<String, Object>> member = memberService.findMember();

                    DisplayTable.displayMembers(member);

                    int ch;

                    System.out.println("1. Confirm");
                    System.out.println("2. Cancel");

                    do {
                        System.out.print("Enter your choice: ");
                        ch = scanner.nextInt();

                        switch (ch) {
                            case 1:
                                // Get the current date
                                Date currentDate = new Date();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String formattedDate = dateFormat.format(currentDate);

                                data.put("isbn", "\"" + bookCriteria.get("isbn") + "\"");
                                data.put("member_id", member.get(0).get("id"));
                                data.put("borrowing_date", "\"" + formattedDate + "\"");
                                data.put("has_been_returned", false);
                                break;
                            case 2:
                                return;
                            default:
                                System.out.println("Invalid choice, please try again.");
                                break;
                        }
                    } while(ch != 1);

                    model.insert(data);

                    System.out.println("\n\u001B[32mThe reservation has been created successfully!\u001B[0m");
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 1 && choice != 2);
    }

    public void returnBook() {}
}
