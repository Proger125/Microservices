package edu.dzyachenka.microservices.service.impl;

import edu.dzyachenka.microservices.model.SongModel;
import edu.dzyachenka.microservices.model.dto.AddSongModelDto;
import edu.dzyachenka.microservices.model.dto.DeleteSongModelDto;
import edu.dzyachenka.microservices.model.dto.GetSongModelDto;
import edu.dzyachenka.microservices.repository.SongRepository;
import edu.dzyachenka.microservices.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongRepository songRepository;
    @Override
    public AddSongModelDto addSong(SongModel songModel) {
        final SongModel addedSongModel = songRepository.save(songModel);
        return new AddSongModelDto(addedSongModel.getId());
    }

    @Override
    public GetSongModelDto getSongById(Integer id) {
        final SongModel songModel =  songRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        return new GetSongModelDto(songModel.getName(),
                songModel.getArtist(),
                songModel.getAlbum(),
                songModel.getLength(),
                songModel.getResourceId(),
                songModel.getYear());
    }

    @Override
    public DeleteSongModelDto deleteSongsById(List<Integer> ids) {
        final List<Integer> result = new ArrayList<>();
        final List<SongModel> songs = (List<SongModel>) songRepository.findAllById(ids);
        for (var song : songs) {
            songRepository.delete(song);
            result.add(song.getId());
        }
        return new DeleteSongModelDto(result);
    }
}
