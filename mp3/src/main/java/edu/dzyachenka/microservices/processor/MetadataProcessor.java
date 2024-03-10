package edu.dzyachenka.microservices.processor;

import edu.dzyachenka.microservices.feign.MetadataFeignClient;
import edu.dzyachenka.microservices.model.dto.MetadataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MetadataProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(MetadataProcessor.class);

    @Autowired
    private MetadataFeignClient metadataFeignClient;

    public void saveMp3Metadata(final MetadataDto metadata) throws IOException {
        String response = metadataFeignClient.addSong(metadata);
        LOG.info("Song was created with id: {}", response);
    }
}
