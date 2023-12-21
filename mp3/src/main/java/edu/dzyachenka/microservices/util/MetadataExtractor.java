package edu.dzyachenka.microservices.util;

import edu.dzyachenka.microservices.model.dto.MetadataDto;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

@Component
public class MetadataExtractor {
    private static final String TITLE_METADATA = "dc:title";
    private static final String ARTIST_METADATA = "xmpDM:artist";
    private static final String ALBUM_METADATA = "xmpDM:album";
    private static final String LENGTH_METADATA = "xmpDM:duration";
    private static final String YEAR_METADATA = "xmpDM:releaseDate";

    public MetadataDto extractMetadata(final MultipartFile file) throws IOException, TikaException, SAXException {
        final BodyContentHandler handler = new BodyContentHandler();
        final Metadata metadata = new Metadata();
        final InputStream inputStream = file.getInputStream();
        final ParseContext context = new ParseContext();

        final Mp3Parser mp3Parser = new Mp3Parser();
        mp3Parser.parse(inputStream, handler, metadata, context);

        return createMetadata(metadata);
    }

    private MetadataDto createMetadata(final Metadata metadata) {
        MetadataDto result = new MetadataDto();
        result.setName(metadata.get(TITLE_METADATA));
        result.setArtist(metadata.get(ARTIST_METADATA));
        result.setAlbum(metadata.get(ALBUM_METADATA));
        result.setLength(metadata.get(LENGTH_METADATA));
        result.setYear(metadata.get(YEAR_METADATA));
        return result;
    }
}
