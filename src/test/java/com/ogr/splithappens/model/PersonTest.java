package com.ogr.splithappens.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    @Test
    public void randomPersonTest() {
        List<Pair<Integer, Integer>> l0 = new ArrayList<>(Arrays.asList(new Pair<>(1, 15), new Pair<>(2, 15)));
        List<Pair<Integer, Integer>> l1 = new ArrayList<>(Arrays.asList(new Pair<>(1, 30), new Pair<>(2, 0)));
        Expense e0 = new Expense("0", 0, 30, l0);
        Expense e1 = new Expense("1", 0, 30, l1);
        ExpenseManager em = new ExpenseManager();
        em.addExpense(e0);
        em.addExpense(e1);
        PersonsManager pm = new PersonsManager(em);
        pm.addPerson("a");
        pm.addPerson("b");
        pm.addPerson("c");
        int ctr = 0;
        for (var person : pm.getPersons()) {
            if (ctr == 0) {
                assertEquals(person.getBalance(), 60);
            }
            if (ctr == 1) {
                assertEquals(person.getBalance(), -45);
            }
            if (ctr == 2) {
                assertEquals(person.getBalance(), -15);
                assertEquals(person.getID(), 2);
                assertEquals(person.getName(), "c");
            }
            if (ctr == 0) {
                int ctr2 = 0;
                assertEquals(person.getDetailedBalances().size(), 2);
                for (var db : person.getDetailedBalances()) {
                    if (ctr2 == 0) {
                        assertEquals(db.balance(), 45);
                        ctr2++;
                    } else {
                        assertEquals(db.balance(), 15);
                    }
                }
            }
            if (ctr == 2) {
                assertEquals(person.getDetailedBalances().size(), 1);
                for (var db : person.getDetailedBalances()) {
                    assertEquals(db.balance(), -15);
                }
            }
            ctr++;
        }
    }

    @Test
    public void simpleRemovePersonTest() {
        ExpenseManager em = new ExpenseManager();
        PersonsManager pm = new PersonsManager(em);
        pm.addPerson("a");
        pm.addPerson("b");
        pm.addPerson("c");
        assertEquals(3, pm.getPersons().size());
        assertTrue(pm.getPersons().get(0).isActive());
        assertTrue(pm.getPersons().get(0).setInactive());
        assertEquals(2, pm.getPersons().size());
        assertTrue(pm.getPersons().get(0).isActive());
        assertTrue(pm.getPersons().get(1).isActive());
        assertTrue(pm.getPersons().get(1).setInactive());
        assertEquals(1, pm.getPersons().size());
        assertTrue(pm.getPersons().get(0).isActive());
        assertTrue(pm.getPersons().get(0).setInactive());
        assertEquals(0, pm.getPersons().size());
    }

    public void AllowOnlySettledDeletionsTest() {
        ExpenseManager em = new ExpenseManager();
        PersonsManager pm = new PersonsManager(em);
        pm.addPerson("a");
        pm.addPerson("b");
        pm.addPerson("c");
        List<Pair<Integer, Integer>> l0 = new ArrayList<>(Arrays.asList(new Pair<>(1, 15), new Pair<>(2, 15)));
        List<Pair<Integer, Integer>> l1 = new ArrayList<>(Arrays.asList(new Pair<>(1, 30), new Pair<>(2, 0)));
        Expense e0 = new Expense("0", 0, 30, l0);
        Expense e1 = new Expense("1", 0, 30, l1);
        em.addExpense(e0);
        em.addExpense(e1);
        assertFalse(pm.getPersons().get(0).canBeSetInactive());
        assertFalse(pm.getPersons().get(1).canBeSetInactive());
        assertFalse(pm.getPersons().get(2).canBeSetInactive());
        List<Pair<Integer, Integer>> l2 = new ArrayList<>(Arrays.asList(new Pair<>(0, 45)));
        List<Pair<Integer, Integer>> l3 = new ArrayList<>(Arrays.asList(new Pair<>(0, 15)));
        Expense e2 = new Expense("2", 1, 45, l2);
        Expense e3 = new Expense("3", 2, 15, l3);
        em.addExpense(e2);
        em.addExpense(e3);
        assertTrue(pm.getPersons().get(0).canBeSetInactive());
        assertTrue(pm.getPersons().get(1).canBeSetInactive());
        assertTrue(pm.getPersons().get(2).canBeSetInactive());
        assertTrue(pm.getPersons().get(0).setInactive());
        assertTrue(pm.getPersons().get(0).setInactive());
        assertTrue(pm.getPersons().get(0).setInactive());
        assertEquals(0, pm.getPersons().size());
    }
}