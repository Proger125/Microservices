package edu.dzyachenka.microservices.model.dto;

public class AddSongModelDto {
    private Integer id;

    public AddSongModelDto(final Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }
}
