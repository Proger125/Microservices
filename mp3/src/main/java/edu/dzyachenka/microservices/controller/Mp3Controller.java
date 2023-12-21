package edu.dzyachenka.microservices.controller;

import edu.dzyachenka.microservices.model.Mp3Model;
import edu.dzyachenka.microservices.model.dto.DeleteMp3ModelDto;
import edu.dzyachenka.microservices.service.Mp3Service;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/resources")
public class Mp3Controller {
    @Autowired
    private Mp3Service mp3Service;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Mp3Model> addRecord(@RequestBody MultipartFile file) throws IOException, TikaException, SAXException, InterruptedException {
        return new ResponseEntity<>(mp3Service.addRecord(file), HttpStatus.CREATED);
    }

    @GetMapping( value = "/{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody ResponseEntity<byte[]> getRecordById(@PathVariable final Integer id, @RequestHeader(name = "Range", required = false) String range) throws IOException {
        final byte[] bytes =  mp3Service.getRecordById(id, range);
        if (range == null || range.isEmpty()) {
            return new ResponseEntity<>(bytes, HttpStatus.OK);
        }
        return new ResponseEntity<>(bytes, HttpStatus.PARTIAL_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<DeleteMp3ModelDto> deleteRecordsById(@RequestParam(name = "id") final List<Integer> ids) {
        return new ResponseEntity<>(mp3Service.deleteRecordsById(ids), HttpStatus.OK);
    }
}
