package chatuser;

import menu.Messages;
import menu.inlinemenu.button.Button;
import menu.staticmenu.commands.Commands;
import org.telegram.telegrambots.meta.api.objects.Message;
import service.OrderService;
import service.UserService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ChatUser {
    private Messages messages = new Messages();
    private UserService userService = new UserService();
    private OrderService orderService = new OrderService();

    private Map<Long, model.User> users = new HashMap<>();
    private HashSet<Long> createdUsers = new HashSet<>();


    public void createUser(Long telegramID, Message message) {
        if (userService.getByTelegramId(telegramID) == null) {
            model.User user = new model.User();

            addUser(telegramID, user);

            user.setTelegramID(telegramID);
            user.setFirstName(message.getChat().getFirstName());
            if(message.getChat().getLastName() == null) {
                user.setLastName("");
            }
            else {
                user.setLastName(message.getChat().getLastName());
            }
            user.setPhoneNumber("");
            user.setCurrentSection("start_page_getNumber");

            userService.save(user);

            messages.sendMsgForStaticMenu("Поздравляем, " + message.getChat().getFirstName() + " " + message.getChat().getLastName() + ", вы успешно прошли этап регистрации!" +
                    "\nВаш ID: " + user.getUserID() + "\nВаш Telegram-ID: " + user.getTelegramID(), "Next", user);

        } else {
            model.User user = userService.getByTelegramId(telegramID);

            addUser(telegramID, user);

            if (user.getPhoneNumber().isEmpty()) {
                user.setCurrentSection("start_page_getNumber");
            } else {
                user.setCurrentSection("start_page");
            }
            messages.sendMsgForStaticMenu("Здравствуйте " + user.getFirstName() + " " + user.getLastName() + " мы не нашли вас в сессии... переходим на стартовую страницу." +
                    "\n\n \uD83D\uDCBC В вашей корзине находится " + orderService.getAllItems(user.getTelegramID()).size() + " заказ(ов)!", "Next", user);
        }

        Commands commands = new Commands(getUser(telegramID));
        Button button = new Button(getUser(telegramID));

        getUser(telegramID).setCommands(commands);
        getUser(telegramID).setButton(button);

        getUser(telegramID).setPageInline(1);
        getUser(telegramID).setCategory("none");
        getUser(telegramID).setTakenProduct("none");
        getUser(telegramID).setMessageText("none");
        addUserToCreated(telegramID);
    }

    public void checkPhoneNumber(Long telegramID, Message message){
        if (getUser(telegramID).getPhoneNumber().isEmpty() || getUser(telegramID).getPhoneNumber() == null) {
            getUser(telegramID).setPhoneNumber(message.getContact().getPhoneNumber());
            getUser(telegramID).setCurrentSection("start_page");
            userService.update(getUser(telegramID));
            messages.sendMsgForStaticMenu("Вы успешно подтвердили номер телефона: " + message.getContact().getPhoneNumber() + "!", "Next", getUser(telegramID));
        } else {
            messages.sendMsgForStaticMenu("Ваш номер телефона уже подтвержден!", "Next", getUser(telegramID));
        }
    }

    public void addUser(Long chatId, model.User user){
        users.put(chatId, user);
    }

    public model.User getUser(Long chatId){
        return users.get(chatId);
    }

    public void addUserToCreated(Long telegramID){
        createdUsers.add(telegramID);
    }

    public boolean isUserCreated(Long telegramID){
        for (Long i : createdUsers) {
            if(i.equals(telegramID)){
                return true;
            }
        }
        return false;
    }
}
