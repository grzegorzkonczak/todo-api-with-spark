package com.checkrise.todos;


import com.checkrise.todos.dao.Sql2oTodoDao;
import com.checkrise.todos.dao.TodoDao;
import com.checkrise.todos.model.Todo;
import com.google.gson.Gson;
import org.sql2o.Sql2o;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        staticFileLocation("/public");

        // Configure database (if condition used for functional testing or
        // custom configuration by client of API)
        String dataSource = "jdbc:h2:~/todos.db";
        if (args.length > 0){
            if (args.length != 2){
                System.out.println("java TODO API <port> <datasource>");
                System.exit(0);
            }
            port(Integer.parseInt(args[0]));
            dataSource = args[1];
        }
        // Default Sql2o object configuration
        Sql2o sql2o = new Sql2o(String.format("%s;INIT=RUNSCRIPT from 'classpath:db/init.sql'", dataSource),
                "", "");

        // Add dao to use in class
        TodoDao dao = new Sql2oTodoDao(sql2o);

        // Create Gson object for converting to Json
        Gson gson = new Gson();

        // Main application starting path - fetches all to do's from database and displays them
        get("/", (req, res) -> {
            res.redirect("index.html");
            return null;
        });

        // GET API method -returns all Todos and populates web application
        get("/api/v1/todos", "application/json",
                (req, res) -> dao.findAll(), gson::toJson);

        // POST API method - creates new to'do in database
        post("/api/v1/todos", "application/json", (req, res) -> {
            Todo todo = gson.fromJson(req.body(), Todo.class);
            dao.create(todo);
            res.status(201);
            return todo;
        }, gson::toJson);
    }

}
