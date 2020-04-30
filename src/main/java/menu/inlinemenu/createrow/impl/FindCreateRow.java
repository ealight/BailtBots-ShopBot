package menu.inlinemenu.createrow.impl;

import menu.inlinemenu.InlineMenu;
import menu.inlinemenu.KeyboardRowFactory;
import menu.inlinemenu.createrow.CreateRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import service.ProductService;

public class FindCreateRow implements CreateRow {
    private ProductService productService = new ProductService();

    @Override
    public void createProducts(KeyboardRowFactory factoryRow, Integer page, Integer maxRows, InlineMenu inlineMenu, Long telegramId, String category, String findText) {
        if(productService.getItemsByNameAtPage((page - 1) * maxRows, maxRows, findText).size() == 0) {
            factoryRow.getRow(0).add(new InlineKeyboardButton().setText("По вашему запросу ничего не найдено.").setCallbackData("NotFound-null:null"));
        }
        for (int i = 0; i < productService.getItemsByNameAtPage((page - 1) * maxRows, maxRows, findText).size(); i++) {
            factoryRow.getRow(i).add(new InlineKeyboardButton().setText("(" + productService.getItemsByNameAtPage((page - 1) * maxRows, maxRows, findText).get(i).getPrice() + " грн) " + productService.getItemsByNameAtPage((page - 1) * maxRows, maxRows, findText).get(i).getName()).setCallbackData("Other-" + productService.getItemsByNameAtPage((page - 1) * maxRows, maxRows, findText).get(i).getId() + ":" + inlineMenu.toString()));
        }
    }

    @Override
    public void createPages(KeyboardRowFactory factoryRow, Integer page, Integer maxRows, Long telegramId, String category, String findText, InlineMenu inlineMenu) {
        factoryRow.getRow(maxRows).add(new InlineKeyboardButton().setText("Страница " + (page) + "/" + productService.getPagesCountForName(findText)).setCallbackData("Page-null:" + inlineMenu.toString()));
    }
}
