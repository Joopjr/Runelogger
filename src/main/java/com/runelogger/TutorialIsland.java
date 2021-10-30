package com.runelogger;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;

import javax.inject.Inject;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class TutorialIsland
{
    @Inject
    private Client client;

    @Inject
    private ApiCommunication apiCommunication;

    String lastDialogText = "";

    public void gametickTutorialIsland()
    {
        //PLAYER HAS A DIALOG SPRITE TEXT AND IS IN FROM OF LUMBRIDGE CASTLE//
        if (client.getWidget(WidgetInfo.DIALOG_SPRITE_TEXT) != null && client.getLocalPlayer().getWorldLocation().getRegionID() == 12850) {
            String dialogText = client.getWidget(WidgetInfo.DIALOG_SPRITE_TEXT).getText();

            //TEXT IS WELCOME TO LUMBRIDGE AND IS DIFFERENT FROM LAST DIALOG TEXT//
            if(dialogText == "Welcome to Lumbridge! If you need some help, simply<br>talk to the Lumbridge Guide. Look for the question<br>mark icon on your minimap to find him." && lastDialogText != dialogText)
            {
                //CURRENTLY UNUSED//
                apiCommunication.sendTutorialInfo("completed");
                lastDialogText = dialogText;    //SET LAST DIALOG TEXT TO CURRENT DIALOG TEXT
            }
        }
    }
}
