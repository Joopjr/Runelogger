package com.runelogger;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class MusicUnlock
{
    @Inject
    private ApiCommunication apiCommunication;

    public void chatMusicUnlock(String message)
    {
        //EXTRACT THE UNLOCKED MUSIC
        Pattern MusicUnlockPatern = Pattern.compile("You have unlocked a new music track: <col=ff0000>(.*?)(</col>|$)");
        Matcher MusicUnlockMatcher = MusicUnlockPatern.matcher(message);

        //GET MATCHES FOR SUBDUEING WINTERTODT
        if (MusicUnlockMatcher.find()) {
            //COMMUNICATE SCORE//
            apiCommunication.sendMusicUnlock(MusicUnlockMatcher.group(1));

            //SUBDUED AMOUNT
        }
    }
}
