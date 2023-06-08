package com.ogr.splithappens.model;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseManager implements Serializable {

    List<Expense> expenses = new ArrayList<>();
    int globalID = 0;

    public int getGlobalID() {
        return globalID;
    }

    public void incrementGlobalID() {
        globalID++;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void addExpense(Expense expense) {
        expenses.add(new Expense(expense.getTitle(), expense.getPayerID(), expense.getAmount(), expense.getBorrowers(), getGlobalID()));
        incrementGlobalID();
    }

    public Map<Integer, Integer> getBalances() {
        Map<Integer, Integer> balances = new HashMap<>();
        for (Expense expense : getExpenses()) {
            balances.putIfAbsent(expense.getPayerID(), 0);
            balances.put(expense.getPayerID(), balances.get(expense.getPayerID()) + expense.getAmount());
            for (Pair<Integer, Integer> borrower : expense.getBorrowers()) {
                balances.putIfAbsent(borrower.first, 0);
                balances.put(borrower.first, balances.get(borrower.first) - borrower.second);
            }
        }
        return balances;
    }

    Map<Integer, List<Pair<Integer, Integer>>> getDetailedBalances() {
        List<Pair<Integer, Integer>> positive = new ArrayList<>();
        List<Pair<Integer, Integer>> negative = new ArrayList<>();
        Map<Integer, List<Pair<Integer, Integer>>> result = new HashMap<>();
        for (var balance : getBalances().entrySet()) {
            if (balance.getValue().equals(0)) {
                continue;
            }
            if (balance.getValue() > 0) {
                positive.add(new Pair<>(balance.getKey(), balance.getValue()));
            } else {
                negative.add(new Pair<>(balance.getKey(), balance.getValue()));
            }

            result.put(balance.getKey(), new ArrayList<>());
        }
        //sort positive in descending and negative in ascending order by .second
        int positiveIndex = 0;
        int negativeIndex = 0;
        while (positiveIndex < positive.size() && negativeIndex < negative.size()) {
            int amount = Math.min(positive.get(positiveIndex).second, -negative.get(negativeIndex).second);
            result.get(positive.get(positiveIndex).first).add(new Pair<>(negative.get(negativeIndex).first, amount));
            result.get(negative.get(negativeIndex).first).add(new Pair<>(positive.get(positiveIndex).first, -amount));
            positive.get(positiveIndex).second -= amount;
            negative.get(negativeIndex).second += amount;
            if (positive.get(positiveIndex).second == 0) {
                positiveIndex++;
            }
            if (negative.get(negativeIndex).second == 0) {
                negativeIndex++;
            }
        }
        return result;
    }

    public boolean removeExpense(int id) {
        int sizeBefore = expenses.size();
        expenses = expenses.stream().filter(((Expense a) -> a.getID() != id)).collect(Collectors.toList());
        return sizeBefore != expenses.size(); // true if deleted something;
    }
}
