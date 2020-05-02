package menu.inlinemenu.createrow;

import menu.inlinemenu.InlineMenu;
import menu.inlinemenu.KeyboardRowFactory;
import model.User;

public interface CreateRow {
    void createProducts(KeyboardRowFactory factoryRow, Integer page, Integer maxRows, InlineMenu inlineMenu, User user, String category, String findText);
    void createPages(KeyboardRowFactory factoryRow, Integer page, Integer maxRows, Long telegramId, String category, String findText, InlineMenu inlineMenu);
}
