package org.example;

import org.apache.logging.log4j.LogManager;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Engine{
  private static long minuteCounter = 0;


  public static void main(String[] args) throws TelegramApiException{
    String ip = "google.com";
    new BotInitializer(new TelegramBot()).init();
    ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
    service.scheduleAtFixedRate(() -> {
      try{
        boolean request = Pinger.sendPingRequest(ip);
        addMinute(request);
      }
      catch(IOException e){
        String message = "Error while sending ping request: " + e.getMessage();
        LogManager.getLogger().error(message);
      }
    }, 0, 60, TimeUnit.SECONDS);

  }
  public static synchronized void addMinute(boolean on){
    if(on){
      minuteCounter = Math.max(minuteCounter, 0);
      minuteCounter++;
    }
    else {
      minuteCounter = Math.min(minuteCounter, 0);
      minuteCounter--;
    }

  }
  public static synchronized long getMinuteCounter(){
    return minuteCounter;
  }
}