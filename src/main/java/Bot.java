import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Bot extends TelegramLongPollingBot {

    MenuFactory menuService = new MenuFactory();


    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return System.getenv("botName");
    }

    @Override
    public String getBotToken() {

        return System.getenv("botToken");
    }


    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasCallbackQuery()){
            System.out.println(update.getCallbackQuery()); // Получение кнопки которую нажали
        }
        String [] data;
        String id;
        if (update.hasCallbackQuery()){
            data = update.getCallbackQuery().getData().split(":");
            id = update.getCallbackQuery().getMessage().getChatId().toString();
        }else {
            if (update.getMessage().getText().startsWith("/")) {
                data = new String[]{update.getMessage().getText().substring(1)};
            }else return;
            id = update.getMessage().getChatId().toString();
        }
        BotApiMethod<?> message = null;

        switch (data[0]) {
            case "start" : message = menuService.createMainMenu(id); break;
            case "trello" : message = menuService.createBoardMenu(id); break;
            case "cards_list" : message = menuService.createListMenu(id, data[1]); break;
            case "card" : message = menuService.createDescriptionCard(id, data[1]); break;
        }

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }






}
