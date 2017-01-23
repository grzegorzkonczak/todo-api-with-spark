package com.checkrise.todos;


import com.checkrise.testing.ApiClient;
import com.checkrise.testing.ApiResponse;
import com.checkrise.todos.dao.Sql2oTodoDao;
import com.checkrise.todos.model.Todo;
import com.google.gson.Gson;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ApiTest {
    public static final String PORT = "4568";
    public static final String TEST_DATASOURCE = "jdbc:h2:mem:testing";
    private Connection conn;
    private ApiClient client;
    private Gson gson;
    private Sql2oTodoDao dao;

    @BeforeClass
    public static void startServer(){
        String[] args = {PORT, TEST_DATASOURCE};
        App.main(args);
    }

    @AfterClass
    public static  void stopServer(){
        Spark.stop();
    }

    @Before
    public void setUp() throws Exception {
        Sql2o sql2o = new Sql2o(TEST_DATASOURCE + ";INIT=RUNSCRIPT from 'classpath:db/init.sql'", "", "");
        dao = new Sql2oTodoDao(sql2o);
        conn = sql2o.open();
        client = new ApiClient("http://localhost:" + PORT);
        gson = new Gson();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingTodosReturnsCreatedStatus() throws Exception {
        Map<String, Object> values = new HashMap<>();
        values.put("name", "Test");
        values.put("completed", true);
        ApiResponse response = client.request("POST", "/api/v1/todos", gson.toJson(values));

        assertEquals(201, response.getStatus());
    }

    @Test
    public void addingTodoCreatesItInDatabase() throws Exception {
        Map<String, Object> values = new HashMap<>();
        values.put("name", "Test");
        values.put("completed", true);

        ApiResponse response = client.request("POST", "/api/v1/todos", gson.toJson(values));
        Todo createdTodo = gson.fromJson(response.getBody(), Todo.class);


        assertEquals(createdTodo, dao.findById(createdTodo.getId()));
    }

    @Test
    public void whenMultipleTodosPresentReturnsAll() throws Exception {
        dao.create(new Todo("Test1", true));
        dao.create(new Todo("Test2", true));

        ApiResponse response = client.request("GET", "/api/v1/todos");
        Todo[] todos = gson.fromJson(response.getBody(), Todo[].class);

        assertEquals(2, todos.length);
    }

    @Test
    public void whenTodoUpdatedReturnsAcceptedStatus() throws Exception {
        Todo todo = newTestTodo();
        dao.create(todo);
        todo.setName("New Name");

        ApiResponse response = client.request("PUT", "/api/v1/todos/" + todo.getId(),
                gson.toJson(todo));

        assertEquals(202, response.getStatus());
    }

    @Test
    public void whenTodoUpdatedDataInTodoChanges() throws Exception {
        Todo todo = newTestTodo();
        dao.create(todo);
        String newName = "New Name";
        todo.setName(newName);

        client.request("PUT", "/api/v1/todos/" + todo.getId(),
                gson.toJson(todo));

        assertEquals(newName, dao.findById(todo.getId()).getName());
    }



    private Todo newTestTodo() {
        return new Todo("Test", true);
    }
}
