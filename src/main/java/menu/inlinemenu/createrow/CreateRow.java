package menu.inlinemenu.createrow;

import menu.inlinemenu.InlineMenu;
import menu.inlinemenu.KeyboardRowFactory;

public interface CreateRow {
    void createProducts(KeyboardRowFactory factoryRow, Integer page, Integer maxRows, InlineMenu inlineMenu, Long telegramId, String category, String findText);
    void createPages(KeyboardRowFactory factoryRow, Integer page, Integer maxRows, Long telegramId, String category, String findText, InlineMenu inlineMenu);
}
