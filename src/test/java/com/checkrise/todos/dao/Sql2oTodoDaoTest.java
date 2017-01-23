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

    @Test
    public void whenSearchingForNonExistingTodoReturnsNull() throws Exception {
        assertNull(dao.findById(2));
    }

    @Test
    public void existingTodosCanBeFoundById() throws Exception {
        Todo todo = newTestTodo();
        dao.create(todo);

        Todo foundTodo = dao.findById(todo.getId());

        assertEquals(todo, foundTodo);
    }

    @Test
    public void updatingExistingTodoChangesName() throws Exception {
        Todo todo = newTestTodo();
        String newName = "Test1";
        dao.create(todo);

        todo.setName(newName);
        dao.update(todo);

        assertEquals(newName, dao.findById(todo.getId()).getName());
    }

    @Test
    public void updatingExistingTodoChangesCompleted() throws Exception {
        Todo todo = newTestTodo();
        boolean newCompleted = false;
        dao.create(todo);

        todo.setCompleted(newCompleted);
        dao.update(todo);

        assertEquals(newCompleted, dao.findById(todo.getId()).isCompleted());
    }

    @Test
    public void addedTodosAreReturnedFromFindAll() throws Exception {
        Todo todo = newTestTodo();

        dao.create(todo);

        assertEquals(1, dao.findAll().size());
    }

    @Test
    public void noTodosReturnsEmptyList() throws Exception {
        assertEquals(0, dao.findAll().size());
    }

    @Test
    public void deletingTodoRemovesItFromDatabase() throws Exception {
        Todo todo = newTestTodo();
        dao.create(todo);

        dao.delete(todo.getId());

        assertNull(dao.findById(todo.getId()));
    }

    @Test
    public void deletingNonExistingTodoDoesNotRemovesExistingTodos() throws Exception {
        Todo todo = newTestTodo();
        dao.create(todo);

        dao.delete(42);

        assertEquals(1, dao.findAll().size());
    }

    private Todo newTestTodo() {
        return new Todo("Test", true);
    }

    @After
    public void tearDown() throws Exception {
        con.close();
    }

}