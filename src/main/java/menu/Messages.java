package menu;

import config.Config;
import menu.inlinemenu.InlineMenu;
import menu.inlinemenu.section.Section;
import menu.inlinemenu.section.impl.BasketPageSection;
import menu.inlinemenu.section.impl.ComponentPageSection;
import menu.staticmenu.StaticMenu;
import model.Product;
import model.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.toIntExact;

public class Messages extends TelegramLongPollingBot {
    private Config config = new Config();
    private StaticMenu staticMenu = new StaticMenu();
    private InlineMenu inlineMenu;

    Map<String, Section> sectionMesFactory = new HashMap<>();

    public Messages() {
        sectionMesFactory.put("component_page", new ComponentPageSection());
        sectionMesFactory.put("basket_page", new BasketPageSection());
    }

    public void sendMsgForInlineMenu(String s, User user) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getTelegramID());
        sendMessage.setText(s);
        inlineMenu = new InlineMenu();
        user.setInlineMenu(inlineMenu);
        System.out.println(inlineMenu);

        String currentSection = user.getCurrentSection();
        Section section = sectionMesFactory.get(currentSection);
        section.sendMessage(user, sendMessage);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMsgForStaticMenu(String s, String menuNav, User user) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        System.out.println("2: " + user.getTelegramID());
        sendMessage.setChatId(user.getTelegramID());
        // sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(s);
        try {
            if (!menuNav.isEmpty()) {
                    if (menuNav == staticMenu.getMenuNext()) {
                        staticMenu.setButtonMenu(sendMessage, user.getCurrentSection());
                    } else if (menuNav == staticMenu.getMenuPrev()) {
                        user.setFind(false);
                        staticMenu.setButtonMenu(sendMessage, user.getLastSection());
                        user.setTempSection(user.getCurrentSection());
                        user.setCurrentSection(user.getLastSection());
                        user.setLastSection(user.getTempSection());
                    }
            }
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void editMessageForInlineMenu(Update update, User user){
        EditMessageReplyMarkup new_message = new EditMessageReplyMarkup();
        new_message.setChatId(update.getCallbackQuery().getMessage().getChatId());
        new_message.setMessageId(toIntExact(update.getCallbackQuery().getMessage().getMessageId()));
        new_message.setInlineMessageId(update.getCallbackQuery().getInlineMessageId());

        String currentSection = user.getCurrentSection();
        Section section = sectionMesFactory.get(currentSection);
        section.editButton(user, new_message);

        try {
            execute(new_message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void editFindMenu(Message message, User user){
        EditMessageReplyMarkup new_message = new EditMessageReplyMarkup();
        new_message.setChatId(user.getInlineButtonForFind().getMessage().getChatId());
        new_message.setMessageId(toIntExact(user.getInlineButtonForFind().getMessage().getMessageId()));
        new_message.setInlineMessageId(user.getInlineButtonForFind().getInlineMessageId());

        user.getInlineMenu().setButtonMenuFind(null, new_message, "Ищем: " + message.getText());

        try {
            execute(new_message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendInlineMenuWithPhoto(Update update, Product item, String text, User user, String userSection){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sendPhoto.setPhoto(item.getImage());
        sendPhoto.setCaption(text);

        Section section = sectionMesFactory.get(userSection);
        section.sendPhoto(user, sendPhoto);

        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return config.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }
}
