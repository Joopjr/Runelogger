package com.runelogger;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.widgets.WidgetInfo;

import javax.inject.Inject;

@Slf4j
public class LevelingUp
{
    @Inject private Client client;

    @Inject private ApiCommunication apiCommunication;

    private String lastLevelUp;
    private Integer storedCombatLevel = 0;
    private String levelUpWidgetText = "";

    public void gametickLevelingUp()
    {
        //LEVEL UP WIDGET PRESENT
        if (client.getWidget(WidgetInfo.LEVEL_UP_LEVEL) != null)
            levelUpWidgetText = client.getWidget(WidgetInfo.LEVEL_UP_LEVEL).getText();  //EXTRACT TEXT FROM WIDGET
        else if(client.getWidget(WidgetInfo.DIALOG_SPRITE_TEXT) != null)
            levelUpWidgetText = client.getWidget(WidgetInfo.DIALOG_SPRITE_TEXT).getText();  //EXTRACT TEXT FROM WIDGET

        //THERE IS A LEVEL UP TEXT
        if(levelUpWidgetText != "")
        {
            //EXTRACT THE SKILL AND LEVEL
            Pattern levelingUpPatern = Pattern.compile(".*Your ([a-zA-Z]+) (?:level is|are)? now (\\d+)\\.");  //FROM SCREENSHOT PLUGIN
            Matcher levelingUpMatcher = levelingUpPatern.matcher(levelUpWidgetText);

            //GET MATCHES FOR LEVEL UP MESSAGE//
            if (levelingUpMatcher.find()) {
                String skillName = levelingUpMatcher.group(1);
                Integer skillLevel = Integer.parseInt(levelingUpMatcher.group(2));

                //CHECK IF THIS LEVEL UP DIFFERS FROM LAST LEVEL UP (TO AVOID DUPLICATE ENTRIES)
                if(!Objects.equals(lastLevelUp, skillName+skillLevel))
                {
                    apiCommunication.sendLevelingUpInfo(skillName, skillLevel);
                    lastLevelUp = skillName+skillLevel; //SET CURRENT LEVEL UP AS LAST LEVEL UP
                }
            }

            //CURRENT COMBAT LEVEL IS NOT EMPTY AND STORED COMBAT LEVEL IS LOWER THAN CURRENT LEVEL
            if(storedCombatLevel > 0 && storedCombatLevel < client.getLocalPlayer().getCombatLevel())
            {
                apiCommunication.sendLevelingUpInfo("Combat", client.getLocalPlayer().getCombatLevel());
                storedCombatLevel = client.getLocalPlayer().getCombatLevel();   //SET STORED COMBAT LEVEL TO CURRENT LEVEL
            }

            levelUpWidgetText = ""; //RESET LEVEL UP WIDGET TEXT
        }

        //THERE IS NO COMBAT LEVEL STORED
        if(storedCombatLevel == 0 && client.getLocalPlayer().getCombatLevel() > 0)
            storedCombatLevel = client.getLocalPlayer().getCombatLevel();   //SET STORED COMBAT LEVEL TO CURRENT LEVEL
    }
}
