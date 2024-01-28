package com.engeto.restaurant;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Order {

    private static int nextId = 1;
    private int id = nextId++;
    private int table;
    private Dish orderDish;
    private int numberOfDish;
    private LocalTime orderedTime;
    private LocalTime fulfilmentTime = null;
    private IsPaid isPaid;
    private IsSortedOut isSortedOut;

private enum IsPaid {
    YES,
    NO
}
private enum IsSortedOut {
    YES,
    NO
}

    public Order(int table, Dish orderDish, int numberOfDish) {
        this.table = table;
        this.orderDish = orderDish;
        this.numberOfDish = numberOfDish;
        this.orderedTime = LocalTime.now();
        this.isPaid = IsPaid.NO;
        this.isSortedOut = IsSortedOut.NO;


    }

    public int getId() {
        return id;
    }

    public int getTable() {
        return table;
    }

    public String getOrderDish() {
        return orderDish.getName();
    }

    public int getNumberOfDish() {
        return numberOfDish;
    }

    public LocalTime getOrderedTime() {
        return Settings.writeFormatteTime(orderedTime);
    }

    public LocalTime getFulfilmentTime() {
        if (fulfilmentTime == null) {
            return null;
        }
        else {
            return Settings.writeFormatteTime(fulfilmentTime);
        }
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
    public IsSortedOut getIsSortedOut() {
        return isSortedOut;
    }
    public void sortedOut() {
        isSortedOut = IsSortedOut.YES;
        fulfilmentTime = LocalTime.now();
    }

    public String writeIsSortedOut() {
        if (isSortedOut.equals(isSortedOut.NO)) {
            return "nevyřízena";
        }
        else {
            return "vyřízena";
        }
    }

    public String writeIsSortedOutWithTime() {
        if (isSortedOut.equals(isSortedOut.NO)) {
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
                " : objednáno " + numberOfDish + " ks " + getOrderDish() +
                " dnes v " + getOrderedTime() +
                ". Objednávka je " +
                writeIsSortedOutWithTime() + " a je " +
                writeIsPaid() + ".";
    }
}
