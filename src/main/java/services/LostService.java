package src.main.java.services;

import src.main.java.repository.Model;
import src.main.java.utilities.DisplayTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LostService {
    Model model = new Model("lost_book");
    LibraryService libraryService = new LibraryService();
    MemberService memberService = new MemberService();
    BorrowService borrowService = new BorrowService();

    public void declareAsLost() {
        Scanner scanner = new Scanner(System.in);
        Map<String, Object> whereCriteria = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> lostBook;
        List<Map<String, Object>> book;
        List<Map<String, Object>> member;
        int choice;
        int ch;

        System.out.println("\n================= Declare book as lost =================\n");
        System.out.println("How does the book got lost?");
        System.out.println("1. The book was lost in the library");
        System.out.println("2. The book was taken by a member and they never returned it");

        do {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    book = libraryService.findBook(whereCriteria);
                    DisplayTable.displayBooks(book);

                    if ((int) book.get(0).get("quantity") == 0) {
                        System.out.println("The book's quantity is currently at 0; it cannot be declared lost.");

                        DisplayTable.callToAction();
                        return;
                    }

                    System.out.println("1. Declare the book as lost");
                    System.out.println("2. Cancel");

                    do {
                        System.out.print("Enter your choice: ");
                        ch = scanner.nextInt();
                        scanner.nextLine();

                        switch (ch) {
                            case 1:
                                lostBook = model.find(whereCriteria, null);
                                if (!lostBook.isEmpty()) {
                                    int newQuantity = (int) lostBook.get(0).get("quantity") + 1;
                                    data.put("quantity", newQuantity);
                                    model.update(data, whereCriteria);
                                } else {
                                    data.put("isbn", book.get(0).get("isbn"));
                                    data.put("quantity", 1);
                                    libraryService.decrementBookQuantity(whereCriteria, (int) book.get(0).get("quantity"), 1);
                                    model.insert(data);
                                }

                                System.out.println("\n\u001B[32mThe book has been declared as lost successfully\u001B[0m");
                                break;
                            case 2:
                                return;
                            default:
                                System.out.println("Invalid choice, please try again");
                                break;
                        }
                    } while (ch != 1);
                    break;
                case 2:
                    book = libraryService.findBook(whereCriteria);
                    if (book.isEmpty()) return;
                    member = memberService.findMember();
                    if (member.isEmpty()) return;

                    whereCriteria.put("member_id", member.get(0).get("id"));
                    whereCriteria.put("has_been_returned", false);

                    List<Map<String, Object>> reservation = borrowService.findReservation(book, member, whereCriteria);
                    DisplayTable.displayReservation(reservation);

                    System.out.println("1. Declare the book as lost");
                    System.out.println("2. Cancel");

                    do {
                        System.out.print("Enter your choice: ");
                        ch = scanner.nextInt();
                        scanner.nextLine();

                        switch (ch) {
                            case 1:
                                Map<String, Object> whereCriteriaOnlyIsbn = new HashMap<>();
                                whereCriteriaOnlyIsbn.put("isbn", book.get(0).get("isbn"));

                                lostBook = model.find(whereCriteriaOnlyIsbn, null);

                                if (!lostBook.isEmpty()) {
                                    int newQuantity = (int) lostBook.get(0).get("quantity") + 1;
                                    data.put("quantity", newQuantity);
                                    model.update(data, whereCriteriaOnlyIsbn);
                                } else {
                                    data.put("isbn", book.get(0).get("isbn"));
                                    data.put("quantity", 1);
                                    model.insert(data);
                                }

                                borrowService.declareReservationAsStolen(whereCriteria);
                                libraryService.updateBookStatus(whereCriteriaOnlyIsbn);

                                System.out.println("\n\u001B[32mThe book has been declared as lost successfully\u001B[0m");
                                break;
                            case 2:
                                return;
                            default:
                                System.out.println("Invalid choice, please try again");
                                break;
                        }
                    } while (ch != 1);
                    break;
                default:
                    System.out.println("Invalid choice, please try again");
                    break;
            }
        } while(choice != 1 && choice != 2);
    }
}
