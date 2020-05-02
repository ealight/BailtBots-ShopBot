import config.Config;
import menu.Messages;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import chatuser.ChatUser;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class Main extends TelegramLongPollingBot {
    private Config config = new Config();

    private Messages messages = new Messages();
    private ChatUser chatUser = new ChatUser();

    public static void main(String[] args) throws ClassNotFoundException, SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Main());

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Message message;
        if (update.hasMessage()) {
            message = update.getMessage();
        } else {
            message = update.getCallbackQuery().getMessage();
        }

        Long telegramID = message.getChatId();

        if (!chatUser.isUserCreated(telegramID)) {
            chatUser.createUser(telegramID, message);
            return;
        }
        if (update.hasMessage()) {
            if (message.getContact() != null) {
                chatUser.checkPhoneNumber(telegramID, message);
            }
            else if(message.getText().equals(chatUser.getUser(telegramID).getMessageText())) {
                return;
            }
            if (message.hasText()) {
                System.out.println("3: " + chatUser.getUser(telegramID).getTelegramID());
                if (chatUser.getUser(telegramID).isTextFind()) {
                    if(message.isUserMessage()) {
                        chatUser.getUser(telegramID).setTextFind(message.getText());
                        messages.editFindMenu(message, chatUser.getUser(telegramID));
                    }
                }
                else {
                    chatUser.getUser(telegramID).getCommands().performCommand(message.getText());
                }
            }

            chatUser.getUser(telegramID).setMessageText(message.getText());

        }
        else if (update.hasCallbackQuery()) {
            String menuName = update.getCallbackQuery().getData().substring(update.getCallbackQuery().getData().indexOf(":") + 1);
            if (chatUser.getUser(telegramID).getCategory().equals("none") && !chatUser.getUser(telegramID).getCurrentSection().equals("basket_page") && !chatUser.getUser(telegramID).isFind() ||
                    !menuName.equals(chatUser.getUser(telegramID).getInlineMenu().toString())) {
                return;
            }

            chatUser.getUser(telegramID).getButton().setUpdate(update);
            chatUser.getUser(telegramID).getButton().performCommand(update.getCallbackQuery().getData().substring(0, update.getCallbackQuery().getData().indexOf("-")));
        }
    }

    public String getBotUsername() {
        return config.getBotUsername();
    }

    public String getBotToken() {
        return config.getBotToken();
    }
}
