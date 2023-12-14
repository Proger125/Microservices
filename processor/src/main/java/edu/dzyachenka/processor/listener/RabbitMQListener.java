package edu.dzyachenka.processor.listener;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@EnableRabbit
@Component
public class RabbitMQListener {
    private static final Logger LOG = LoggerFactory.getLogger(RabbitMQListener.class);

    @Autowired
    private RetryTemplate retryTemplate;

    @RabbitListener(queues = "resourceQueue")
    public void processMessages(final String message) {
        LOG.info("Received message: {}", message);

        try {
            byte[] resource = retryTemplate.execute(ctx -> getResourceById(message));
            LOG.info("Resource: " + Arrays.toString(resource));

            final String song = generateSong();
            final String addSongResult = retryTemplate.execute(ctx -> addSong(song));
            LOG.info("Song: " + addSongResult);
        } catch (InterruptedException | IOException e) {
            LOG.error(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getResourceById(final String id) throws IOException, InterruptedException {
        final URI uri = URI.create("http://localhost:8080/resources/" + id);
        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("accept", "application/json")
                .GET()
                .build();
        final HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
        return response.body();
    }

    private String addSong(final String song) throws IOException, InterruptedException {
        StringEntity entity = new StringEntity(song, ContentType.APPLICATION_JSON);
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost("http://localhost:8082/songs");
            request.setEntity(entity);
            org.apache.http.HttpResponse response = httpClient.execute(request);
            return new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

    private String generateSong() {
        return """
                {
                    "name": "We are the champion",
                    "artist": "Queen",
                    "album": "News of the world",
                    "length": "2:59",
                    "resourceId": "123",
                    "year": "1977"
                }""";
    }
}
