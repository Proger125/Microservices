package edu.dzyachenka.microservices.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import edu.dzyachenka.microservices.exception.FormatNotSupportedException;
import edu.dzyachenka.microservices.model.Mp3Model;
import edu.dzyachenka.microservices.model.dto.DeleteMp3ModelDto;
import edu.dzyachenka.microservices.repository.Mp3Repository;
import edu.dzyachenka.microservices.service.Mp3Service;
import edu.dzyachenka.microservices.util.Mp3ModelIdGenerator;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class Mp3ServiceImpl implements Mp3Service {

    private static final String AUDIO_FORMAT = "audio/mpeg";
    private static final String ROUTING_KEY = "resourceQueue";

    @Autowired
    private AmqpTemplate template;
    @Autowired
    private AmazonS3 amazonS3;
    @Autowired
    private Mp3Repository mp3Repository;
    @Value("${config.aws.s3.bucket}")
    private String bucketName;

    @Override
    public Mp3Model addRecord(final MultipartFile file) throws IOException {
        if (!Objects.equals(file.getContentType(), AUDIO_FORMAT)) {
            throw new FormatNotSupportedException("Only audio/mpeg files are supported");
        }
        final Integer mp3RecordId = Mp3ModelIdGenerator.generateMp3Id();
        final String fileName = file.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucketName, mp3RecordId.toString(), file.getInputStream(), new ObjectMetadata()));
        final Mp3Model result = mp3Repository.save(new Mp3Model(mp3RecordId, fileName));

        sendToRabbitMQ(result.getId());

        return result;
    }

    private void sendToRabbitMQ(final Object message) {
        template.convertAndSend(ROUTING_KEY, message);
    }

    @Override
    public byte[] getRecordById(final Integer id, final String range) throws IOException {
        final Mp3Model record = mp3Repository.findById(id).orElseThrow(NoSuchElementException::new);
        final S3Object s3Object = amazonS3.getObject(bucketName, record.getId().toString());
        final InputStream inputStream =  s3Object.getObjectContent();
        byte[] bytes;
        if (range == null || range.isEmpty()) {
            bytes = inputStream.readAllBytes();
        } else {
            final String[] ranges = range.split("-");
            final int startRange = Integer.parseInt(ranges[0]);
            final int endRange = Integer.parseInt(ranges[1]);
            final int length = endRange - startRange;
            bytes = new byte[length];
            inputStream.read(bytes, startRange, length);
        }
        return bytes;
    }

    @Override
    public DeleteMp3ModelDto deleteRecordsById(final List<Integer> ids) {
        final List<Mp3Model> records = (List<Mp3Model>) mp3Repository.findAllById(ids);
        final List<Integer> deletedRecordsIds = new ArrayList<>();
        for (var record : records) {
            amazonS3.deleteObject(bucketName, record.getId().toString());
            mp3Repository.delete(record);
            deletedRecordsIds.add(record.getId());
        }
        return new DeleteMp3ModelDto(deletedRecordsIds);
    }
}
