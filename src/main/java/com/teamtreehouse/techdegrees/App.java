package com.teamtreehouse.techdegrees;


import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

public class App {

    public static void main(String[] args) {
        staticFileLocation("/public");

        // Main application starting path - should fetch all to do's from database and display them
        get("/", (req, res) -> {
            res.redirect("index.html");
            return null;
        });

    }

}
