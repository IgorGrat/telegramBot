package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TelegramBot extends TelegramLongPollingBot implements OnOffLightListener{
  private Set<Long> invokeUsersList = Collections.synchronizedSet(new HashSet<>());
  /**
   * Метод прийому повідомлень.
   * @param update Містить повідомлення від користувача.
   */
  @Override
  public void onUpdateReceived(Update update){
    Message message1 = update.getMessage();
    String message = message1.getText();
    long chatId = message1.getChatId();
    invokeUsersList.add(chatId);

    switch(message){
      case "/питання є": {
          String answer = "Питань нема ! Мене міняти намагаєтесь дарма";
              sendMessage(chatId, answer);
          break;
      }
      case "/about": {
        String answer = "«Це наш Магістр Чорної Міді. Він єдиний, хто знає, чи працює кавомашина, " +
            "чи ми сьогодні знову п'ємо холодну воду з-під крана і вдаємо, що це " +
            "\"айс-лате\". Якщо він мовчить — значить, в офісі зараз атмосфера середньовічного " +
            "замку: холодно, темно і хочеться спалити когось на вогнищі за поганий Wi-Fi».";
            sendMessage(chatId, answer);
        break;
      }
      case "/start":
        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
        break;
      case "/хелп" :
      case "/help":
        String answer = "Маніфест:\n" +
            "/about\n" +
            "/strum\n" +
            "/струм\n" +
            "/start\n";

        sendMessage(chatId, answer);
        break;
        case "/струм":
        case "/strum" :
          long minuteCounter = Engine.getMinuteCounter();
          LocalTime lastedTime = LocalTime.ofSecondOfDay(Math.abs(minuteCounter) * 60);
          LocalDateTime date = LocalDateTime.now().minusMinutes(Math.abs(minuteCounter));
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
          String stringDate =  formatter.format(date);

          String result = minuteCounter < 0?
          " ☝ Електропостачання припинено о " + stringDate + "\n\n ❗ Світла немає вже " + lastedTime + " хвилин(у)" :
          "✌ Електропостачання відновлено о " + stringDate + "\n\n ✅ Світло є вже " + lastedTime + " хвилин(у)";
          sendMessage(chatId, result);
          break;
      default:


    }
  }
  private void startCommandReceived(Long chatId, String name) {
    String answer = "Вітаю! Ти щойно активував Цифрового Магістра Чорної Міді⚡";
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
    return "8218259173:AAEQAF8ubPtM8Ukj0QlqbvXnGXvtsBkipTs";
//    return "8228623088:AAGUh2gOKsHVMzFelWkLLqawW4ctbIix99I";
  }

  @Override
  public void onOffLightInvoke(OnOffLightEvent event){
    boolean on = event.isOn();
    String status = on? "✅ Струм є, настрій теж!" : "❗Струм втік, залишилась лише надія і паверстанція.";
    for(Long chatId : invokeUsersList){
      sendMessage(chatId, status);
    }
  }
}
