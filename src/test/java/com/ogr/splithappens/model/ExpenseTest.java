package com.ogr.splithappens.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {
    @Test
    public void simpleSplitTest() {
        List<Pair<Integer, Integer>> l0 = new ArrayList<>(Arrays.asList(new Pair<>(1, 15), new Pair<>(2, 15)));
        List<Pair<Integer, Integer>> l1 = new ArrayList<>(Arrays.asList(new Pair<>(1, 30), new Pair<>(2, 0)));
        Expense e0 = new Expense("0", 0, 30, l0);
        Expense e1 = new Expense("1", 0, 30, l1);
        ExpenseManager em = new ExpenseManager();
        em.addExpense(e0);
        em.addExpense(e1);
        assertEquals(2, em.getDetailedBalances().get(0).size());
        assertEquals(1, em.getDetailedBalances().get(1).size());
        assertEquals(1, em.getDetailedBalances().get(2).size());
        assertEquals(1, em.getDetailedBalances().get(0).get(0).first);
        assertEquals(45, em.getDetailedBalances().get(0).get(0).second);
        assertEquals(2, em.getDetailedBalances().get(0).get(1).first);
        assertEquals(15, em.getDetailedBalances().get(0).get(1).second);
        assertEquals(0, em.getDetailedBalances().get(1).get(0).first);
        assertEquals(-45, em.getDetailedBalances().get(1).get(0).second);
        assertEquals(0, em.getDetailedBalances().get(2).get(0).first);
        assertEquals(-15, em.getDetailedBalances().get(2).get(0).second);
    }

    @Test
    public void removeExpenseTest() {
        ExpenseManager em = new ExpenseManager();
        em.addExpense(new Expense("a", 0, 0, new ArrayList<>(Arrays.asList(new Pair<>(1, 0), new Pair<>(2, 0)))));
        em.addExpense(new Expense("b", 0, 30, new ArrayList<>(Arrays.asList(new Pair<>(1, 10), new Pair<>(2, 20)))));
        assertEquals(em.getExpenses().size(), 2);
        for (var expense : em.getExpenses()) {
            if (expense.getID() == 0) {
                assertEquals(expense.getAmount(), 0);
            }
            if (expense.getID() == 1) {
                assertEquals(expense.getAmount(), 30);
            }
        }
        em.removeExpense(0);
        assertEquals(em.getExpenses().size(), 1);
        for (var expense : em.getExpenses()) {
            if (expense.getID() == 0) {
                assertEquals(expense.getAmount(), 30);
            }
        }
        em.removeExpense(1);
        assertEquals(em.getExpenses().size(), 0);
        em.addExpense(new Expense("a", 0, 0, new ArrayList<>(Arrays.asList(new Pair<>(1, 0), new Pair<>(2, 0)))));
        em.addExpense(new Expense("b", 0, 30, new ArrayList<>(Arrays.asList(new Pair<>(1, 10), new Pair<>(2, 20)))));
        assertEquals(em.getExpenses().size(), 2);
        boolean t1 = em.removeExpense(3);
        assertEquals(em.getExpenses().size(), 1);
        for (var expense : em.getExpenses()) {
            if (expense.getID() == 2) {
                assertEquals(expense.getAmount(), 0);
            }
        }
        boolean t2 = em.removeExpense(2);
        assertEquals(em.getExpenses().size(), 0);
        assertTrue(t1);
        assertTrue(t2);
        assertFalse(em.removeExpense(-1));
        assertFalse(em.removeExpense(0));
        assertFalse(em.removeExpense(1));
        assertFalse(em.removeExpense(2));
        assertFalse(em.removeExpense(3));
    }
}