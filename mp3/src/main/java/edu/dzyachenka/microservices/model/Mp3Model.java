package edu.dzyachenka.microservices.model;

import jakarta.persistence.*;

@Entity
@Table(name = "resources")
public class Mp3Model {
    @Id
    private Integer id;

    private String name;

    public Mp3Model() {
        this.id = 0;
        this.name = "";
    }

    public Mp3Model(final Integer id, final String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
