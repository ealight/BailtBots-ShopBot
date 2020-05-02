package menu.staticmenu;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class StaticMenu {
    private static final String MENU_NEXT = "Next";
    private static final String MENU_PREV = "Previous";

    public void setButtonMenu(SendMessage sendMessage, String pageName){

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        switch(pageName) {

            case "basket_page": {
                KeyboardRow keyboardFirstRow = new KeyboardRow();

                keyboardFirstRow.add(new KeyboardButton("Назад"));

                keyboardRowList.add(keyboardFirstRow);
                break;
            }
            case "start_page": {
                KeyboardRow keyboardFirstRow = new KeyboardRow();

                keyboardFirstRow.add(new KeyboardButton( "\uD83D\uDCBC Корзина"));
                keyboardFirstRow.add(new KeyboardButton("\uD83D\uDCBB Комп'ютерные комплектующие"));

                keyboardRowList.add(keyboardFirstRow);
                break;
            }
            case "start_page_getNumber": {
                KeyboardRow keyboardFirstRow = new KeyboardRow();
                KeyboardRow keyboardSecondRow = new KeyboardRow();

                keyboardFirstRow.add(new KeyboardButton( "\uD83D\uDCBC Корзина"));
                keyboardFirstRow.add(new KeyboardButton("\uD83D\uDCBB Комп'ютерные комплектующие"));

                keyboardSecondRow.add(new KeyboardButton( "Подтвердить номер").setRequestContact(true));

                keyboardRowList.add(keyboardFirstRow);
                keyboardRowList.add(keyboardSecondRow);

                break;
            }
            case "component_page": {
                KeyboardRow keyboardFirstRow = new KeyboardRow();
                KeyboardRow keyboardSecondRow = new KeyboardRow();
                KeyboardRow keyboardThreeRow = new KeyboardRow();
                KeyboardRow keyboardFourRow = new KeyboardRow();

                keyboardFirstRow.add(new KeyboardButton( "Оперативная пам'ять"));
                keyboardFirstRow.add(new KeyboardButton( "SSD диски"));
                keyboardFirstRow.add(new KeyboardButton( "Процессоры"));
                keyboardFirstRow.add(new KeyboardButton( "Жесткие диски"));

                keyboardSecondRow.add(new KeyboardButton( "Видеокарты"));
                keyboardSecondRow.add(new KeyboardButton( "Материнские платы"));
                keyboardSecondRow.add(new KeyboardButton( "Блоки питания"));
                keyboardSecondRow.add(new KeyboardButton( "Мониторы"));

                keyboardThreeRow.add(new KeyboardButton( "Поиск"));

                keyboardFourRow.add(new KeyboardButton("Назад"));

                keyboardRowList.add(keyboardFirstRow);
                keyboardRowList.add(keyboardSecondRow);
                keyboardRowList.add(keyboardThreeRow);
                keyboardRowList.add(keyboardFourRow);

                break;
            }

        }
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    public static String getMenuNext() {
        return MENU_NEXT;
    }

    public static String getMenuPrev() {
        return MENU_PREV;
    }

}
