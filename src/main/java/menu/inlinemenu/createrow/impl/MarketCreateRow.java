package menu.inlinemenu.createrow.impl;

import menu.inlinemenu.InlineMenu;
import menu.inlinemenu.KeyboardRowFactory;
import menu.inlinemenu.createrow.CreateRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import service.ProductService;

public class MarketCreateRow implements CreateRow {
    private ProductService productService = new ProductService();

    @Override
    public void createProducts(KeyboardRowFactory factoryRow, Integer page, Integer maxRows, InlineMenu inlineMenu, Long telegramId, String category, String findText) {
        for (int i = 0; i < productService.getRamsAtPage((page - 1) * maxRows, maxRows, category).size(); i++) {
            factoryRow.getRow(i).add(new InlineKeyboardButton().setText("(" + productService.getRamsAtPage((page - 1) * maxRows, maxRows, category).get(i).getPrice() + " грн) " + productService.getRamsAtPage((page - 1) * maxRows, maxRows, category).get(i).getName()).setCallbackData("Other-" + productService.getRamsAtPage((page - 1) * maxRows, maxRows, category).get(i).getId() + ":" + inlineMenu.toString()));
        }
    }

    @Override
    public void createPages(KeyboardRowFactory factoryRow, Integer page, Integer maxRows, Long telegramId, String category, String findText, InlineMenu inlineMenu) {
        factoryRow.getRow(maxRows).add(new InlineKeyboardButton().setText("Страница " + (page) + "/" + productService.getPagesCount(category)).setCallbackData("Page-null:" + inlineMenu.toString()));
    }


}
