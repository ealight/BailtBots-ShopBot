package menu.inlinemenu;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class KeyboardRowFactory {
    private List<List<InlineKeyboardButton>> createdRow = new ArrayList<>();

    public void createRow(int size) {
        for(int i = 0; i < size; i ++) {
            createdRow.add(new ArrayList<>());
        }
    }

    public List<InlineKeyboardButton> getRow(int id){
        return createdRow.get(id);
    }

    public void clearRow(){
        createdRow.clear();
    }
}
