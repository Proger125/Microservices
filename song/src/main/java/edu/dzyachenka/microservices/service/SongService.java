package edu.dzyachenka.microservices.service;

import edu.dzyachenka.microservices.model.SongModel;
import edu.dzyachenka.microservices.model.dto.AddSongModelDto;
import edu.dzyachenka.microservices.model.dto.DeleteSongModelDto;
import edu.dzyachenka.microservices.model.dto.GetSongModelDto;

import java.util.List;

public interface SongService {

    AddSongModelDto addSong(final SongModel songModel);

    GetSongModelDto getSongById(final Integer id);

    DeleteSongModelDto deleteSongsById(final List<Integer> ids);
}
