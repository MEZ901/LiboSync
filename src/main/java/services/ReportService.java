package src.main.java.services;

import src.main.java.repository.Model;
import src.main.java.repository.StatisticsRepository;
import src.main.java.utilities.DisplayTable;

import java.util.Map;

public class ReportService {
    public void statistics() {
        StatisticsRepository statisticsRepository = new StatisticsRepository();

        Map<String, Object> statistics = statisticsRepository.statistics();

        System.out.println("\n+---------------------------------------------------------------------------------------------------------+");
        System.out.println("|  Total Books  |  Available Books  |  Borrowed Books  |  Lost Books  |  Total Members  |  Total Authors  |");
        System.out.println("+---------------------------------------------------------------------------------------------------------+");

        System.out.printf("| %13s | %17s | %16s | %12s | %15s | %15s |\n",
                statistics.get("totalBooks"), statistics.get("availableBooks"),
                statistics.get("borrowedBooks"), statistics.get("lostBooks"),
                statistics.get("totalMembers"), statistics.get("totalAuthors"));
        System.out.println("+---------------------------------------------------------------------------------------------------------+\n");

        DisplayTable.callToAction();
    }
}
