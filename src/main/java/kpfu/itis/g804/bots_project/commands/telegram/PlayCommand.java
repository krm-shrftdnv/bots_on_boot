package kpfu.itis.g804.bots_project.commands.telegram;

import kpfu.itis.g804.bots_project.model.Session;
import kpfu.itis.g804.bots_project.service.SessionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static kpfu.itis.g804.bots_project.service.Helper.*;

@Component
@Profile("tel")
public class PlayCommand extends Command {

    @Autowired
    SessionService sessionService;

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String authorName = message.getFrom().getUserName();
        Long gameStarterId = new Long(message.getFrom().getId());
        if (sessionService.getSession(chatId).isPresent()) {
            Session session = sessionService.getSession(chatId).get();
            System.out.println(session.toString());

            if (session.isOpen()) {
                if (session.isGuessed()){
                    if(session.getLastId() + 1 <= maxId) {
                        String imgUrl = sendRequestForImages(session.getLastId() + 1);
                        String answer = parseImgUrl(imgUrl);
                        sessionService.updateSession(session.getId(), session.getLastId() + 1, imgUrl, answer, false);
                        sendImageMessage(chatId,imgUrl, answer);
                        sendMessage(chatId, authorName  + " начал игру, угадайте слово.");
                    } else {
                        sendMessage(chatId, "Поздравляю! Вы прошли всю игру!");
                        String imgUrl = sendRequestForImages();
                        String answer = parseImgUrl(imgUrl);
                        sessionService.updateSession(session.getId(), -1, imgUrl, answer, false);
                    }
                } else {
                    sendImageMessage(chatId,session.getImgUrl(), session.getAnswer());
                    sendMessage(chatId, authorName  + " начал игру, угадайте слово.");
                }
//                channel.sendMessage("Игра уже начата пользователем " + getMemberById(session.getGameStarterId(), members)).queueAfter(2, TimeUnit.SECONDS);
            } else {
                sessionService.startSession(session.getId(), gameStarterId, session.getImgUrl(), session.getAnswer());
                sendImageMessage(chatId,session.getImgUrl(), session.getAnswer());
                sendMessage(chatId, authorName  + " начал игру, угадайте слово.");
            }

        } else {
            String imgUrl = sendRequestForImages();
            String answer = parseImgUrl(imgUrl);
            sessionService.startSession(chatId, gameStarterId, imgUrl, answer);
            sendImageMessage(chatId,imgUrl, answer);
            sendMessage(chatId, authorName  + " начал игру, угадайте слово.");
        }

    }

    @Override
    public Header header() {
        return Header.play;
    }

    @Override
    public String description() {
        return "Начинает игру в данном чате";
    }
}
