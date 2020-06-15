package kpfu.itis.g804.bots_project.commands.telegram;

import kpfu.itis.g804.bots_project.model.Session;
import kpfu.itis.g804.bots_project.service.SessionService;
import kpfu.itis.g804.bots_project.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

@Component
@Profile("tel")
public class GuessCommand extends Command {

    @Autowired
    SessionService sessionService;
    @Autowired
    UsersService usersService;

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        Long userId = new Long(message.getFrom().getId());

        if (message.getText().split(" ").length > 1) {

            String word = message.getText().split(" ")[1].toLowerCase();
            if (sessionService.getSession(chatId).isPresent()) {
                Session session = sessionService.getSession(chatId).get();
                if (session.isOpen() && !session.isGuessed()) {
                    if (word.equals(session.getAnswer())) {
                        usersService.addUserPoint(userId, "telegram");
                        sessionService.updateSession(session.getId(), session.getLastId(), session.getImgUrl(), session.getAnswer(), true);
                        sendMessage(chatId, message.getFrom().getFirstName() + ", ты угадал! Чтобы продолжить играть напишите команду /play. ");
                    } else {
                        sendMessage(chatId, message.getFrom().getFirstName() + ", ты не угадал. Пробуйте ещё.");
                    }
                } else {
                    sendMessage(chatId, "Сначала продолжите игру командой /play");
                }
            } else {
                sendMessage(chatId, "Сначала начните игру командой /play");
            }
        }

    }

    @Override
    public Header header() {
        return Header.guess;
    }

    @Override
    public String description() {
        return "Попробуйте отгадать загаданное в картинках слово. Например \"/guess красный\"";
    }
}
