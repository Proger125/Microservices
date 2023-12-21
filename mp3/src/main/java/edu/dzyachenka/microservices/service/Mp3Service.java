package edu.dzyachenka.microservices.service;

import edu.dzyachenka.microservices.model.Mp3Model;
import edu.dzyachenka.microservices.model.dto.DeleteMp3ModelDto;
import org.apache.tika.exception.TikaException;
import org.springframework.http.HttpRange;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

public interface Mp3Service {

    Mp3Model addRecord(final MultipartFile file) throws IOException, TikaException, SAXException, InterruptedException;

    byte[] getRecordById(final Integer id, final String range) throws IOException;

    DeleteMp3ModelDto deleteRecordsById(final List<Integer> ids);
}
