package menu.inlinemenu.section.impl;

import menu.Messages;
import menu.inlinemenu.section.Section;
import model.Product;
import model.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.OrderService;
import service.ProductService;

public class BasketPageSection implements Section {
    private ProductService productService = new ProductService();
    private OrderService orderService = new OrderService();

    @Override
    public void previous(User user) {
        if (orderService.getPagesCount(user.getTelegramID()) == 1) {
            return;
        }
        if (user.getPageInline() <= 1) {
            user.setPageInline(orderService.getPagesCount(user.getTelegramID()));
        } else {
            user.setPageInline(user.getPageInline() - 1);
        }
    }

    @Override
    public void next(User user) {
        if(orderService.getPagesCount(user.getTelegramID()) == 1) {
            return;
        }
        if(user.getPageInline() >= orderService.getPagesCount(user.getTelegramID())){
            user.setPageInline(1);
        }
        else {
            user.setPageInline(user.getPageInline() + 1);
        }
    }

    @Override
    public void other(User user, Update update, Messages messages) {
        Product item = productService.getRamById(orderService.getRamById(Long.valueOf(update.getCallbackQuery().getData().substring(0, update.getCallbackQuery().getData().indexOf(":")).substring(update.getCallbackQuery().getData().indexOf("-") + 1))).getProductId());

        String text =
                "Заказ #" + update.getCallbackQuery().getData().substring(0, update.getCallbackQuery().getData().indexOf(":")).substring(update.getCallbackQuery().getData().indexOf("-") + 1) +
                        ":\n\nНазвание: " + item.getName() +
                        "\n\nХарактериски:\n\n" + item.getDescription() +
                        "\n\nЦена: " + item.getPrice() + " грн. ";

        messages.sendInlineMenuWithPhoto(update,item,text,user, "basket_page");
    }

    @Override
    public void editButton(User user, EditMessageReplyMarkup new_message) {
        user.getInlineMenu().setButtonMenu(null, new_message, user.getPageInline(), null, user.getTelegramID(), null, "basket");
    }

    @Override
    public void sendMessage(User user, SendMessage sendMessage) {
        user.getInlineMenu().setButtonMenu(sendMessage, null, user.getPageInline(), null, user.getTelegramID(), null, "basket");
    }

    @Override
    public void sendPhoto(User user, SendPhoto sendPhoto) {
        user.getInlineMenu().setButtonMenuInBasket(sendPhoto);
    }
}
