package org.example;

import org.apache.logging.log4j.LogManager;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Engine{
  private static long minuteCounter = 0;
  private static final List<OnOffLightListener> listeners = new ArrayList<>();

  public static void main(String[] args) throws TelegramApiException{
    String ip = args[0];
    TelegramBot telegramBot = new TelegramBot();
    listeners.add(telegramBot);
    new BotInitializer(telegramBot).init();
    ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
    service.scheduleAtFixedRate(() -> {
      try{
        boolean turnOn = Pinger.sendPingRequest(ip);
        long minuteCounter = getMinuteCounter();
        boolean isChangedStatusLight = turnOn && minuteCounter < 0 || ! turnOn && minuteCounter > 0;
        minuteCounter = addMinute(turnOn);
        if(isChangedStatusLight){
          invokeAllListeners(new OnOffLightEvent(service, turnOn, minuteCounter));
        }
      }
      catch(IOException e){
        String message = "Error while sending ping request: " + e.getMessage();
        LogManager.getLogger().error(message);
      }
    }, 0, 60, TimeUnit.SECONDS);

  }
  public static synchronized long addMinute(boolean on){
    if(on){
      minuteCounter = Math.max(minuteCounter, 0);
      minuteCounter++;
    }
    else {
      minuteCounter = Math.min(minuteCounter, 0);
      minuteCounter--;
    }
    return minuteCounter;
  }
  public static synchronized long getMinuteCounter(){
    return minuteCounter;
  }
  public static void invokeAllListeners(OnOffLightEvent event){
    for(OnOffLightListener listener : listeners){
      listener.onOffLightInvoke(event);
    }
  }
}