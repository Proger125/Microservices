package edu.dzyachenka.microservices.model;

import jakarta.persistence.*;

@Entity
@Table(name = "resources")
public class Mp3Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public Mp3Model() {
        this.name = "";
    }

    public Mp3Model(final String name) {
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
