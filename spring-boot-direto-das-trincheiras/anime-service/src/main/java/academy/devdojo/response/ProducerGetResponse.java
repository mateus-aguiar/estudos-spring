package academy.devdojo.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ProducerGetResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
