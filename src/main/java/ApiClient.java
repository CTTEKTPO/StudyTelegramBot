import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.SimpleType;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.util.List;

/**
 * Клиент для взаимодействия с API
 * */
public class ApiClient {
    private final String boardListsLinks;
    private final String cardsLink;
    private final String cardLink;
    private final String attachmentsLink;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ApiClient(String idBoard, String key, String token){

        boardListsLinks = "https://api.trello.com/1/boards/"+idBoard+"/lists?key="+key+"&token="+token;
        cardsLink = "https://api.trello.com/1/lists/%s/cards?key="+key +"&token=" + token;
        cardLink = "https://api.trello.com/1/cards/%s/?key="+key +"&token=" + token;
        attachmentsLink = "https://api.trello.com/1/cards/%s/attachments?key="+key +"&token=" + token;
    }
    public Card getCard(String id){
        Card card = null;
        try {
            card = objectMapper.readValue(readJsonStringFromUrl(String.format(cardLink, id)), SimpleType.construct(Card.class));
            if(card.hasAttachments()) {
                card.setAttachments(objectMapper.readValue(readJsonStringFromUrl(
                        String.format(attachmentsLink, id)), CollectionType.construct(
                        List.class, SimpleType.construct(Attachment.class))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return card;
    }

    public List<Card> getCards(String id){
        List <Card> list = null;
        try {
            list = objectMapper.readValue(readJsonStringFromUrl(String.format(cardsLink, id)), CollectionType.construct
                    (List.class, SimpleType.construct(Card.class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<BoardList> getBoardsLists(){
        List <BoardList> list = null;
        try {
            list = objectMapper.readValue(readJsonStringFromUrl(boardListsLinks), CollectionType.construct
                    (List.class, SimpleType.construct(BoardList.class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private String readJsonStringFromUrl(String url){
        String result = null;
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {

                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);


            } catch (ClientProtocolException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }



}
