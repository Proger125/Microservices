package edu.dzyachenka.microservices.model.dto;

import java.util.List;

public class DeleteSongModelDto {
    private List<Integer> ids;

    public DeleteSongModelDto(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
