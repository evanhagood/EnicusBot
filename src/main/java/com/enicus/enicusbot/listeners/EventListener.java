package com.enicus.enicusbot.listeners;

/* get access to all events provided by the JDA API */
// https://jda.wiki/introduction/events-list/
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class EventListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        User user = event.getUser();
        String emoji = event.getReaction().getEmoji().getAsReactionCode();
        String channelMention = event.getChannel().getAsMention();
        String jumpLink = event.getJumpUrl();

        String message =
                user.getAsMention() + " reacted to a message with " + emoji +
                        " in the " + channelMention + " channel!";

        event.getChannel().sendMessage(message).queue(); // REST action (queue using .queue())
        System.out.println("Done!");
    }

    /*
    * TODO: fix this
    * Discord has deprecated the use of text commands in favor for /. Implement slash commands instead
    * also this is just a bad way to implement this regardless... no way to change prefix among other things.
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        switch(message) {
            case "$ping": event.getChannel().sendMessage(event.getAuthor().getAsMention() + "pong!").queue(); break;
            case "$test": event.getChannel().sendMessage("test!").queue(); break;
            case "$forthecompany": forTheCompany(event); break;
        }
    }

    private void forTheCompany(MessageReceivedEvent event) {
        //System.out.println("debug :: From forTheCompany()");
        event.getChannel().sendMessage("WE LIVE").queue();
        event.getChannel().sendMessage("WE DIE").queue();
        event.getChannel().sendMessage("FOR THE COMPANY!").queue();
    }
}
