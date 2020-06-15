package kpfu.itis.g804.bots_project.config;

import kpfu.itis.g804.bots_project.controller.receivers.TelegramReceiver;
import kpfu.itis.g804.bots_project.controller.resolvers.TelegramResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan(basePackages = "kpfu.itis.g804.bots_project")
@Profile("tel")
public class TelegramBotConfig {

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public TelegramLongPollingBot init(TelegramResolver telegramResolver){
//        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            TelegramLongPollingBot telegramLongPollingBot = new TelegramReceiver(telegramResolver);
            telegramBotsApi.registerBot(telegramLongPollingBot);
            return telegramLongPollingBot;
        } catch (TelegramApiRequestException e) {
            throw new IllegalStateException("Failed creating bot", e);
        }
    }

}
