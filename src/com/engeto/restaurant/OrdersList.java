package com.engeto.restaurant;

import java.io.*;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalTime;
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
        if (cookbook.getSize()==0) {
            throw new RestaurantException("Kuchařka je prázdná!");
        }
        int lineNumber = 1;
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                parseLine(line, result, cookbook, lineNumber);
                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            throw new RestaurantException("Nepodařilo se nalézt soubor " + filename + ": " + e.getLocalizedMessage());
        }
        return result;
    }

    private static void parseLine(String line, OrdersList ordersList, Cookbook cookbook, int lineNumber) throws RestaurantException {
        String[] blocks = line.split("\t");
        int numOfBlocks = blocks.length;
        int needNumOfBlocks = 8;
        if (numOfBlocks != needNumOfBlocks) {
            throw new RestaurantException(
                    "Nesprávný počet položek na řádku: " + line +
                            "! Počet položek: " + numOfBlocks + " z " + needNumOfBlocks + ".");
        }
        int id;
        int table;
        int orderDishNumber;
        int numberOfDish;
        LocalTime orderedTime;
        LocalTime fulfilmentTime;

        try {
            id = Integer.parseInt(blocks[0].trim());
        } catch (Exception e) {
            throw new RestaurantException("Chybně zadáno číslo objednávky: " + blocks[0] +
                    " na řádku " + lineNumber + "! Chybný zápis řádku: '" + line + "'");
        }
        try {
            table = Integer.parseInt(blocks[1].trim());
        } catch (Exception e) {
            throw new RestaurantException("Chybně zadáno číslo stolu: " + blocks[0] +
                    " na řádku " + lineNumber + "! Chybný zápis řádku: '" + line + "'");
        }
        try {
            orderDishNumber = Integer.parseInt(blocks[2].trim());
        } catch (Exception e) {
            throw new RestaurantException("Chybně zadáno číslo jídla: " + blocks[0] +
                    " na řádku " + lineNumber + "! Chybný zápis řádku: '" + line + "'");
        }
        try {
            numberOfDish = Integer.parseInt(blocks[3].trim());
        } catch (Exception e) {
            throw new RestaurantException("Chybně zadán počet jídel: " + blocks[0] +
                    " na řádku " + lineNumber + "! Chybný zápis řádku: '" + line + "'");
        }
        try {
            orderedTime = LocalTime.parse(blocks[4].trim());
        } catch (DateTimeException e) {
            throw new RestaurantException("Chybně zadáno číslo objednávky: " + blocks[0] +
                    " na řádku " + lineNumber + "! Chybný zápis řádku: '" + line + "'");
        }
        try {
            fulfilmentTime = LocalTime.parse(blocks[5].trim());
        } catch (DateTimeException e) {
            throw new RestaurantException("Chybně zadáno číslo objednávky: " + blocks[0] +
                    " na řádku " + lineNumber + "! Chybný zápis řádku: '" + line + "'");
        }
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

    public void writeAverageSortedOutTime() {
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
        System.out.println("Průměrná doba vyřízení objednávky je " + result + " minut.");
    }

    public void writeDayOrderedDish() {
        Set<String> dishList = new HashSet<>();
        for (Order order : orderList) {
            dishList.add(order.getOrderDishName());
        }
        System.out.println("Jídla která byla dnes objednána: " + dishList);
    }

    public void writeOrderForTable (int table) throws RestaurantException {
        String tableTwoDigit;
        if (table <= 0) {
            throw new RestaurantException ("Číslo stolu nesmí být menší než 0!");
        }
        tableTwoDigit = (table < 10 ? "0" : " ") + table;
        System.out.println("** Objednávky pro stůl č. " + tableTwoDigit + " **" +
                "\n****");
        for (Order order : orderList) {
            if ( order.getTable() == table) {
                System.out.println(order.getId() + " " + order.getOrderDishName() + " " +
                        order.getNumberOfDish() + "x (" + order.getFinalPrice() + " Kč):" + "\t" +
                        order.getOrderedTime() + "-" + order.getFulfilmentTime() + "\t" + order.writeIsPaidYes());
            }
        }
        System.out.println("******");
        }

    public void writeTableFinalPrice (int table) throws RestaurantException {
        BigDecimal tableFinalPrice = BigDecimal.ZERO;
        if (table <= 0) {
            throw new RestaurantException ("Číslo stolu nesmí být menší než 0!");
        }
        for (Order order : orderList) {
            if (order.getTable()==table && order.getIsPaid().equals(YesOrNot.NO)) {
                tableFinalPrice = tableFinalPrice.add(order.getFinalPrice());
            }
        }
        System.out.println("Celková cena pro stůl " + table + " je " + tableFinalPrice + " Kč.");
    }

    @Override
    public String toString() {
        return "OrdersList{" +
                "orderList=" + orderList +
                '}';
    }
}
