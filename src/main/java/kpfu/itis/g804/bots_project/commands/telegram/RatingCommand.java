package kpfu.itis.g804.bots_project.commands.telegram;

import kpfu.itis.g804.bots_project.service.UsersService;
import net.dv8tion.jda.api.entities.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Profile("tel")
public class RatingCommand extends Command {

    @Autowired
    UsersService usersService;

    @Override
    public void execute(Update update) {
        Message message = update.getMessage();
        String fromNick = message.getFrom().getUserName();
        Long fromId = new Long(message.getFrom().getId());
        List<MessageEntity> entities = message.getEntities();
        List<MessageEntity> mentions = new ArrayList<>();
        for(MessageEntity entity : entities){
            if(entity.getType().equals(EntityType.MENTION))  mentions.add(entity);
        }

        if(mentions.size()>0){
            User user = mentions.get(0).getUser();
            Long userId = new Long(user.getId());
            int rating = usersService.getUserRating(userId);
            sendMessage(message.getChatId(), "Пользователь " + user.getUserName() + " имеет рейтинг - " + rating);
        } else {
            int rating = usersService.getUserRating(fromId);
            sendMessage(message.getChatId(), "Пользователь " + fromNick + " имеет рейтинг - " + rating);
        }

    }

    @Override
    public Header header() {
        return Header.rating;
    }

    @Override
    public String description() {
        return "Возвращает ваш глобальный рейтинг или рейтинг отмеченного пользователя";
    }
}
