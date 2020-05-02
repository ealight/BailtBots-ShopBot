package menu.inlinemenu.section;

import menu.Messages;
import model.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Section {
    void previous(User user);
    void next(User user);
    void other(User user, Update update, Messages messages);
    void editButton(User user, EditMessageReplyMarkup new_message);
    void sendMessage(User user, SendMessage sendMessage);
    void sendPhoto(User user, SendPhoto sendPhoto);
}
