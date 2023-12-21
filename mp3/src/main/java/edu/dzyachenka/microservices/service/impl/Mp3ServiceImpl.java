package edu.dzyachenka.microservices.service.impl;

import edu.dzyachenka.microservices.exception.FormatNotSupportedException;
import edu.dzyachenka.microservices.model.Mp3Model;
import edu.dzyachenka.microservices.model.dto.DeleteMp3ModelDto;
import edu.dzyachenka.microservices.model.dto.MetadataDto;
import edu.dzyachenka.microservices.processor.MetadataProcessor;
import edu.dzyachenka.microservices.repository.Mp3Repository;
import edu.dzyachenka.microservices.service.Mp3Service;
import edu.dzyachenka.microservices.util.MetadataExtractor;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.*;

@Service
public class Mp3ServiceImpl implements Mp3Service {

    private static final String AUDIO_FORMAT = "audio/mpeg";
    @Autowired
    private Mp3Repository mp3Repository;
    @Autowired
    private MetadataExtractor metadataExtractor;
    @Autowired
    private MetadataProcessor metadataProcessor;

    @Override
    @Transactional
    public Mp3Model addRecord(final MultipartFile file) throws IOException, TikaException, SAXException, InterruptedException {
        if (!Objects.equals(file.getContentType(), AUDIO_FORMAT)) {
            throw new FormatNotSupportedException("Only audio/mpeg files are supported");
        }
        final MetadataDto metadata = metadataExtractor.extractMetadata(file);
        final String fileName = file.getOriginalFilename();
        final Mp3Model result = mp3Repository.save(new Mp3Model(fileName, file.getBytes()));

        metadata.setResourceId(result.getId().toString());

        metadataProcessor.saveMp3Metadata(metadata);
        return result;
    }

    @Override
    public byte[] getRecordById(final Integer id, final String range) {
        final Mp3Model record = mp3Repository.findById(id).orElseThrow(NoSuchElementException::new);
        byte[] bytes;
        if (range == null || range.isEmpty()) {
            bytes = record.getResource();
        } else {
            final String[] ranges = range.split("-");
            final int startRange = Integer.parseInt(ranges[0]);
            final int endRange = Integer.parseInt(ranges[1]);
            bytes = Arrays.copyOfRange(record.getResource(), startRange, endRange);
        }
        return bytes;
    }

    @Override
    public DeleteMp3ModelDto deleteRecordsById(final List<Integer> ids) {
        final List<Mp3Model> records = (List<Mp3Model>) mp3Repository.findAllById(ids);
        final List<Integer> deletedRecordsIds = new ArrayList<>();
        for (var record : records) {
            mp3Repository.delete(record);
            deletedRecordsIds.add(record.getId());
        }
        return new DeleteMp3ModelDto(deletedRecordsIds);
    }
}
