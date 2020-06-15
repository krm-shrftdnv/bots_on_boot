package kpfu.itis.g804.bots_project.commands.telegram;

import kpfu.itis.g804.bots_project.controller.resolvers.TelegramResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
@Profile("tel")
public class HelpCommand extends Command {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        StringBuilder builder = new StringBuilder();
        builder.append("Что я умею?\n");
        Header[] headers = Header.values();
        for (Header header : headers) {
            if(!header.equals(Header.start))
            builder.append("/").append(header.name()).append(" - ").append(applicationContext.getBean(TelegramResolver.class).getCommandMap().get(header.name()).description()).append("\n");
        }
        builder.append("Спасибо, что спросили.");
        SendMessage sendMessage = new SendMessage(message.getChatId(), builder.toString());
//        SendMessage sendMessage = new SendMessage(message.getChatId(), "help command");
        try {
            telegramReceiver.sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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
