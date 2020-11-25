package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.statistic.StatisticManager;
import com.javarush.task.task27.task2712.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.Observer;

public class Cook extends Observable implements Observer {
    private final String name;

    public Cook(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object objOrder) {
        Order order = (Order) objOrder;

        ConsoleHelper.writeMessage("Start cooking - " + order + ", cooking time " +
                order.getTotalCookingTime() + "min");

        CookedOrderEventDataRow cookedOrderEventDataRow = new CookedOrderEventDataRow(o.toString(),
                name, order.getTotalCookingTime() * 60,
                order.getDishes());
        StatisticManager.getInstance().register(cookedOrderEventDataRow);

        setChanged();
        notifyObservers(order);

    }

    @Override
    public String toString() {
        return name;
    }
}
