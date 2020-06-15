package kpfu.itis.g804.bots_project.commands.discord;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dis")
public abstract class Command {

    public abstract void execute(MessageReceivedEvent event);
    public abstract Header header();
    public abstract String description();

    public enum Header {
        help,
        play,
        stop,
        rating,
        top,
        guess,
        restart
    }

}
