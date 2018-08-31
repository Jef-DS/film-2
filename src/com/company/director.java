package com.company;

import java.util.Objects;

public class director {
    private String director;

    public director(String director) {
        this.director = director;
    }

    public String getDirector() {
        return director;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        director director1 = (director) o;
        return Objects.equals(director, director1.director);
    }

    @Override
    public int hashCode() {
        return Objects.hash(director);
    }
}
