package edu.dzyachenka.microservices.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dzyachenka.microservices.model.dto.MetadataDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class MetadataProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(MetadataProcessor.class);

    private static final String METADATA_URI = "http://localhost:8081/songs";
    private static final int SUCCESS_STATUS_CODE = 200;
    private static final int CREATED_STATUS_CODE = 201;

    public void saveMp3Metadata(final MetadataDto metadata) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestBody = objectMapper.writeValueAsString(metadata);
        final StringEntity entity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            final HttpPost request = new HttpPost(METADATA_URI);
            request.setEntity(entity);
            final HttpResponse response = httpClient.execute(request);
            final int statusCode = response.getStatusLine().getStatusCode();
            System.out.println(statusCode);
            final String responseContent = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
            if (statusCode == CREATED_STATUS_CODE || statusCode == SUCCESS_STATUS_CODE) {
                LOG.info("Song was created with id: {}", responseContent);
            } else {
                LOG.error("Something went wrong: {}", responseContent);
            }

        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }
}
