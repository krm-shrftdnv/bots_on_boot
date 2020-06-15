package kpfu.itis.g804.bots_project.controller.receivers;

import kpfu.itis.g804.bots_project.controller.resolvers.TelegramResolver;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import javax.annotation.Resource;

@PropertySource("classpath:application.properties")
@Component
@Profile("tel")
public class TelegramReceiver extends TelegramLongPollingBot {

    private final TelegramResolver telegramResolver;

    @Resource
    private Environment env;

    public TelegramReceiver(@Lazy TelegramResolver telegramResolver){
        this.telegramResolver = telegramResolver;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            System.out.println(update.getMessage().getFrom().getUserName());
            System.out.println(update.getMessage().getText());
            telegramResolver.executeCommand(update);
        }
    }

    @Override
    public String getBotUsername() {
        return "4Pics1WordBot";
    }

    @Override
    public String getBotToken() {
//        return env.getRequiredProperty("telegram.token");

        //YOUR TELEGRAM TOKEN
        String telegramToken = "";

        return telegramToken;
    }
}
