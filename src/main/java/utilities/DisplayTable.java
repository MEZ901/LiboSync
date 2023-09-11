package src.main.java.utilities;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DisplayTable {
    public static void displayBooks(List<Map<String, Object>> books) {
        System.out.println("+-----------------------------------------------------------------------------------------+");
        System.out.println("|       ISBN      |         Title        |        Author        |  Quantity |    Status   |");
        System.out.println("+-----------------------------------------------------------------------------------------+");

        for (Map<String, Object> row : books) {
            System.out.printf("| %15s | %-20s | %-20s |  %8s | %-11s |\n",
                    row.get("isbn"), row.get("title"), row.get("name"),
                    row.get("quantity"), row.get("status"));
        }

        System.out.println("+-----------------------------------------------------------------------------------------+\n");
    }

    public static void displayMembers(List<Map<String, Object>> members) {
        System.out.println("+------------------------------------------------------------------------------------+");
        System.out.println("|   Member ID   |   First Name   |   Last Name   |   Gender   |   Membership Number  |");
        System.out.println("+------------------------------------------------------------------------------------+");

        for (Map<String, Object> row : members) {
            System.out.printf("|  %12s | %-14s | %-13s | %-10s |  %19s |\n",
                    row.get("id"), row.get("first_name"), row.get("last_name"),
                    row.get("gender"), row.get("membership_number"));
        }

        System.out.println("+------------------------------------------------------------------------------------+\n");

    }

    public static void callToAction() {
        Scanner scanner = new Scanner(System.in);
        int ch;

        System.out.println("1. Back to menu");
        System.out.println("0. Exit");

        do {
            System.out.print("Enter your choice: ");
            ch = scanner.nextInt();

            switch (ch) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    break;
                default:
                    System.out.println("You can only choose between 1 or 0");
                    break;
            }
        } while(ch != 1);
    }
}
