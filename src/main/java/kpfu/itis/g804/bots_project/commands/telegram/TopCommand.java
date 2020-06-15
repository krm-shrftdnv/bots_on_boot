package kpfu.itis.g804.bots_project.commands.telegram;

import kpfu.itis.g804.bots_project.model.User;
import kpfu.itis.g804.bots_project.service.UsersService;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static kpfu.itis.g804.bots_project.service.Helper.getMemberById;

@Component
@Profile("tel")
public class TopCommand extends Command {

    @Autowired
    UsersService usersService;

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        String fromNick = message.getFrom().getUserName();
        Long fromId = new Long(message.getFrom().getId());

        StringBuilder builder = new StringBuilder();
        builder.append("Топ игроков:\n");
        List<User> topUsers = usersService.getTopUsers();

        for (int i = 0; i < topUsers.size(); i++) {
            User user = topUsers.get(i);
            builder.append(i + 1).append(". ");
            if(!user.getId().equals(fromId)){
                builder.append("Неизвестный пользователь с id ").append(user.getId());
            } else builder.append(fromNick);
            builder.append(" из ").append(user.getMessenger()).append(" ");
            builder.append(" ").append(usersService.getUserRating(user.getId())).append("\n");
        }
        sendMessage(message.getChatId(), builder.toString());
    }

    @Override
    public Header header() {
        return Header.top;
    }

    @Override
    public String description() {
        return "Возвращает топ игроков.";
    }
}
