package kpfu.itis.g804.bots_project.commands.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Update;

@Component
@Profile("tel")
public class StartCommand extends Command {

    @Autowired
    private ApplicationContext context;

    @Override
    public void execute(Update update) {
        sendMessage(update.getMessage().getChatId(), "Привет! Я бот для игры \"4 картинки = 1 слово\".");
        context.getBean(HelpCommand.class).execute(update);
    }

    @Override
    public Header header() {
        return Header.start;
    }

    @Override
    public String description() {
        return "Используется в первый раз, для запуска бота";
    }
}
