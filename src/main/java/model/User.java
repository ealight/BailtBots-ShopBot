package model;


import menu.inlinemenu.InlineMenu;
import menu.inlinemenu.button.Button;
import menu.staticmenu.commands.Commands;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "telegram_id")
    private Long telegramID;

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Transient
    private String lastSection;

    @Transient
    private String currentSection;

    @Transient
    private String takenProduct;

    @Transient
    private Integer pageInline;

    @Transient
    private boolean isFind;

    @Transient
    private boolean isTextFind;

    @Transient
    private String textFind;

    @Transient
    private CallbackQuery inlineButtonForFind;

    @Transient
    private InlineMenu inlineMenu;

    @Transient
    private Commands commands;

    @Transient
    private Button button;

    @Transient
    private String message;

    @Transient
    private String category;

    @Transient
    private String tempSection;

    public Long getTelegramID() {
        return telegramID;
    }

    public void setTelegramID(Long telegramID) {
        this.telegramID = telegramID;
    }

    public void setLastSection(String lastSection) {
        this.lastSection = lastSection;
    }

    public String getLastSection() {
        return lastSection;
    }

    public void setCurrentSection(String currentSection) { this.currentSection = currentSection; }

    public String getCurrentSection() {
        return currentSection;
    }

    public Long getUserID() {
        return userID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getPageInline() {
        return pageInline;
    }

    public String getMessageText() {
        return message;
    }

    public void setMessageText(String message) {
        this.message = message;
    }

    public boolean isFind() {
        return isFind;
    }

    public boolean isTextFind() {
        return isTextFind;
    }

    public void setTextFind(boolean textFind) {
        isTextFind = textFind;
    }

    public CallbackQuery getInlineButtonForFind() {
        return inlineButtonForFind;
    }

    public Commands getCommands() {
        return commands;
    }

    public void setCommands(Commands commands) {
        this.commands = commands;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public void setInlineButtonForFind(CallbackQuery inlineButtonForFind) {
        this.inlineButtonForFind = inlineButtonForFind;
    }

    public String getTextFind() {
        return textFind;
    }

    public void setTextFind(String textFind) {
        this.textFind = textFind;
    }

    public void setFind(boolean find) {
        isFind = find;
    }

    public void setPageInline(Integer pageInline) {
        this.pageInline = pageInline;
    }

    public String getTempSection() {
        return tempSection;
    }

    public String getTakenProduct() {
        return takenProduct;
    }

    public void setTakenProduct(String takenProduct) {
        this.takenProduct = takenProduct;
    }

    public void setTempSection(String tempSection) {
        this.tempSection = tempSection;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public InlineMenu getInlineMenu() {
        return inlineMenu;
    }

    public void setInlineMenu(InlineMenu inlineMenu) {
        this.inlineMenu = inlineMenu;
    }
}
