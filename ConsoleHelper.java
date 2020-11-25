package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    // для вывода message в консоль
    public static void writeMessage(String message) {
        System.out.println(message);
    }

    //для чтения строки с консоли
    public static String readString() throws IOException {
        return bufferedReader.readLine();
    }

    // просит пользователя выбрать блюдо и добавляет его в список
    public static List<Dish> getAllDishesForOrder() throws IOException {
        ConsoleHelper.writeMessage(Dish.allDishesToString());
        ConsoleHelper.writeMessage("Choose dishes and then type 'exit':");
        List<Dish> listOfDishesForOrder = new ArrayList<>();

        while (true) {
            String dishName = ConsoleHelper.readString().trim();
            if (dishName.equals("exit")) {
                break;
            }
            try {
                Dish dish = Dish.valueOf(dishName);
                listOfDishesForOrder.add(dish);
                writeMessage(dishName + " has been successfully added to your order");
            } catch (Exception e) {
                writeMessage(dishName + " hasn't been detected");
            }
        }


        return listOfDishesForOrder;
    }

}
