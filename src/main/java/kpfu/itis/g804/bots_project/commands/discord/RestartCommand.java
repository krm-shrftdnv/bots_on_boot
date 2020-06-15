package kpfu.itis.g804.bots_project.commands.discord;

import kpfu.itis.g804.bots_project.model.Session;
import kpfu.itis.g804.bots_project.service.SessionService;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Profile("dis")
public class RestartCommand extends Command {

    @Autowired
    SessionService sessionService;

    @Override
    public void execute(MessageReceivedEvent event) {

        MessageChannel channel = event.getChannel();
        Message message = event.getMessage();
        if (message.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            if (sessionService.getSession(channel.getIdLong()).isPresent()) {
                Session session = sessionService.getSession(channel.getIdLong()).get();
                sessionService.deleteSession(session.getId());
                channel.sendMessage("Результат сброшен. ").queueAfter(2, TimeUnit.SECONDS);
            } else
                channel.sendMessage("Чтобы что-то сбросить, надо сначала что-нибудь набрать").queueAfter(2, TimeUnit.SECONDS);
        }
    }

    @Override
    public Header header() {
        return Header.restart;
    }

    @Override
    public String description() {
        return "Сбрасывает весь достигнутый результат игры";
    }
}
