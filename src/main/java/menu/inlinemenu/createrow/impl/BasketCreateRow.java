package menu.inlinemenu.createrow.impl;

import menu.inlinemenu.InlineMenu;
import menu.inlinemenu.KeyboardRowFactory;
import menu.inlinemenu.createrow.CreateRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import service.OrderService;
import service.ProductService;

public class BasketCreateRow implements CreateRow {
    private OrderService orderService = new OrderService();
    private ProductService productService = new ProductService();

    @Override
    public void createProducts(KeyboardRowFactory factoryRow, Integer page, Integer maxRows, InlineMenu inlineMenu, Long telegramId, String category, String findText) {
        if(orderService.getRamsAtPage((page - 1) * maxRows, maxRows, telegramId).size() == 0) {
            factoryRow.getRow(0).add(new InlineKeyboardButton().setText("Ваша корзина пуста.").setCallbackData("NotFound"));
        }
        for (int i = 0; i < orderService.getRamsAtPage((page - 1) * maxRows, maxRows, telegramId).size(); i++) {
            factoryRow.getRow(i).add(new InlineKeyboardButton().setText(i+1 + ". (#"+ orderService.getRamsAtPage((page - 1) * maxRows, maxRows, telegramId).get(i).getId() +") " + productService.getRamById(orderService.getRamsAtPage((page - 1) * maxRows, maxRows, telegramId).get(i).getProductId()).getName()).setCallbackData("Other-" + orderService.getRamsAtPage((page - 1) * maxRows, maxRows, telegramId).get(i).getId() + ":" + inlineMenu.toString()));
        }
    }

    @Override
    public void createPages(KeyboardRowFactory factoryRow, Integer page, Integer maxRows, Long telegramId, String category, String findText, InlineMenu inlineMenu) {
        factoryRow.getRow(maxRows).add(new InlineKeyboardButton().setText("Страница " + (page) + "/" + orderService.getPagesCount(telegramId)).setCallbackData("Page-null:" + inlineMenu.toString()));
    }
}
