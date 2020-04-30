package menu.inlinemenu.section.impl;

import menu.Messages;
import menu.inlinemenu.section.Section;
import model.Product;
import model.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.ProductService;

public class ComponentPageSection implements Section {
    private ProductService productService = new ProductService();

    @Override
    public void previous(User user) {
        if(!user.isFind()) {
            if (productService.getPagesCount(user.getCategory()) == 1) {
                return;
            }
            if (user.getPageInline() <= 1) {
                user.setPageInline(productService.getPagesCount(user.getCategory()));
            } else {
                user.setPageInline(user.getPageInline() - 1);
            }
        }
        else {
            if (productService.getPagesCountForName(user.getTextFind()) == 1) {
                return;
            }
            if (user.getPageInline() <= 1) {
                user.setPageInline(productService.getPagesCountForName(user.getTextFind()));
            } else {
                user.setPageInline(user.getPageInline() - 1);
            }
        }
    }

    @Override
    public void next(User user) {
        if(!user.isFind()) {
            if (productService.getPagesCount(user.getCategory()) == 1) {
                return;
            }
            if (user.getPageInline() >= productService.getPagesCount(user.getCategory())) {
                user.setPageInline(1);
            } else {
                user.setPageInline(user.getPageInline() + 1);
            }
        }
        else {
            if (productService.getPagesCountForName(user.getTextFind()) == 1) {
                return;
            }
            if (user.getPageInline() >= productService.getPagesCountForName(user.getTextFind())) {
                user.setPageInline(1);
            } else {
                user.setPageInline(user.getPageInline() + 1);
            }
        }
    }

    @Override
    public void other(User user, Update update, Messages messages) {
        Product item = productService.getRamById(Long.valueOf(update.getCallbackQuery().getData().substring(0, update.getCallbackQuery().getData().indexOf(":")).substring(update.getCallbackQuery().getData().indexOf("-") + 1)));

        String text =
                "\nНазвание: " + item.getName() +
                        "\n\nХарактеристики:\n\n" + item.getDescription() +
                        "\n\nЦена: " + item.getPrice() + " грн. ";

        messages.sendInlineMenuWithPhoto(update,item,text,user, "component_page");
    }

    @Override
    public void editButton(User user, EditMessageReplyMarkup new_message) {
        if(user.isFind() == false) {
            user.getInlineMenu().setButtonMenu(null, new_message, user.getPageInline(), user.getCategory(), null, null, "market");
        }
        else {
            user.getInlineMenu().setButtonMenu(null, new_message, user.getPageInline(), null, null, user.getTextFind(), "find");
        }
    }

    @Override
    public void sendMessage(User user, SendMessage sendMessage) {
        if(user.isFind()){
            user.getInlineMenu().setButtonMenuFind(sendMessage,null, "Введите сюда то что вас интересует...");
            if(user.isTextFind()){
                user.getInlineMenu().setButtonMenu(sendMessage, null, user.getPageInline(), null, null, user.getTextFind(), "find");
            }
        }
        else {
            user.getInlineMenu().setButtonMenu(sendMessage, null, user.getPageInline(), user.getCategory(), null, null, "market");
        }
    }

    @Override
    public void sendPhoto(User user, SendPhoto sendPhoto) {
        user.getInlineMenu().setButtonMenuInMarket(sendPhoto);
    }
}
