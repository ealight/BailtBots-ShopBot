package menu.inlinemenu.button;

import menu.Messages;
import menu.inlinemenu.section.Section;
import menu.inlinemenu.section.impl.BasketPageSection;
import menu.inlinemenu.section.impl.ComponentPageSection;
import model.Order;
import model.Product;
import model.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.OrderService;
import service.ProductService;

import java.util.HashMap;
import java.util.Map;

public class Button {
    private Messages messages = new Messages();
    private OrderService orderService = new OrderService();
    private ProductService productService = new ProductService();
    private Update update;

    Map<String, Runnable> map = new HashMap<>();
    Map<String, Section> sectionFactory = new HashMap<>();

    public Button(User user){
        sectionFactory.put("component_page", new ComponentPageSection());
        sectionFactory.put("basket_page", new BasketPageSection());

        map.put("LetFind", () -> letFind(user));
        map.put("CancelFind", () -> cancelFind(user));
        map.put("SetTextFind", () -> setTextFind(user, update));
        map.put("AcceptOrder", () -> acceptOrder(user));
        map.put("ToBasket", () -> toBasket(user));
        map.put("Previous", () -> previous(user, update));
        map.put("Next", () -> next(user, update));
        map.put("Other", () -> other(user, update));
    }

    private void letFind(User user) {
        if (!user.getTextFind().isEmpty()) {
            messages.sendMsgForInlineMenu("Результат поиска:", user);

            user.setTextFind(false);
        }
    }

    private void cancelFind(User user) {
        user.setFind(false);
        user.setTextFind(false);
        messages.sendMsgForStaticMenu("Поиск успешно отменен.", "Next", user);
    }

    private void setTextFind(User user, Update update) {
        user.setTextFind(true);
        user.setInlineButtonForFind(update.getCallbackQuery());
        messages.sendMsgForStaticMenu("Введите в чат то что хотите найти. \u2B07", "Next", user);
    }

    private void acceptOrder(User user){
        if(user.getTakenProduct().equals("none")){
            return;
        }
        if (user.getPhoneNumber().isEmpty()) {
            user.setCurrentSection("start_page_getNumber");
            messages.sendMsgForStaticMenu("Вы должны подтвердить свой номер телефона для того что-бы мы могли связатся с вами.", "Next", user);
            return;
        }
        Order order = orderService.getRamById(Long.valueOf(user.getTakenProduct()));
        order.setExecute(true);
        orderService.update(order);
        messages.sendMsgForStaticMenu("Заказ #" + order.getId() + " успешно подтверджен на протяжении 5-10 минут с вами свяжется наш оператор.", "Next", user);
        user.setTakenProduct("none");
    }

    private void toBasket(User user) {
        if(user.getTakenProduct().equals("none")){
            return;
        }
        Product item = productService.getRamById(Long.valueOf(user.getTakenProduct()));

        Order order = new Order();
        order.setTelegramId(user.getTelegramID());
        order.setProductId(item.getId());
        order.setExecute(false);
        orderService.save(order);
        user.setTakenProduct("none");
        messages.sendMsgForStaticMenu("Товар \"" + item.getName() + "\" успешно добавлен в корзину.\n\nНомер заказа: #" + order.getId() + "\n\n\u26A0 Для подтверждения заказа перейдите в корзину.", "Next", user);
        if(user.isFind()) {
            user.setFind(false);
            user.setTextFind(false);
        }
    }

    private void previous(User user, Update update){
        String currentSection = user.getCurrentSection();
        Section section = sectionFactory.get(currentSection);
        section.previous(user);
        messages.editMessageForInlineMenu(update, user);
    }

    private void next(User user, Update update) {
        String currentSection = user.getCurrentSection();
        Section section = sectionFactory.get(currentSection);
        section.next(user);
        messages.editMessageForInlineMenu(update, user);
    }

    private void other(User user, Update update){
        String currentSection = user.getCurrentSection();
        Section section = sectionFactory.get(currentSection);
        section.other(user, update, messages);
        user.setTakenProduct(update.getCallbackQuery().getData().substring(0, update.getCallbackQuery().getData().indexOf(":")).substring(update.getCallbackQuery().getData().indexOf("-") + 1));
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public void performCommand(String key) {
        map.get(key).run();
    }
}
