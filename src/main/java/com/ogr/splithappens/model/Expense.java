package com.ogr.splithappens.model;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Expense implements Serializable {
    String title;
    int payerID;
    int amount;

    String description;
    Date dateAdded;
    Category category;
    SerializableImage image;
    boolean isATransfer = false;
    int ID;
    List<Pair<Integer, Integer>> borrowers;

    public Expense(String title, int payerID, int amount, List<Pair<Integer, Integer>> borrowers, int ID) {
        this.ID = ID;
        this.title = title;
        this.payerID = payerID;
        this.amount = amount;
        this.borrowers = borrowers;
        description = "";
        dateAdded = new Date();
        category = Category.Other;
        image = null;
    }

    public Expense(String title, int payerID, int amount, List<Pair<Integer, Integer>> borrowers) {
        this.ID = -1;
        this.title = title;
        this.payerID = payerID;
        this.amount = amount;
        this.borrowers = borrowers;
        description = "";
        dateAdded = new Date();
        category = Category.Other;
        image = null;
    }

    public Expense(String title, int payerID, int amount, List<Pair<Integer, Integer>> borrowers, String description, Date dateAdded, Category category, Image image, boolean isATransfer) {
        this.ID = -1;
        this.title = title;
        this.payerID = payerID;
        this.amount = amount;
        this.borrowers = borrowers;
        this.description = description;
        this.dateAdded = dateAdded;
        this.category = category;
        this.image = new SerializableImage(image);
        this.isATransfer = isATransfer;
    }


    public String getTitle() {
        return title;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public List<Pair<Integer, Integer>> getBorrowers() {
        return borrowers;
    }

    public int getAmount() {
        return amount;
    }

    public int getPayerID() {
        return payerID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Image getImage() {
        try {
            return image.getImage();
        } catch (Exception e) {
            //return default image
        }
        return null;
    }

    public void setImage(Image image) {
        setImage(image);
    }

    public boolean isATransfer() {
        return isATransfer;
    }
}
