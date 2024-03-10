package edu.dzyachenka.microservices.feign;

import edu.dzyachenka.microservices.model.dto.MetadataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("${application.song.name}")
public interface MetadataFeignClient {

    @PostMapping("/songs")
    String addSong(final MetadataDto dto);
}
