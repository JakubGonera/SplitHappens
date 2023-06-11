package com.ogr.splithappens.IOservice;

import com.ogr.splithappens.Program;
import com.ogr.splithappens.model.*;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReadDataTest {
    @Test
    public void test1(){
        ExpenseManager em = new ExpenseManager();
        PersonsManager pm = new PersonsManager(em);
        pm.addPerson("a");
        pm.addPerson("b");
        pm.addPerson("c");
        List<Pair<Integer,Integer>> l0 = List.of(new Pair<>(1, 15), new Pair<>(2,15));
        em.addExpense(new Expense("0",0, 30, l0));
        List<Pair<Integer,Integer>> l1 = List.of(new Pair<>(1, 30), new Pair<>(2,0));
        em.addExpense(new Expense("1", 0, 30, l1));
        List<Person.detailedBalance>lbefore = new ArrayList<>();
        for(var person : pm.getPersons()){
            for(var expense : person.getDetailedBalances()){
                lbefore.add(expense);
            }
        }
        WriteData.writeData(pm);
        em = new ExpenseManager();
        pm = new PersonsManager(em);
        pm = ReadData.readData();
        em = pm.getExpenseManager();
        List<Person.detailedBalance>lafter = new ArrayList<>();
        for(var person : pm.getPersons()){
            for(var expense : person.getDetailedBalances()){
                lafter.add(expense);
            }
        }
        assertEquals(lbefore.size(), lafter.size());
        assertNotEquals(lafter.size(), 0);
        pm.addPerson("d");
        em.addExpense(new Expense("2", 0, 30, List.of(new Pair<>(1, 30), new Pair<>(2,0)), "desc", null, Category.Other, new Image(Program.class.getResourceAsStream("images/spn.jpg")), false));
        WriteData.writeData(pm);
        pm = ReadData.readData();
        em = pm.getExpenseManager();
        for(var exp : em.getExpenses()){
            try{
                System.out.println(exp.getImage().getHeight());
            }
            catch (Exception e){
                System.out.println("null");
            }
        }
        List<Person.detailedBalance>lafter2 = new ArrayList<>();
        for(var person : pm.getPersons()){
            for(var expense : person.getDetailedBalances()){
                lafter2.add(expense);
            }
        }
        assertEquals(lbefore.size(), lafter2.size());
        assertNotEquals(lafter2.size(), 0);
        ExpenseManager expm = new ExpenseManager();
        PersonsManager perm = new PersonsManager(expm);
        perm.addPerson("a");
        perm.addPerson("b");
        perm.addPerson("c");
        perm.addPerson("d");
        WriteData.writeData(perm);
        PersonsManager newperm = ReadData.readData();
        assertEquals(4, newperm.getPersons().size());
        int counter = 0;
        for(var person : newperm.getPersons()){
            assertEquals(0, person.getDetailedBalances().size());
            counter++;
            if(counter == 1){
                assertEquals("a", person.getName());
            }
            else if(counter == 2){
                assertEquals("b", person.getName());
            }
            else if(counter == 3){
                assertEquals("c", person.getName());
            }
            else if(counter == 4){
                assertEquals("d", person.getName());
            }
        }
    }
}