package com.runelogger;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;

import javax.inject.Inject;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class DuelArena
{
    @Inject
    private Client client;

    @Inject
    private ApiCommunication apiCommunication;

    boolean duelArenaWidgetOpen = false;

    public void gametickDuelArena()
    {
        //GET WINTERTODT WIDGET
        Widget duelArenaWidget = client.getWidget(372, 17);

        //THERE IS A DUEL ARENA WIDGET AND IT NEEDS PROCESSING
        if(duelArenaWidget != null && !duelArenaWidgetOpen)
        {
            duelArenaWidgetOpen = true;

            String winnerCombat = client.getWidget(372, 6).getText();
            String winnerName = client.getWidget(372, 7).getText();

            String loserCombat = client.getWidget(372, 2).getText();
            String loserName = client.getWidget(372, 3).getText();

            String winningValue = client.getWidget(372, 40).getText();

            if(Objects.equals(winnerName, client.getLocalPlayer().getName()))
            {
                apiCommunication.sendDuelArena("won", winnerCombat, loserName, loserCombat, winningValue);
            }
            else
            {
                apiCommunication.sendDuelArena("lost", loserCombat, winningValue, winnerCombat, loserName);
            }
        }
        else if (duelArenaWidget == null && duelArenaWidgetOpen)
        {
            duelArenaWidgetOpen = false;
        }
    }
}
