package com.checkrise.todos.dao;

import com.checkrise.todos.exc.DaoException;
import com.checkrise.todos.model.Todo;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

// Sql2o dao implementation
public class Sql2oTodoDao implements TodoDao{

    private final Sql2o sql2o;
    private int id;

    public Sql2oTodoDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    // Adds new to'do to database and sets id of added to'do
    @Override
    public void create(Todo todo) throws DaoException {
        // Create SQL statement
        String sql = "INSERT INTO todos(name, is_completed) VALUES(:name, :completed)";
        // Open connection using try with resources
        try(Connection con = sql2o.open()) {
            // Execute update
            id = (int) con.createQuery(sql)
                    .bind(todo)
                    .executeUpdate()
                    .getKey();

            // Set id of newly created to'do
            todo.setId(id);
        }catch (Sql2oException ex){
            throw new DaoException(ex, "Problem adding todo...");
        }
    }

    // Updates existing to'do name and completion status
    @Override
    public void update(Todo todo) throws DaoException {
        // Create SQL statement
        String sql = "UPDATE todos SET name = :name, is_completed = :completed WHERE id = :id";
        // Open connection using try with resources
        try(Connection con = sql2o.open()) {
            // Execute update
             con.createQuery(sql)
                     .bind(todo)
                     .executeUpdate();

        }catch (Sql2oException ex){
            throw new DaoException(ex, "Problem updating todo...");
        }
    }

    @Override
    public void delete(int id) throws DaoException {

    }

    // Finds existing to'do by it's id
    @Override
    public Todo findById(int id) throws DaoException {
        try (Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM todos WHERE id = :id")
                    .addParameter("id", id)
                    .addColumnMapping("IS_COMPLETED", "completed")
                    .executeAndFetchFirst(Todo.class);
        }
    }

    // Finds and returns all existing todos as list
    @Override
    public List<Todo> findAll() {
        try (Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM todos")
                    .addColumnMapping("IS_COMPLETED", "completed")
                    .executeAndFetch(Todo.class);
        }
    }
}
