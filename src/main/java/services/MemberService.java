package src.main.java.services;

import src.main.java.repository.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MemberService {
    Model model = new Model("member");

    public List<Map<String, Object>> findMember() {
        Scanner scanner = new Scanner(System.in);
        Map<String, Object> whereCriteria = new HashMap<>();
        List<Map<String, Object>> member;

        do {
            System.out.print("Enter the membership number: ");
            String membershipNumber = scanner.nextLine();
            whereCriteria.put("membership_number", membershipNumber);

            member = model.find(whereCriteria, null);

            if (member.isEmpty()) {
                System.out.println("\n\u001B[31mThere's no member has this membership number.\u001B[0m\n");
                System.out.println("1. Try again");
                System.out.println("2. Back to menu");

                int choice;
                do {
                    System.out.print("Enter your choice: ");
                    choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                            break;
                        case 2:
                            return member;
                    }
                } while(choice != 1);
            }
        } while (member.isEmpty());

        return member;
    }
}
