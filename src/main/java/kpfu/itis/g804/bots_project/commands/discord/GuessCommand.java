package kpfu.itis.g804.bots_project.commands.discord;

import kpfu.itis.g804.bots_project.model.Session;
import kpfu.itis.g804.bots_project.service.SessionService;
import kpfu.itis.g804.bots_project.service.UsersService;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Profile("dis")
public class GuessCommand extends Command {

    @Autowired
    SessionService sessionService;
    @Autowired
    UsersService usersService;

    @Override
    public void execute(MessageReceivedEvent event) {
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        List<Member> members = event.getGuild().getMembers();

        String word = message.getContentRaw().split(" ")[2].toLowerCase();
        if(sessionService.getSession(channel.getIdLong()).isPresent()) {
            Session session = sessionService.getSession(channel.getIdLong()).get();
            if (session.isOpen()&& !session.isGuessed()) {
                if(word.equals(session.getAnswer())) {
                    usersService.addUserPoint(message.getAuthor().getIdLong(), "discord");
                    sessionService.updateSession(session.getId(),session.getLastId(), session.getImgUrl(), session.getAnswer(), true);
                    channel.sendMessage(message.getAuthor().getAsMention() + ", ты угадал! Чтобы продолжить играть напишите команду \"play\". ").queueAfter(2, TimeUnit.SECONDS);
                } else {
                    channel.sendMessage(message.getAuthor().getAsMention() + ", ты не угадал. Пробуйте ещё.").queueAfter(2, TimeUnit.SECONDS);
                }
            } else {
                channel.sendMessage("Сначала продолжите игру командой \"play\"").queueAfter(2, TimeUnit.SECONDS);
            }
        } else {
            channel.sendMessage("Сначала начните игру командой \"play\"").queueAfter(2, TimeUnit.SECONDS);
        }

    }

    @Override
    public Header header() {
        return Header.guess;
    }

    @Override
    public String description() {
        return "Попробуйте отгадать загаданное в картинках слово. Например \"@4Pics1WordBot guess красный\"";
    }
}
