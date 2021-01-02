package net.runelite.client.plugins.coxspecialloothider;

import com.google.inject.Provides;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.MessageNode;
import net.runelite.client.chat.ChatMessageManager;

import java.util.ArrayList;

/**
 * @author Danny
 */
@Slf4j
@PluginDescriptor(
        name = "CoX Special Loot Hider"
)

public class CoxSpecialLootHiderPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private CoxSpecialLootHiderConfig config;

    @Inject
    private ChatMessageManager chatMessageManager;

    private ArrayList<String> turnOffMessages = new ArrayList<String>();

    @Override
    protected void startUp() throws Exception {
        turnOffMessages.clear();
        client.refreshChat();
    }

    @Override
    protected void shutDown() throws Exception {
        //Shows the player the messages that were censored
        //Multiple items are stored in the ArrayList and re-sent upon turning off the plugin
        //On completion of another raid, the list is cleared
        if (!turnOffMessages.isEmpty()) {
            turnOffMessages.forEach((n) -> client.addChatMessage(ChatMessageType.FRIENDSCHATNOTIFICATION, "", n, ""));
        }
        client.refreshChat();
    }

    @Subscribe
    public void onChatMessage(ChatMessage chatMessage) {
        if (chatMessage.getType() == ChatMessageType.FRIENDSCHATNOTIFICATION) {
            //Shown when completing a raid. When this happens, the previous loots are cleared from our storage of
            //messages so that the player only sees items from the last raid when turning off the plugin
            if (chatMessage.getMessage().contains("Congratulations - your raid is complete!")) {
                turnOffMessages.clear();
            }

            //If it has playerName - Item, we know to censor and store it
            //If its a Friends Chat Notification and has the item name, the message is replaced with a censored one
            //and the original message is stored so you can see it later
            if (chatMessage.getMessage().contains("Dexterous prayer scroll") ||
                    chatMessage.getMessage().contains("Arcane prayer scroll") ||
                    chatMessage.getMessage().contains("Twisted buckler") ||
                    chatMessage.getMessage().contains("Dragon hunter crossbow") ||
                    chatMessage.getMessage().contains("Dinh's bulwark") ||
                    chatMessage.getMessage().contains("Ancestral hat") ||
                    chatMessage.getMessage().contains("Ancestral robe top") ||
                    chatMessage.getMessage().contains("Ancestral robe bottom") ||
                    chatMessage.getMessage().contains("Dragon claws") ||
                    chatMessage.getMessage().contains("Elder maul") ||
                    chatMessage.getMessage().contains("Kodai insignia") ||
                    chatMessage.getMessage().contains("Twisted bow")) {

                //Setting an empty message string that will keep the player that got the drop while replacing
                //the drop with question marks.
                String msg = "";

                //Stores the message so the player can turn off the plugin to see the item
                turnOffMessages.add(chatMessage.getMessage());

                //Finding the literal item to censor
                //Replaces item name with ???
                if (chatMessage.getMessage().contains("Dexterous prayer scroll")) {
                    msg = chatMessage.getMessage().replace("Dexterous prayer scroll", "???");
                } else if (chatMessage.getMessage().contains("Arcane prayer scroll")) {
                    msg = chatMessage.getMessage().replace("Arcane prayer scroll", "???");
                } else if (chatMessage.getMessage().contains("Twisted buckler")) {
                    msg = chatMessage.getMessage().replace("Twisted buckler", "???");
                } else if (chatMessage.getMessage().contains("Dragon hunter crossbow")) {
                    msg = chatMessage.getMessage().replace("Dragon hunter crossbow", "???");
                } else if (chatMessage.getMessage().contains("Dinh's bulwark")) {
                    msg = chatMessage.getMessage().replace("Dinh's bulwark", "???");
                } else if (chatMessage.getMessage().contains("Ancestral hat")) {
                    msg = chatMessage.getMessage().replace("Ancestral hat", "???");
                } else if (chatMessage.getMessage().contains("Ancestral robe top")) {
                    msg = chatMessage.getMessage().replace("Ancestral robe top", "???");
                } else if (chatMessage.getMessage().contains("Ancestral robe bottom")) {
                    msg = chatMessage.getMessage().replace("Ancestral robe bottom", "???");
                } else if (chatMessage.getMessage().contains("Dragon claws")) {
                    msg = chatMessage.getMessage().replace("Dragon claws", "???");
                } else if (chatMessage.getMessage().contains("Elder maul")) {
                    msg = chatMessage.getMessage().replace("Elder maul", "???");
                } else if (chatMessage.getMessage().contains("Kodai insignia")) {
                    msg = chatMessage.getMessage().replace("Kodai insignia", "???");
                } else if (chatMessage.getMessage().contains("Twisted bow")) {
                    msg = chatMessage.getMessage().replace("Twisted bow", "???");
                }

                //Changing the message of the chatMessage. This only sets the message on the backend
                chatMessage.setMessage(msg);

                //Updating it on the UI end
                final MessageNode messageNode = chatMessage.getMessageNode();
                messageNode.setRuneLiteFormatMessage(msg);
                chatMessageManager.update(messageNode);
                client.refreshChat();
            }
        }
    }

    @Provides
    CoxSpecialLootHiderConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(CoxSpecialLootHiderConfig.class);
    }
}