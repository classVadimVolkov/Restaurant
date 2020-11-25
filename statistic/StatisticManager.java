package com.javarush.task.task27.task2712.statistic;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventDataRow;
import com.javarush.task.task27.task2712.statistic.event.EventType;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticManager {
    private StatisticStorage statisticStorage = new StatisticStorage();
    private Set<Cook> cooks = new HashSet<>();

    private StatisticManager() {

    }

    public void register(Cook cook) {
        cooks.add(cook);
    }

    private static class SingletonHolder {
        private static final StatisticManager statisticManager = new StatisticManager();
    }

    public static StatisticManager getInstance() {
        final StatisticManager statisticManager = SingletonHolder.statisticManager;
        return statisticManager;
    }

    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }

    public Map<Date, Long> statisticAdvertisementProfitPerDay() {
        List<EventDataRow> eventDataRowList = statisticStorage.get(EventType.SELECTED_VIDEOS);
        Map<Date, Long> map = new HashMap<>();
        Date oldDate = null;

        for (EventDataRow eventDataRow : eventDataRowList) {
            long amount = ((VideoSelectedEventDataRow) eventDataRow).getAmount();
            Date date = ((VideoSelectedEventDataRow) eventDataRow).getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            if (map.isEmpty()) {
                map.put(date, new Long(0));
                oldDate = date;
            }
            if (!dateFormat.format(date).equals(dateFormat.format(oldDate))) {
                map.put(date, new Long(amount));
                oldDate = date;
            } else {
                map.computeIfPresent(oldDate, (k, v) -> v + amount);
            }

        }

        return map;
    }

    public Map<Date, Map<String, Integer>> statisticCookWorkPerDay() {
        List<EventDataRow> eventDataRowList = statisticStorage.get(EventType.COOKED_ORDER);
        Map<Date, Map<String, Integer>> mapToReturn = new HashMap<>();
        Map<String, Integer> mapOfCooks = new HashMap<>();
        Date oldDate = null;

        for (EventDataRow eventDataRow : eventDataRowList) {
            String cookName = ((CookedOrderEventDataRow) eventDataRow).getCookName();
            int cookingTimeSeconds = ((CookedOrderEventDataRow) eventDataRow).getTime();
            Date date = ((CookedOrderEventDataRow) eventDataRow).getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            if (mapOfCooks.isEmpty()) {
                mapOfCooks.put(cookName, new Integer(0));
            }
            mapOfCooks.putIfAbsent(cookName, cookingTimeSeconds);
            mapOfCooks.computeIfPresent(cookName, (k, v) -> v + cookingTimeSeconds);
            if (mapToReturn.isEmpty()) {
                mapToReturn.put(date, mapOfCooks);
                oldDate = date;
            }
            if (!dateFormat.format(date).equals(dateFormat.format(oldDate))) {
                mapToReturn.put(date, mapOfCooks);
                mapOfCooks.clear();
            }
        }
        return mapToReturn;
    }


    private class StatisticStorage {
        private Map<EventType, List<EventDataRow>> storage = new HashMap<>();

        private StatisticStorage() {
            for (EventType eventType : EventType.values()) {
                storage.put(eventType, new ArrayList<EventDataRow>());
            }
        }

        private void put(EventDataRow data) {
            if (storage.containsKey(data.getType())) {
                storage.get(data.getType()).add(data);
            }
        }

        public List<EventDataRow> get(EventType eventType) {
            return storage.get(eventType);
        }
    }

}
