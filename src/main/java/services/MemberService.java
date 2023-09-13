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

    public List<Map<String, Object>> createMember() {
        Scanner scanner = new Scanner(System.in);
        Map<String,Object> data = new HashMap<>();

        System.out.println("\n================= Create member =================\n");

        System.out.print("First name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last name: ");
        String lastName = scanner.nextLine();

        int gender;
        do {
            System.out.print("Gender (press 1 for male and 2 for female): ");
            gender = scanner.nextInt();
            scanner.nextLine();

            if (gender != 1 && gender != 2) {
                System.out.println("Please select 1 or 2");
            }
        } while (gender != 1 && gender != 2);

        System.out.print("Membership number: ");
        int membershipNumber = scanner.nextInt();
        scanner.nextLine();

        data.put("first_name", "\"" + firstName + "\"");
        data.put("last_name", "\"" + lastName + "\"");
        data.put("gender", gender == 1 ? "\"MALE\"" : "\"FEMALE\"");
        data.put("membership_number", membershipNumber);

        List<Map<String, Object>> member = model.insertAndReturn(data);

        return member;
    }
}
