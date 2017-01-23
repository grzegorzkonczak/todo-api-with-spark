package com.checkrise.todos;


import org.sql2o.Sql2o;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

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


        // Main application starting path - should fetch all to do's from database and display them
        get("/", (req, res) -> {
            res.redirect("index.html");
            return null;
        });

    }

}
