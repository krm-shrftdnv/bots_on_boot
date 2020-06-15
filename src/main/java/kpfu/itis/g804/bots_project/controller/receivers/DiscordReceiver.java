package kpfu.itis.g804.bots_project.controller.receivers;

import kpfu.itis.g804.bots_project.controller.resolvers.DiscordResolver;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
@Profile("dis")
public class DiscordReceiver extends ListenerAdapter {

    private final DiscordResolver discordResolver;

    public DiscordReceiver(@Lazy DiscordResolver discordResolver) {
        this.discordResolver = discordResolver;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (event.getMessage().getMentionedMembers().size() > 0)
            if (event.getMessage().getMentionedMembers().get(0).getIdLong() == event.getJDA().getSelfUser().getIdLong())
                discordResolver.executeCommand(event);
    }

}