package kpfu.itis.g804.bots_project.commands.telegram;

import kpfu.itis.g804.bots_project.model.Session;
import kpfu.itis.g804.bots_project.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Update;

@Component
@Profile("tel")
public class StopCommand extends Command {

    @Autowired
    SessionService sessionService;

    @Override
    public void execute(Update update) {

        Long chatId = update.getMessage().getChatId();

        if (sessionService.getSession(chatId).isPresent()) {
            Session session = sessionService.getSession(chatId).get();
            if (session.isOpen()) {
                sessionService.stopSession(session.getId());
                sendMessage(chatId, "Игра приостановлена, результат сохранён. Для продолжания используйте команду /play");
            }
        } else sendMessage(chatId, "Сначала /play, потом /stop. It's easy, bro!");

    }

    @Override
    public Header header() {
        return Header.stop;
    }

    @Override
    public String description() {
        return "Останавливает игру, сохраняя достигнутый результат. Вы можете возобновить игру комнадой /play";
    }
}
