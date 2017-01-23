package com.checkrise.todos.dao;

import com.checkrise.todos.model.Todo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;


public class Sql2oTodoDaoTest {

    private Sql2oTodoDao dao;
    private Connection con;

    // Set up connection with database for testing
    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        dao = new Sql2oTodoDao(sql2o);
        //Keep connection open trough entire test so that it isn't wiped out
        con = sql2o.open();
    }

    @Test
    public void addingNewTodoSetsId() throws Exception {
        Todo todo = newTestTodo();
        int originalTodoId = todo.getId();

        dao.create(todo);

        assertNotEquals(originalTodoId, todo.getId());
    }

    private Todo newTestTodo() {
        return new Todo("Test", true);
    }

    @After
    public void tearDown() throws Exception {
        con.close();
    }

}