package edu.dzyachenka.microservices.controller;

import edu.dzyachenka.microservices.model.SongModel;
import edu.dzyachenka.microservices.model.dto.AddSongModelDto;
import edu.dzyachenka.microservices.model.dto.DeleteSongModelDto;
import edu.dzyachenka.microservices.model.dto.GetSongModelDto;
import edu.dzyachenka.microservices.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @PostMapping
    public AddSongModelDto addSong(@RequestBody final SongModel songModel) {
        return songService.addSong(songModel);
    }

    @GetMapping("/{id}")
    public GetSongModelDto getSongById(@PathVariable final Integer id) {
        return songService.getSongById(id);
    }

    @DeleteMapping
    public DeleteSongModelDto deleteSongsById(@RequestParam(name = "id") final List<Integer> ids) {
        return songService.deleteSongsById(ids);
    }
}
