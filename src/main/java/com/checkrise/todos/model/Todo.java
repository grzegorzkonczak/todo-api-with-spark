package com.checkrise.todos.model;


public class Todo {
    private int id;
    private String name;
    private boolean isCompleated;

    public Todo(String name, boolean isCompleated) {
        this.name = name;
        this.isCompleated = isCompleated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleated() {
        return isCompleated;
    }

    public void setCompleated(boolean compleated) {
        isCompleated = compleated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;

        if (id != todo.id) return false;
        if (isCompleated != todo.isCompleated) return false;
        return name.equals(todo.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + (isCompleated ? 1 : 0);
        return result;
    }
}
