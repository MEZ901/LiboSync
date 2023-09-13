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
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> bookCriteria = new HashMap<>();

        List<Map<String, Object>> book = libraryService.findBook(bookCriteria);
        if (book.isEmpty()) {
            return;
        } else if ((int) book.get(0).get("quantity") == 0) {
            System.out.println("\n\u001B[31mThis book currently " + (book.get(0).get("status").equals("BORROWED") ? "borrowed" : "lost") + " you can't make any reservation\u001B[0m\n");

            System.out.println("1. back to menu");
            System.out.println("0. Exit");

            int ch;
            do {
                System.out.print("Enter your choice: ");
                ch = scanner.nextInt();
                scanner.nextLine();

                switch (ch) {
                    case 1:
                        return;
                    case 0:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                        break;
                }
            } while(true);
        }

        DisplayTable.displayBooks(book);

        int choice;
        List<Map<String, Object>> member = new ArrayList<>();

        System.out.println("\n1. Continue with existing member");
        System.out.println("2. Create new member");

        do {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    member = memberService.findMember();
                    break;
                case 2:
                    member = memberService.createMember();
                    System.out.println("\nThe member has been created successfully");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 1 && choice != 2);

        DisplayTable.displayMembers(member);

        int ch;

        System.out.println("1. Confirm");
        System.out.println("2. Cancel");

        do {
            System.out.print("Enter your choice: ");
            ch = scanner.nextInt();

            switch (ch) {
                case 1:
                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = dateFormat.format(currentDate);

                    data.put("isbn", bookCriteria.get("isbn"));
                    data.put("member_id", member.get(0).get("id"));
                    data.put("borrowing_date", formattedDate);
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
        libraryService.decrementBookQuantity(bookCriteria, (int) book.get(0).get("quantity"), 1);

        System.out.println("\n\u001B[32mThe reservation has been created successfully!\u001B[0m");
    }

    public void returnBook() {}
}
