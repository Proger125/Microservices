package edu.dzyachenka.microservices.model.dto;

import java.util.List;

public class DeleteMp3ModelDto {

    private List<Integer> ids;

    public DeleteMp3ModelDto(final List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getIds() {
        return this.ids;
    }

    public void setIds(final List<Integer> ids) {
        this.ids = ids;
    }
}
