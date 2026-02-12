package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot{

  /**
   * Метод прийому повідомлень.
   * @param update Містить повідомлення від користувача.
   */
  @Override
  public void onUpdateReceived(Update update) {
    String message = update.getMessage().getText();
    sendMsg(update.getMessage().getChatId().toString(), message);
  }

  /**
   * Метод для налаштування повідомлення та його надсилання.
   * @param chatId id чату
   * @param s Рядок, який необхідно відправити як повідомлення.
   */
  public synchronized void sendMsg(String chatId, String s) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.enableMarkdown(true);
    sendMessage.setChatId(chatId);
    sendMessage.setText(s);
    try {
      sendMessage(sendMessage);
    }
    catch (TelegramApiException e) {
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
