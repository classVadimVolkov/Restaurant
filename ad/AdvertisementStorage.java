package com.javarush.task.task27.task2712.ad;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementStorage {
    private final List<Object> videos = new ArrayList<>();

    public List<Object> list() {
        return videos;
    }

    public void add(Advertisement advertisement) {
        videos.add(advertisement);
    }


    private static class SingletonHolder {
        private static final AdvertisementStorage advertisementStorage = new AdvertisementStorage();
    }


    private AdvertisementStorage() {
        Object someContent = new Object();
        videos.add(new Advertisement(someContent, "First Video", 5000, 100, 3 * 60)); // 3 min
        videos.add(new Advertisement(someContent, "Second Video", 100, 10, 15 * 60)); //15 min
        videos.add(new Advertisement(someContent, "Third Video", 400, 2, 10 * 60)); //10 min
    }

    public static AdvertisementStorage getInstance() {
        return SingletonHolder.advertisementStorage;
    }
}

