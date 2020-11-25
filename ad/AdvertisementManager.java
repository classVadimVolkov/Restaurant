package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.NoAvailableVideoEventDataRow;
import com.javarush.task.task27.task2712.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds; // время приготовления заказа

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() {
        if (storage.list().isEmpty()) {
            StatisticManager.getInstance().register(new NoAvailableVideoEventDataRow(timeSeconds));
            throw new NoVideoAvailableException();
        }

        List<Advertisement> mainList = new ArrayList<>();
        storage.list().forEach((obj) -> mainList.add((Advertisement) obj));

        /*сортировка по стоимости одного показа рекламного объявления в копейках,
                затем по продолжительности показа*/
        mainList.sort(Comparator.comparingLong(Advertisement::getAmountPerOneDisplaying)
                .thenComparingInt(Advertisement::getDuration));
        Collections.reverse(mainList);

        int freeTime = timeSeconds;
        List<Advertisement> playList = new ArrayList<>();

        for (Advertisement video : mainList) {
            if (video.getDuration() <= freeTime && video.getAmountPerOneDisplaying() > 0) {
                playList.add(video);
                freeTime = freeTime - video.getDuration();
            }
        }

        long totalAmount = playList.stream().mapToLong(Advertisement::getAmountPerOneDisplaying).sum();
        int totalDuration = playList.stream().mapToInt(Advertisement::getDuration).sum();

        VideoSelectedEventDataRow videoSelectedEventDataRow =
                new VideoSelectedEventDataRow(playList, totalAmount, totalDuration);
        StatisticManager.getInstance().register(videoSelectedEventDataRow);

        for (Advertisement video : playList) {
            ConsoleHelper.writeMessage(video.getName() +
                    " is displaying... " + video.getAmountPerOneDisplaying() +
                    ", " +
                    video.getAmountPerOneDisplaying() * 1000 / video.getDuration());
            // TODO: без этой проверки вылетает исключение (нужна ли она?)
            if (video.getHits() > 0) {
                video.revalidate();
            }
        }

    }

}
