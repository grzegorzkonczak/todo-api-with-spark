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

    public Sql2oTodoDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    // Adds new to'do to database and sets id of added to'do
    @Override
    public void create(Todo todo) throws DaoException {
        // Create SQL statement
        String sql = "INSERT INTO todos(name, isCompleated) VALUES(:name, :isCompleated)";
        // Open connection using try with resources
        try(Connection con = sql2o.open()) {
            // Execute update
            int id = (int) con.createQuery(sql)
                    .bind(todo)
                    .executeUpdate()
                    .getKey();

            // Set id of newly created to'do
            todo.setId(id);
        }catch (Sql2oException ex){
            throw new DaoException(ex, "Problem adding todo...");
        }
    }

    @Override
    public void update(int id) throws DaoException {

    }

    @Override
    public void delete(int id) throws DaoException {

    }

    @Override
    public List<Todo> findAll() {
        return null;
    }
}
