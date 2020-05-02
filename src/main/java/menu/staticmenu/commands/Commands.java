package menu.staticmenu.commands;

import menu.Messages;
import model.User;
import org.telegram.telegrambots.meta.api.objects.Message;
import service.OrderService;

import java.util.HashMap;
import java.util.Map;

public class Commands {
    private Messages messages = new Messages();
    private OrderService orderService = new OrderService();

    Map<String, Runnable> map = new HashMap<>();

    public Commands(User user){
        map.put("/start", () -> start(user));
        map.put("\uD83D\uDCBC Корзина", () -> basket(user));
        map.put("Назад", () -> previous(user));
        map.put("\uD83D\uDCBB Комп'ютерные комплектующие", () -> components(user));
        map.put("Оперативная пам'ять", () -> ram(user));
        map.put("SSD диски", () -> ssd(user));
        map.put("Поиск", () -> find(user));
    }

    private void start(User user){
        user.setLastSection(user.getCurrentSection());
        if (user.getPhoneNumber() == null) {
            user.setCurrentSection("start_page_getNumber");
        } else {
            user.setCurrentSection("start_page");
        }
        messages.sendMsgForStaticMenu("Вы перешли на стартовую страницу!", "Next", user);
    }

    private void basket(User user){
        user.setLastSection(user.getCurrentSection());
        user.setCurrentSection("basket_page");
        messages.sendMsgForStaticMenu("Корзина... \u27A1", "Next", user);
        messages.sendMsgForInlineMenu( "Ваша корзина(" + orderService.getAllItems(user.getTelegramID()).size() + "):", user);
    }

    private void previous(User user){
        messages.sendMsgForStaticMenu("\u2B05 Назад...", "Previous", user);
    }

    private void components(User user) {
        System.out.println("5: " + user.getTelegramID());
        user.setLastSection(user.getCurrentSection());
        user.setCurrentSection("component_page");
        messages.sendMsgForStaticMenu("Комп'ютерные комплектующие... \u27A1", "Next", user);
    }

    private void ram(User user){
        user.setCategory("ram");
        user.setPageInline(1);
        messages.sendMsgForInlineMenu("Раздел 'Оперативная пам'ять':", user);
    }

    private void ssd(User user){
        user.setCategory("ssd");
        user.setPageInline(1);
        messages.sendMsgForInlineMenu("Раздел 'SSD диски':", user);
    }

    private void find(User user){
        user.setFind(true);
        user.setPageInline(1);
        messages.sendMsgForInlineMenu("Поиск:", user);
    }

    public void performCommand(String key) {
        map.get(key).run();
    }
}
