package com.runelogger;

import javax.inject.Inject;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bosses
{
    @Inject
    private ApiCommunication apiCommunication;

    public void chatBosses(String message)
    {
        //EXTRACT THE UNLOCKED ITEM
        Pattern BossesPatern = Pattern.compile("Your (.+) kill count is: <col=ff0000>(\\d+)</col>.");   //FROM SCREENSHOT API
        Matcher BossesMatcher = BossesPatern.matcher(message);

        //GET MATCHES FOR ITEM
        if (BossesMatcher.find())
        {
            //DONT PROCESS TEMPOROSS AS BOSS
            if(!Objects.equals(BossesMatcher.group(1), "Tempoross"))
            {
                //COMMUNICATE ITEM//
                apiCommunication.sendBosses(BossesMatcher.group(1));
            }
        }
    }
}
