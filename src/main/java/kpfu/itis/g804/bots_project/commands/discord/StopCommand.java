package kpfu.itis.g804.bots_project.commands.discord;

import kpfu.itis.g804.bots_project.model.Session;
import kpfu.itis.g804.bots_project.service.SessionService;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Profile("dis")
public class StopCommand extends Command {

    @Autowired
    SessionService sessionService;

    @Override
    public void execute(MessageReceivedEvent event) {

        MessageChannel channel = event.getChannel();

        if(sessionService.getSession(channel.getIdLong()).isPresent()){
            Session session = sessionService.getSession(channel.getIdLong()).get();
            if(session.isOpen()) {
                sessionService.stopSession(session.getId());
                channel.sendMessage("Игра приостановлена, результат сохранён. Для продолжания введите команду \"play\"").queueAfter(2, TimeUnit.SECONDS);
            }
        } else channel.sendMessage("Сначала play, потом stop. It's easy, bro!").queueAfter(2, TimeUnit.SECONDS);

    }

    @Override
    public Header header() {
        return Header.stop;
    }

    @Override
    public String description() {
        return "Останавливает текущую игру, сохраняя достигнутый результат";
    }
}
