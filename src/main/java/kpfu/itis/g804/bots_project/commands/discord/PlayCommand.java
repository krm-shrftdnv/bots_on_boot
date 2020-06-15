package kpfu.itis.g804.bots_project.commands.discord;

import kpfu.itis.g804.bots_project.model.Session;
import kpfu.itis.g804.bots_project.service.SessionService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static kpfu.itis.g804.bots_project.service.Helper.*;

@Component
@Profile("dis")
public class PlayCommand extends Command {

    @Autowired
    SessionService sessionService;

    @Override
    public void execute(MessageReceivedEvent event) {
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        List<Member> members = event.getGuild().getMembers();

        Long gameStarterId = message.getAuthor().getIdLong();
        String authorName = message.getAuthor().getName();
        if (sessionService.getSession(channel.getIdLong()).isPresent()) {
            Session session = sessionService.getSession(channel.getIdLong()).get();
            System.out.println(session.toString());

            if (session.isOpen()) {
                if (session.isGuessed()){
                    if(session.getLastId() + 1 <= maxId) {
                        String imgUrl = sendRequestForImages(session.getLastId() + 1);
                        String answer = parseImgUrl(imgUrl);
                        sessionService.updateSession(session.getId(), session.getLastId() + 1, imgUrl, answer, false);
                        MessageEmbed img = new EmbedBuilder().setImage(imgUrl).setTitle(authorName + " начал игру, угадайте слово.").setColor(Color.MAGENTA).build();
                        channel.sendMessage(img).queueAfter(2, TimeUnit.SECONDS);
                    } else {
                        channel.sendMessage("Поздравляю! Вы прошли всю игру!").queueAfter(2, TimeUnit.SECONDS);
                        String imgUrl = sendRequestForImages();
                        String answer = parseImgUrl(imgUrl);
                        sessionService.updateSession(session.getId(), -1, imgUrl, answer, false);
                    }
                } else {
                    MessageEmbed img = new EmbedBuilder().setImage(session.getImgUrl()).setTitle(authorName + " начал игру, угадайте слово.").setColor(Color.MAGENTA).build();
                    channel.sendMessage(img).queueAfter(2, TimeUnit.SECONDS);
                }
//                channel.sendMessage("Игра уже начата пользователем " + getMemberById(session.getGameStarterId(), members)).queueAfter(2, TimeUnit.SECONDS);
            } else {
                sessionService.startSession(session.getId(), gameStarterId, session.getImgUrl(), session.getAnswer());
                MessageEmbed img = new EmbedBuilder().setImage(session.getImgUrl()).setTitle(authorName + " начал игру, угадайте слово.").setColor(Color.MAGENTA).build();
                channel.sendMessage(img).queueAfter(2, TimeUnit.SECONDS);
            }

        } else {
            String imgUrl = sendRequestForImages();
            String answer = parseImgUrl(imgUrl);
            sessionService.startSession(channel.getIdLong(), gameStarterId, imgUrl, answer);
            MessageEmbed img = new EmbedBuilder().setImage(imgUrl).setTitle(authorName + " начал игру, угадайте слово.").setColor(Color.MAGENTA).build();
            channel.sendMessage(img).queueAfter(2, TimeUnit.SECONDS);
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
