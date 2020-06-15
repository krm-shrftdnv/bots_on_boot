package kpfu.itis.g804.bots_project.config;

import kpfu.itis.g804.bots_project.controller.receivers.DiscordReceiver;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;

@Configuration
@ComponentScan(basePackages = "kpfu.itis.g804.bots_project")
@EnableAspectJAutoProxy
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@Profile("dis")
public class DiscordBotConfig {

    @Resource
    private Environment env;

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public JDA jda() throws LoginException {
//        JDABuilder builder = JDABuilder.createDefault(env.getRequiredProperty("discord.token"));
        // YOUR DISCORD TOKEN
        String discordToken = "";

        JDABuilder builder = JDABuilder.createDefault(discordToken);
        builder.setActivity(Activity.listening("@krm_as_bot"));
        builder.addEventListeners(applicationContext.getBean(DiscordReceiver.class));
        return builder.build();
    }

}
