package com.ogr.splithappens.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class ExpenseTest {
    @Test
    public void staticTest(){
        List<Pair<Integer,Integer>> ex = new ArrayList<>();
        Expense e0 = new Expense("a", 0, 0, ex );
        Expense e1 = new Expense("a", 0, 0, ex );
        assertEquals(e0.getID()+1, e1.getID());
    }
    @Test
    public void simpleTest(){
        List<Pair<Integer,Integer>> l0 = new ArrayList<>(Arrays.asList(new Pair<>(1, 15), new Pair<>(2,15)));
        List<Pair<Integer,Integer>> l1 = new ArrayList<>(Arrays.asList(new Pair<>(1, 30), new Pair<>(2,0)));
        Expense e0 = new Expense("0",0, 30, l0);
        Expense e1 = new Expense("1", 0, 30, l1);
        ExpenseManager em = new ExpenseManager();
        em.addExpense(e0);
        em.addExpense(e1);
        System.out.println(em.getBalances());
        PersonsManager pm = new PersonsManager(em);
        pm.addPerson("a");
        pm.addPerson("b");
        pm.addPerson("c");
        int ctr = 0;
        for(var person : pm.getPersons()){
            if(ctr==0){
                assertEquals(person.getBalance(), 60);
            }
            if(ctr==1){
                assertEquals(person.getBalance(), -45);
            }
            if(ctr==2){
                assertEquals(person.getBalance(), -15);
                assertEquals(person.getID(),2 );
                assertEquals(person.getName(), "c");
            }
            if(ctr==0){
                int ctr2=0;
                assertEquals(person.getDetailedBalances().size(), 2);
                for(var db: person.getDetailedBalances()){
                    if(ctr2==0){
                        assertEquals(db.balance(), 45);
                        ctr2++;
                    }
                    else{
                        assertEquals(db.balance(),15);
                    }
                }
            }
            if(ctr==2){
                assertEquals(person.getDetailedBalances().size(), 1);
                for(var db: person.getDetailedBalances()){
                    assertEquals(db.balance(), -15);
                }
            }
            ctr++;
        }
    }
    @Test
    public void deleteExpenseTest(){
        ExpenseManager em = new ExpenseManager();
        em.addExpense(new Expense("a", 0, 0, new ArrayList<>(Arrays.asList(new Pair<>(1, 0), new Pair<>(2,0)))));
        em.addExpense(new Expense("b", 0, 30,  new ArrayList<>(Arrays.asList(new Pair<>(1, 10), new Pair<>(2,20)))));
        assertEquals(em.getExpenses().size(), 2);
        for(var expense : em.getExpenses()){
            if(expense.getID()==0){
                assertEquals(expense.getAmount(), 0);
            }
            if(expense.getID()==1){
                assertEquals(expense.getAmount(), 30);
            }
        }
        em.removeExpense(0);
        assertEquals(em.getExpenses().size(), 1);
        for(var expense : em.getExpenses()){
            if(expense.getID()==0){
                assertEquals(expense.getAmount(), 30);
            }
        }
        em.removeExpense(1);
        assertEquals(em.getExpenses().size(), 0);
        em.addExpense(new Expense("a", 0, 0, new ArrayList<>(Arrays.asList(new Pair<>(1, 0), new Pair<>(2,0)))));
        em.addExpense(new Expense("b", 0, 30,  new ArrayList<>(Arrays.asList(new Pair<>(1, 10), new Pair<>(2,20)))));
        assertEquals(em.getExpenses().size(), 2);
        boolean t1 = em.removeExpense(3);
        assertEquals(em.getExpenses().size(), 1);
        for(var expense : em.getExpenses()){
            if(expense.getID()==2){
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