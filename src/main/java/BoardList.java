import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Класс структура списка доски
 * */

@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardList {
    private final String name;
    private final String id;

    @JsonCreator
    public BoardList(@JsonProperty("id") String personId, @JsonProperty("name") String name) {
        this.id = personId;
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public String getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
