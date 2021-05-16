import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/***/

@JsonIgnoreProperties(ignoreUnknown = true)
public class Card {

    private final String description;
    private final String name;
    private final String id;
    private final int attachmentsCount;
    private List<Attachment> attachments;

    @JsonCreator
    public Card(@JsonProperty("desc")String description, @JsonProperty("name") String name,
                @JsonProperty("id")String idCard, @JsonProperty("badges")Map<String,Object> attachmentsCount){
        this.description = description;
        this.name = name;
        this.id = idCard;

        if(attachmentsCount.get("attachments") != null)
            this.attachmentsCount = (int) attachmentsCount.get("attachments");
        else
            this.attachmentsCount = 0;
    }
    public void setAttachments(List<Attachment> attachments){
        this.attachments = attachments;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public boolean hasAttachments(){return attachmentsCount > 0;}

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
