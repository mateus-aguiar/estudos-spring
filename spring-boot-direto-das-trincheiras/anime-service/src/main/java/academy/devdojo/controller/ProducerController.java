package academy.devdojo.controller;

import academy.devdojo.domain.Producer;
import academy.devdojo.mapper.ProducerMapper;
import academy.devdojo.request.ProducerPostRequest;
import academy.devdojo.response.ProducerGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {
    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;

    @GetMapping
    public List<Producer> listAll() {
        return Producer.getProducers();
    }

    @GetMapping("filter")
    public Producer filterByName(@RequestParam String name) {
        return Producer
                .getProducers()
                .stream()
                .filter(producer -> producer.getName().equalsIgnoreCase(name))
                .findFirst().orElse(Producer.builder().build());
    }

    @GetMapping("{id}")
    public Producer findById(@PathVariable Long id) {
        return Producer
                .getProducers()
                .stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst().orElse(null);
    }

    //Idempotente
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
            headers = "x-api-key") //headers forca uma checkagem para ver se existe o  header passado
    public ResponseEntity<ProducerGetResponse> save(@RequestBody ProducerPostRequest producerPostRequest, @RequestHeader HttpHeaders headers) {
        log.info("{}", headers);
        var producer = MAPPER.toProducer(producerPostRequest);
        var response = MAPPER.toProducerGetResponse(producer);

        Producer.getProducers().add(producer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
