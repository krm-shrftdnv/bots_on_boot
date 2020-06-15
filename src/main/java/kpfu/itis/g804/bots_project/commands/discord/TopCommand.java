package kpfu.itis.g804.bots_project.commands.discord;

import kpfu.itis.g804.bots_project.model.User;
import kpfu.itis.g804.bots_project.service.UsersService;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static kpfu.itis.g804.bots_project.service.Helper.getMemberById;

@Component
@Profile("dis")
public class TopCommand extends Command {

    @Autowired
    UsersService usersService;

    @Override
    public void execute(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        List<Member> members = event.getGuild().getMembers();
        StringBuilder builder = new StringBuilder();
        builder.append("Топ игроков:\n");
        List<User> topUsers = usersService.getTopUsers();
        for (int i = 0; i < topUsers.size(); i++) {
            User user = topUsers.get(i);
            builder.append(i + 1).append(". ");
            if (getMemberById(user.getId(), members).isPresent()) {
                Member member = getMemberById(user.getId(), members).get();
                builder.append(member.getNickname());
            } else builder.append("Неизвестный пользователь с id ").append(user.getId());
            builder.append(" из ").append(user.getMessenger()).append(" ");
            builder.append(" ").append(usersService.getUserRating(user.getId())).append("\n");
        }
        channel.sendMessage(builder).queueAfter(2, TimeUnit.SECONDS);
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
