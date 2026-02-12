package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalTime;

public class TelegramBot extends TelegramLongPollingBot{

  /**
   * Метод прийому повідомлень.
   * @param update Містить повідомлення від користувача.
   */
  @Override
  public void onUpdateReceived(Update update) {
    Message message1 = update.getMessage();
    String message = message1.getText();
    long chatId = message1.getChatId();

    switch(message){
      case "/start":
        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
        break;
      case "/help":
        String answer = "This bot is created for demonstration purposes. It can respond to the /start command with a greeting message. For more information, please contact the developer.";
        sendMessage(chatId, answer);
        break;
        case  "/світло":
          long minuteCounter = Engine.getMinuteCounter();
          String result = minuteCounter < 0?
              "Світло вимкнено " + LocalTime.ofSecondOfDay(Math.abs(minuteCounter) * 60) + " хвилин(у)" :
              "Світло увімкнено " + LocalTime.ofSecondOfDay(minuteCounter * 60) + " хвилин(у)";
          sendMessage(chatId, result);
          break;

      default:


    }
  }
  private void startCommandReceived(Long chatId, String name) {
    String answer = "Hi, " + name + ", nice to meet you!";
    sendMessage(chatId, answer);
  }

  /**
   * Метод для налаштування повідомлення та його надсилання.
   * @param chatId id чату
   * @param s Рядок, який необхідно відправити як повідомлення.
   */
  public synchronized void sendMessage(long chatId, String s) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableMarkdown(true);
    sendMessage.setChatId(chatId);
    sendMessage.setText(s);
    try {
      execute(sendMessage);
    }
    catch (TelegramApiException e){
      e.printStackTrace();
    }
  }

  /**
   * Метод повертає ім'я бота, вказане під час реєстрації.
   * @return ім'я бота
   */
  @Override
  public String getBotUsername() {
    return "m3c_java_bot";
  }

  /**
   * Метод повертає token бота для зв'язку з сервером Telegram
   * @return token для бота
   */
  @Override
  public String getBotToken() {
    return "8228623088:AAGUh2gOKsHVMzFelWkLLqawW4ctbIix99I";
  }
}
