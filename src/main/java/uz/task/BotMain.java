package uz.task;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import uz.task.util.CurrencyUtil;

import java.util.ArrayList;

public class BotMain extends TelegramLongPollingBot {

    public static String username = "Curriencies Bot";
    public static String token = "1135321006:AAF9-e64TYiDvQktcxXbl3uh-dB-RaTdBE8";
    public static int step = 0;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new BotMain());

        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }


    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();

        String text = message.getText().toUpperCase();
        String sendText = null;

        if (text.equals("/START")) {
            sendText = "Welcome to Jaxongir's Currencies Bot!";
            step = 1;
        } else if (text.equals("Sum to \uD83D\uDCB8")) {
            step = 2;
        } else {
            sendText = CurrencyUtil.getCurrency(text);
            if (sendText == null) {
                sendText = "Currency is not founded!!!";
            }
        }

        sendMessage.setText(sendText);
        sendMessage.setChatId(message.getChatId());
        setKeyboard(sendMessage);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void setKeyboard(SendMessage sendMessage) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        keyboardMarkup.setResizeKeyboard(true);
        ArrayList<KeyboardRow> rows = new ArrayList<KeyboardRow>();
        KeyboardRow row1, row2, row3, row4;
        KeyboardButton button1, button2, button3, button4, button5, button6, button7;
        switch (step) {
            case 0:
                break;
            case 1:
                row4 = new KeyboardRow();
                button7 = new KeyboardButton("Sum to \uD83D\uDCB8");
                row4.add(button7);
                rows.add(row4);
                step = 2;
                keyboardMarkup.setKeyboard(rows);
                break;
            case 2:
                row1 = new KeyboardRow();
                button1 = new KeyboardButton("RUB");
                button2 = new KeyboardButton("EUR");
                row1.add(button1);
                row1.add(button2);

                row2 = new KeyboardRow();
                button3 = new KeyboardButton("USD");
                button4 = new KeyboardButton("GBP");
                row2.add(button3);
                row2.add(button4);

                row3 = new KeyboardRow();
                button5 = new KeyboardButton("JPY");
                button6 = new KeyboardButton("CHF");
                row3.add(button5);
                row3.add(button6);

                rows.add(row1);
                rows.add(row2);
                rows.add(row3);

                step = 1;
                keyboardMarkup.setKeyboard(rows);
                break;
        }

        sendMessage.setReplyMarkup(keyboardMarkup);
    }

    public String getBotUsername() {
        return username;
    }

    public String getBotToken() {
        return token;
    }
}

//        if (text.equals("/start")) {
//            send = "Welcome to Jaxongir's Currencies Bot!";
//        } else if (text.equals("RUB") || text.equals("EUR")
//                || text.equals("USD") || text.equals("GBP")
//                || text.equals("JPY") || text.equals("CHF")) {
//            String txt = text.toUpperCase();
//            send = CurrencyUtil.getCurrency(txt);
//        } else if (text.equals("Change Curreny")) {
//            step = 1;
//        } else {
//            send = "I don't know about this word!!!";
//        }