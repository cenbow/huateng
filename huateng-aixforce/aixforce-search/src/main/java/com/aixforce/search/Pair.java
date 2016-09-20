package com.aixforce.search;

import java.io.Serializable;

public class Pair implements Serializable {
    private static final long serialVersionUID = 3924771832643671901L;
    private String name;
    private final Long id;

    public Pair(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
