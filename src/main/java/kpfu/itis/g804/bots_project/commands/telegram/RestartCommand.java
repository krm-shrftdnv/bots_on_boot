package kpfu.itis.g804.bots_project.commands.telegram;

import kpfu.itis.g804.bots_project.model.Session;
import kpfu.itis.g804.bots_project.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Update;

@Component
@Profile("tel")
public class RestartCommand extends Command {

    @Autowired
    SessionService sessionService;

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        if (sessionService.getSession(chatId).isPresent()) {
            Session session = sessionService.getSession(chatId).get();
            sessionService.deleteSession(session.getId());
            sendMessage(chatId, "Результат сброшен.");
        } else
            sendMessage(chatId, "Чтобы что-то сбросить, надо сначала что-нибудь набрать");
    }

    @Override
    public Header header() {
        return Header.restart;
    }

    @Override
    public String description() {
        return "Сбрасывает весь достигнутый результат игры.";
    }
}
