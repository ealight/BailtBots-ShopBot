package menu.inlinemenu;

import menu.inlinemenu.createrow.CreateRow;
import menu.inlinemenu.createrow.impl.BasketCreateRow;
import menu.inlinemenu.createrow.impl.FindCreateRow;
import menu.inlinemenu.createrow.impl.MarketCreateRow;
import menu.inlinemenu.section.Section;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import service.OrderService;
import service.ProductService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InlineMenu {
    final static int MAX_ROWS = 8;

    private KeyboardRowFactory factoryRow = new KeyboardRowFactory();

    Map<String, CreateRow> createRowFactory = new HashMap<>();

    public InlineMenu(){
        createRowFactory.put("basket", new BasketCreateRow());
        createRowFactory.put("market", new MarketCreateRow());
        createRowFactory.put("find", new FindCreateRow());
    }

    public void setButtonMenu(SendMessage sendMessage, EditMessageReplyMarkup editMessage, Integer page, String category, Long telegramId, String findText, String action){
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboardRowList = new ArrayList<>();

        factoryRow.clearRow();
        factoryRow.createRow(MAX_ROWS + 1);

        CreateRow createRow = createRowFactory.get(action);
        createRow.createProducts(factoryRow, page, MAX_ROWS, InlineMenu.this, telegramId, category, findText);

        factoryRow.getRow(MAX_ROWS ).add(new InlineKeyboardButton().setText("Назад").setCallbackData("Previous-null:" + InlineMenu.this.toString()));

        createRow.createPages(factoryRow, page, MAX_ROWS, telegramId, category, findText, InlineMenu.this);

        factoryRow.getRow(MAX_ROWS ).add(new InlineKeyboardButton().setText("Вперед").setCallbackData("Next-null:" + InlineMenu.this.toString()));


        for(int i = 0; i < MAX_ROWS + 1; i++) {
            keyboardRowList.add(factoryRow.getRow(i));
        }

        markupInline.setKeyboard(keyboardRowList);
        if(editMessage == null) {
            sendMessage.setReplyMarkup(markupInline);
        }
        else if(sendMessage == null){
            editMessage.setReplyMarkup(markupInline);
        }
    }

    public void setButtonMenuFind(SendMessage sendMessage, EditMessageReplyMarkup editMessage, String text){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRowList = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonFirstRow = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonSecondRow = new ArrayList<>();

        keyboardButtonFirstRow.add(new InlineKeyboardButton().setText(text).setCallbackData("SetTextFind-null:" + InlineMenu.this.toString()));
        keyboardButtonSecondRow.add(new InlineKeyboardButton().setText("Отмена").setCallbackData("CancelFind-null:" + InlineMenu.this.toString()));
        keyboardButtonSecondRow.add(new InlineKeyboardButton().setText("Найти").setCallbackData("LetFind-null:" + InlineMenu.this.toString()));

        keyboardRowList.add(keyboardButtonFirstRow);
        keyboardRowList.add(keyboardButtonSecondRow);

        keyboardMarkup.setKeyboard(keyboardRowList);
        if(editMessage == null) {
            sendMessage.setReplyMarkup(keyboardMarkup);
        }
        else if(sendMessage == null){
            editMessage.setReplyMarkup(keyboardMarkup);
        }
    }

    public void setButtonMenuInMarket(SendPhoto sendPhoto){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRowList = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

        keyboardButtonsRow.add(new InlineKeyboardButton().setText("В корзину").setCallbackData("ToBasket-null:" + InlineMenu.this.toString()));

        keyboardRowList.add(keyboardButtonsRow);

        keyboardMarkup.setKeyboard(keyboardRowList);
        sendPhoto.setReplyMarkup(keyboardMarkup);
    }

    public void setButtonMenuInBasket(SendPhoto sendPhoto){
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardRowList = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();

        keyboardButtonsRow.add(new InlineKeyboardButton().setText("Подвердить покупку").setCallbackData("AcceptOrder-null:" + InlineMenu.this.toString()));

        keyboardRowList.add(keyboardButtonsRow);

        keyboardMarkup.setKeyboard(keyboardRowList);
        sendPhoto.setReplyMarkup(keyboardMarkup);
    }

}
