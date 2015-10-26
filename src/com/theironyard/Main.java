package com.theironyard;

import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList(); //this allows us to store multiple user accounts

	// static HTML file
        Spark.staticFileLocation("/public");
        Spark.init();
        Spark.post(
                "/create-account",
                ((request, response) -> {
                    User user = new User();
                    user.name = request.queryParams("username"); //sets the name
                    user.password = request.queryParams("password"); //creating user object
                    users.add(user);
                    response.redirect("/");
                    return "";
                })
        );

        Spark.get (
                "/accounts",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    m.put("count", users.size()); //tell it what to inject
                    m.put("accounts", users); //tell it what to inject
                    return new ModelAndView(m, "accounts.html");
                }),
                new MustacheTemplateEngine()
        );
    }
}
