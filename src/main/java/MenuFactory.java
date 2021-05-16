import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.LinkedList;
import java.util.List;

public class MenuFactory {
    private final ApiClient client = new ApiClient(System.getenv("boardId"), System.getenv("key"), System.getenv("token"));
    public BotApiMethod<?> createMainMenu(String chatId){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите необходимый режим:");
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List <List<InlineKeyboardButton>> rows = new LinkedList<>();

        List <InlineKeyboardButton> buttons = new LinkedList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("Trello");
        button.setCallbackData("trello:");
        buttons.add(button);
        rows.add(buttons);


        markup.setKeyboard(rows);

        message.setReplyMarkup(markup);
        return message;

    }

    public  BotApiMethod<?> createBoardMenu(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Список листов");
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List <List<InlineKeyboardButton>> rows = new LinkedList<>();

        for (BoardList boardlist: client.getBoardsLists()){
            List <InlineKeyboardButton> buttons = new LinkedList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(boardlist.getName());
            button.setCallbackData("cards_list:" + boardlist.getId());
            buttons.add(button);
            rows.add(buttons);
        }


        markup.setKeyboard(rows);

        message.setReplyMarkup(markup);

        return message;
    }

    public  BotApiMethod<?> createListMenu(String chatId, String listId){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Карточки выбранного листа:");
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List <List<InlineKeyboardButton>> rows = new LinkedList<>();

        for (Card card: client.getCards(listId)){
            List <InlineKeyboardButton> buttons = new LinkedList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(card.getName());
            button.setCallbackData("card:" + card.getId());
            buttons.add(button);
            rows.add(buttons);
        }


        markup.setKeyboard(rows);

        message.setReplyMarkup(markup);

        return message;
    }

    public  BotApiMethod<?> createDescriptionCard(String id, String cardId){
        SendMessage message = new SendMessage();
        message.setChatId(id);
        message.setText("Содержимое карточки:");
        Card card = client.getCard(cardId);
        StringBuilder result = new StringBuilder(card.getName() + "\n" + card.getDescription() + "\n");

        if(card.hasAttachments()){
            List <Attachment> s = card.getAttachments();
            for (Attachment attachment : s){
                result.append(attachment.getName()).append(" :\n ").append(attachment.getUrl()).append("\n");

            }
        }
        message.setText(result.toString());
        return message;

    }
}
