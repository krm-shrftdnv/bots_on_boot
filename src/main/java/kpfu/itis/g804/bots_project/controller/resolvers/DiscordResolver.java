package kpfu.itis.g804.bots_project.controller.resolvers;

import kpfu.itis.g804.bots_project.commands.discord.Command;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Profile("dis")
public class DiscordResolver {

    private final ApplicationContext context;
    private final Map<String, Command> commandMap = new HashMap<>();
    private final JDA jda;

    public DiscordResolver(ApplicationContext context, JDA jda) {
        this.context = context;
        this.jda = jda;
        initializeCommands();
    }

    public Map<String, Command> getCommandMap() {
        return commandMap;
    }

    private void initializeCommands() {
        Collection<Command> commands = context.getBeansOfType(Command.class).values();
        for (Command command : commands) {
            addCommand(command);
        }
    }

    private void addCommand(Command command) {
        commandMap.put(command.header().name(), command);
    }

    public void executeCommand(MessageReceivedEvent event) {

        Message message = event.getMessage();

        if (message.getMentionedMembers().size() > 0) {
            if (message.getMentionedMembers().get(0).getIdLong() == event.getJDA().getSelfUser().getIdLong()) {
                String commandText = message.getContentRaw().toLowerCase().split(" ")[1];
                Command command = commandMap.get(commandText);
                if (command != null)
                    command.execute(event);
            }
        }
    }

}
