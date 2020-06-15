package kpfu.itis.g804.bots_project.commands.telegram;

import kpfu.itis.g804.bots_project.controller.receivers.TelegramReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Component
@Profile("tel")
public abstract class Command {

    @Autowired
    TelegramReceiver telegramReceiver;

    public abstract void execute(Update update);
    public abstract Header header();
    public abstract String description();

    protected void sendMessage(Long chatId, String messageText){
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        try {
            telegramReceiver.sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    protected void sendImageMessage(Long chatId, String urlString, String photoName) {
        SendPhoto sendPhoto = new SendPhoto();
        try {
            sendPhoto.setNewPhoto(photoName, new URL(urlString).openStream());
            sendPhoto.setChatId(chatId);
            telegramReceiver.sendPhoto(sendPhoto);
        } catch (IOException | TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public enum Header {
        start,
        help,
        play,
        stop,
        rating,
        top,
        guess,
        restart
    }



}
