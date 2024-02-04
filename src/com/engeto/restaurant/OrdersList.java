package com.engeto.restaurant;

import java.io.*;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.ValueRange;
import java.util.*;

import static java.util.Comparator.comparing;


public class OrdersList {

    private List<Order> orderList = new ArrayList<>();

    public List<Order> getOrderList() {
        List<Order> i = orderList;
        return i;
    }
    public void addOrder(Order order) {
        orderList.add(order);
    }
    public void removeOrder(int index) {
        orderList.remove(index);
    }
    public Order getOrder(int index) {
        return orderList.get(index);
    }

    public long getSize() {
        return orderList.size();
    }


    public static OrdersList loadFromFile(String filename, Cookbook cookbook) throws RestaurantException {
        OrdersList result = new OrdersList();
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                parseLine(line, result, cookbook);
            }
        } catch (FileNotFoundException e) {
            throw new RestaurantException("Nepodařilo se nalézt soubor " + filename + ": " + e.getLocalizedMessage());
        }
        return result;
    }

    private static void parseLine(String line, OrdersList ordersList, Cookbook cookbook) throws RestaurantException {
        String[] blocks = line.split("\t");
        int numOfBlocks = blocks.length;
        int needNumOfBlocks = 8;
        if (numOfBlocks != needNumOfBlocks) {
            throw new RestaurantException(
                    "Nesprávný počet položek na řádku: " + line +
                            "! Počet položek: " + numOfBlocks + " z " + needNumOfBlocks + ".");
        }
            int id = Integer.parseInt(blocks[0].trim());
            int table = Integer.parseInt(blocks[1].trim());
            int orderDishNumber = Integer.parseInt(blocks[2].trim());
            int numberOfDish = Integer.parseInt(blocks[3].trim());
            LocalTime orderedTime = LocalTime.parse(blocks[4].trim());
            LocalTime fulfilmentTime = LocalTime.parse(blocks[5].trim());
            String isSortedOut = blocks[6].trim();
            String isPaid = blocks[7].trim();

        Order newOrder = new Order(id, table, orderDishNumber, numberOfDish,
                orderedTime, fulfilmentTime, isSortedOut, isPaid, cookbook);
        ordersList.addOrder(newOrder);
    }

    public void saveToFile (String filename) throws RestaurantException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
           for (Order order : orderList) {
               writer.println(order.getId() + Settings.getFileDelimiter() +
                       order.getTable() + Settings.getFileDelimiter() +
                       order.getOrderDishNumber() + Settings.getFileDelimiter() +
                       order.getNumberOfDish() + Settings.getFileDelimiter() +
                       order.getOrderedTime() + Settings.getFileDelimiter() +
                       order.getFulfilmentTime() + Settings.getFileDelimiter() +
                       order.getIsSortedOut() + Settings.getFileDelimiter() +
                       order.getIsPaid());
           }
        } catch (IOException e) {
            throw new RestaurantException("Chyba při zápisu do souboru '"
                    + filename + "' : " + e.getLocalizedMessage());
        }
    }

    public int countNotSortedOrder() {
        int i = 0;
        for (Order order : orderList) {
            if (order.getIsSortedOut().equals(YesOrNot.NO)) {
                i++;
            }
        }
        return i;
    }

    public int countNotPaid() {
        int i = 0;
        for (Order order : orderList) {
            if (order.getIsPaid().equals(YesOrNot.NO)) {
                i++;
            }
        }
        return i;
    }

    public void sortByOrderedTime() {
        orderList.sort(comparing(Order::getOrderedTime));
    }

    public String averageSortedOutTime() {
        long size = 0;
        long averageTime = 0;
        for (Order order: orderList) {
            if (order.getIsSortedOut().equals(YesOrNot.YES)) {
                long i = Duration.between(order.getOrderedTime()
                        , order.getFulfilmentTime()).toMinutes();
                averageTime = averageTime + i;
                size++;
            }
        }
        long result = averageTime / size;
        return ("Průměrná doba vyřízení objednávky je " + result + " minut.");
    }

    public String writeDayOrderedDish() {
        Set<String> dishList = new HashSet<>();
        for (Order order : orderList) {
            dishList.add(order.getOrderDishName());
        }
        return ("Jídla která byla dnes objednána: " + dishList);
    }
//region    Výpis na obrazovku

//    private String firstLine (int table) {
//
//    }

//endregion

    @Override
    public String toString() {
        return "OrdersList{" +
                "orderList=" + orderList +
                '}';
    }
}
