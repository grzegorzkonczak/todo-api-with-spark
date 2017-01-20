package com.checkrise.todos.dao;


import com.checkrise.todos.exc.DaoException;
import com.checkrise.todos.model.Todo;

import java.util.List;

public interface TodoDao {
    void create(Todo todo) throws DaoException;
    void update(int id) throws DaoException;
    void delete(int id) throws DaoException;
    List<Todo> findAll();
}
