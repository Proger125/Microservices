package edu.dzyachenka.microservices.model;

import jakarta.persistence.*;

@Entity
@Table(name = "resources")
public class Mp3Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private byte[] resource;

    public Mp3Model() {
        this.name = "";
    }

    public Mp3Model(final String name, final byte[] resource) {
        this.name = name;
        this.resource = resource;
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

    public byte[] getResource() {
        return resource;
    }

    public void setResource(byte[] resource) {
        this.resource = resource;
    }
}
