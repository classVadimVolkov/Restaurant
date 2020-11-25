package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.statistic.StatisticManager;

import java.text.SimpleDateFormat;
import java.util.*;

public class DirectorTablet {
    public void printAdvertisementProfit() {
        StatisticManager statisticManager = StatisticManager.getInstance();
        Map<Date, Long> map = new TreeMap<>(Collections.reverseOrder());
        map.putAll(statisticManager.statisticAdvertisementProfitPerDay());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        map.forEach((k, v) -> {
            if (v == 0) {
                map.remove(k);
            }
        });

        for (Date date : map.keySet()) {
            Long amountInKopecks = map.get(date);
            double amount = ((double) amountInKopecks)/100;
            String amountText = String.format("%.2f", amount).replaceAll(",", ".");
            ConsoleHelper.writeMessage(dateFormat.format(date) + " - " + amountText);
        }

        Long totalAmountInKopecks = map.values().stream().mapToLong(Long::longValue).sum();
        double totalAmount = ((double) totalAmountInKopecks)/100;
        String totalAmountText = String.format("%.2f", totalAmount).replaceAll(",", ".");
        ConsoleHelper.writeMessage("Total - " + totalAmountText);
    }

    public void printCookWorkloading() {
        StatisticManager statisticManager = StatisticManager.getInstance();
        Map<Date, Map<String, Integer>> map = new TreeMap<>(Collections.reverseOrder());
        map.putAll(statisticManager.statisticCookWorkPerDay());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

        for (Date date : map.keySet()) {
            Map<String, Integer> mapOfCooks = new TreeMap<>();
            ConsoleHelper.writeMessage(dateFormat.format(date));
            mapOfCooks.putAll(map.get(date));
            mapOfCooks.forEach((k, v) -> {
                if (v == 0) {
                    mapOfCooks.remove(k);
                }
            });
            for (String nameCook : mapOfCooks.keySet()) {
                int totalCookingTimeInSeconds = mapOfCooks.get(nameCook).intValue();
                int totalCookingTimeInMinutes = (int) Math.ceil(totalCookingTimeInSeconds/60);
                ConsoleHelper.writeMessage(nameCook + " - " + totalCookingTimeInMinutes + " min");
            }
            ConsoleHelper.writeMessage("");
        }

    }

    public void printActiveVideoSet() {

    }

    public void printArchivedVideoSet() {

    }
}
