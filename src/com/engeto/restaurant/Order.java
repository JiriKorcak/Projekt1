package com.engeto.restaurant;

import java.time.LocalTime;

public class Order {

    private static int nextId = 1;
    private int id;
    private int table;
    private int orderDishNumber;
    private Dish orderDish;
    private int numberOfDish;
    private LocalTime orderedTime;
    private LocalTime fulfilmentTime = LocalTime.MIN;
    private IsPaid isPaid;
    private IsSortedOut isSortedOut;

public enum IsPaid {
    YES,
    NO
}
private enum IsSortedOut {
    YES,
    NO
}

    public Order(int orderId, int table, int orderDishNumber, int numberOfDish, LocalTime orderedTime,
                 LocalTime fulfilmentTime, String isSortedOut, String isPaid, Cookbook cookbook) {
        this.id = orderId;
        this.table = table;
        this.orderDishNumber = orderDishNumber;
        this.orderDish = cookbook.getDish(orderDishNumber);
        this.numberOfDish = numberOfDish;
        this.orderedTime = orderedTime;
        this.fulfilmentTime = fulfilmentTime;
        this.isSortedOut = IsSortedOut.valueOf(isSortedOut);
        this.isPaid = IsPaid.valueOf(isPaid);
        nextId = id+1;


    }

    public Order(int table, int orderDishNumber, int numberOfDish, Cookbook cookbook) {
        this.id = nextId++;
        this.table = table;
        this.orderDishNumber = orderDishNumber;
        this.orderDish = cookbook.getDish(orderDishNumber);
        this.numberOfDish = numberOfDish;
        this.orderedTime = LocalTime.now();
        this.isSortedOut = IsSortedOut.NO;
        this.isPaid = IsPaid.NO;



    }

    public int getId() {
        return id;
    }

    public int getTable() {
        return table;
    }

    public String getOrderDishName() {
        return orderDish.getName();
    }

    public int getNumberOfDish() {
        return numberOfDish;
    }

    public LocalTime getOrderedTime() {
        return Settings.writeFormatteTime(orderedTime);
    }

    public LocalTime getFulfilmentTime() {
//        if (fulfilmentTime == null) {
//            return null;
//        }
//        else {
            return Settings.writeFormatteTime(fulfilmentTime);
//        }
    }

    public IsPaid getIsPaid() {
        return isPaid;
    }
    public void pay() {
        isPaid = IsPaid.YES;
    }
    public String writeIsPaid() {
        if (isPaid.equals(IsPaid.NO)) {
            return "nezaplacena";
        }
        else {
            return "zaplacena";
        }
    }
    public int getOrderDishNumber() {
        return orderDishNumber;
    }
    public IsSortedOut getIsSortedOut() {
        return isSortedOut;
    }
    public void sortedOut() {
        isSortedOut = IsSortedOut.YES;
        fulfilmentTime = LocalTime.now();
    }

    public String writeIsSortedOut() {
        if (isSortedOut.equals(IsSortedOut.NO)) {
            return "nevyřízena";
        }
        else {
            return "vyřízena";
        }
    }

    public String writeIsSortedOutWithTime() {
        if (isSortedOut.equals(IsSortedOut.NO)) {
            return "nevyřízena";
        }
        else {
            return "vyřízena v " + Settings.writeFormatteTime(fulfilmentTime);
        }
    }

    @Override
    public String toString() {
        return "Objednávka č." + getId() +
                " ke stolu " + table +
                " : objednáno " + numberOfDish + " ks " + getOrderDishName() +
                " dnes v " + getOrderedTime() +
                ". Objednávka je " +
                writeIsSortedOutWithTime() + " a je " +
                writeIsPaid() + ".\n";
    }
}
