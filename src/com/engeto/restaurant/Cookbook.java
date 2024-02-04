package com.engeto.restaurant;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cookbook {

    private List<Dish> cookbook = new ArrayList<>();

    public List<Dish> getCookbook() {
        return cookbook;
    }
    public void addDish(Dish dish) {
        cookbook.add(dish);
    }
    public void removeDish(int index) {
        cookbook.removeIf(dish -> dish.getDishNumber() == index);
    }
    public Dish getDish(int dishNumber) {
        Dish i = null;
        for (Dish dish : cookbook) {
            if (dish.getDishNumber()==dishNumber) {
                i = dish;
            }
        }
        return i;
    }

    public static Cookbook loadFromFile(String filename) throws RestaurantException {
       Cookbook result = new Cookbook();
       try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)))) {
           while (scanner.hasNextLine()) {
               String line = scanner.nextLine();
               parseLine(line, result);
           }
       } catch (FileNotFoundException e) {
           throw new RestaurantException("Nepodařilo se nalézt soubor " + filename + ": " + e.getLocalizedMessage());
       }
       return result;
    }
    private static void parseLine(String line, Cookbook cookbook) throws RestaurantException {
        String[] blocks = line.split("\t");
        int numOfBlocks = blocks.length;
        int needNumOfBlocks = 5;
        if (numOfBlocks != needNumOfBlocks) {
            throw new RestaurantException(
                    "Nesprávný počet položek na řádku: " + line +
                            "! Počet položek: " + numOfBlocks + " z " + needNumOfBlocks + ".");
        }
        int dishNumber = Integer.parseInt(blocks[0].trim());
        String name = blocks[1].trim();
        BigDecimal price = new BigDecimal(blocks[2].trim());
        LocalTime preparationTime = LocalTime.parse(blocks[3].trim());
        String image = blocks[4].trim();

        Dish newDish = new Dish(dishNumber, name, price, preparationTime, image);
        cookbook.addDish(newDish);
    }

    public static void saveToFile(String filename, Cookbook cookbook) throws RestaurantException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            for (Dish dish : cookbook.getCookbook()) {
                writer.println(dish.getDishNumber() + Settings.getFileDelimiter() +
                        dish.getName() + Settings.getFileDelimiter() +
                        dish.getPrice() + Settings.getFileDelimiter() +
                        dish.getPreparationTime() + Settings.getFileDelimiter() +
                        dish.getImage());
            }
        } catch (IOException e) {
            throw new RestaurantException("Chyba při zápisu do souboru '"
                    + filename + "' : " + e.getLocalizedMessage());
        }


    }

    @Override
    public String toString() {
        return "Cookbook{" +
                "cookbook=" + cookbook +
                '}';
    }
}
