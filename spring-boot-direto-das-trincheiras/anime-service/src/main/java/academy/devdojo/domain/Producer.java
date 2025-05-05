package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class Producer {
    private Long id;
    @JsonProperty("name") // Forma de alterar o nome do campo que sera serializado / deserializado para json
    private String name;
    private LocalDateTime createdAt;
    @Getter
    private static List<Producer> producers = new ArrayList<>();

    static {
        var mappa = Producer.builder().id(1L).name("Mappa").createdAt(LocalDateTime.now()).build();
        var madhouse = Producer.builder().id(2L).name("Madhouse").createdAt(LocalDateTime.now()).build();
        producers.addAll(List.of(mappa, madhouse));
    }

}
