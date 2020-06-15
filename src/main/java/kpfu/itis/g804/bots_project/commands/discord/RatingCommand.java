package kpfu.itis.g804.bots_project.commands.discord;

import kpfu.itis.g804.bots_project.service.UsersService;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Profile("dis")
public class RatingCommand extends Command {

    @Autowired
    UsersService usersService;


    @Override
    public void execute(MessageReceivedEvent event) {
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();

        if(message.getMentionedMembers().size()>1){
            Member member = message.getMentionedMembers().get(1);
            if(!member.getUser().isBot()) {
                int rating = usersService.getUserRating(member.getIdLong());
                channel.sendMessage("Пользователь " + member.getAsMention() + " имеет рейтинг - " + rating).queueAfter(2, TimeUnit.SECONDS);
            } else channel.sendMessage("Серьезно? Он бот. Технологии ещё не настолько развились, чтобы тратить впустую своё время.").queueAfter(2, TimeUnit.SECONDS);
        } else {
            int rating = usersService.getUserRating(message.getAuthor().getIdLong());
            channel.sendMessage("Пользователь " + message.getAuthor().getAsMention() + " имеет рейтинг - " + rating).queueAfter(2, TimeUnit.SECONDS);
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
