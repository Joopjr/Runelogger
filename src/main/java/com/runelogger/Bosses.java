package com.runelogger;

import javax.inject.Inject;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bosses
{
    @Inject private ApiCommunication apiCommunication;

    public void chatBosses(String message)
    {
        //EXTRACT THE UNLOCKED ITEM
        Pattern BossesPatern = Pattern.compile("Your (.+) kill count is: <col=ff0000>(\\d+)</col>.");   //FROM SCREENSHOT API
        Matcher BossesMatcher = BossesPatern.matcher(message);

        //GET MATCHES FOR ITEM
        if (BossesMatcher.find())
        {
            //COMMUNICATIE BOSS (WHEN BOSS ISN'T TEMPOROSS)
            if(!Objects.equals(BossesMatcher.group(1), "Tempoross")) apiCommunication.sendBosses(BossesMatcher.group(1));
        }
    }
}
