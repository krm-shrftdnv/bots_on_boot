package kpfu.itis.g804.bots_project.commands.discord;

import kpfu.itis.g804.bots_project.controller.resolvers.DiscordResolver;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.concurrent.TimeUnit;

@Component
@Profile("dis")
public class HelpCommand extends Command {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void execute(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        StringBuilder builder = new StringBuilder();

        Header[] headers = Header.values();
        for (Header header : headers) {
            builder.append(header.name()).append(" - ").append(applicationContext.getBean(DiscordResolver.class).getCommandMap().get(header.name()).description()).append("\n");
        }

        MessageEmbed about = new EmbedBuilder()
                .setTitle("Что я умею?")
                .setColor(Color.MAGENTA)
                .setDescription(builder)
                .addField("Формат команды", "@krm_as_bot имя-команды", true)
                .setFooter("Спасибо, что спросили.")
                .build();
        channel.sendMessage(about).queueAfter(2, TimeUnit.SECONDS);

    }

    @Override
    public Header header() {
        return Header.help;
    }

    @Override
    public String description() {
        return "Выводит все команды";
    }
}
