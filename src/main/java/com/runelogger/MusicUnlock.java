package com.runelogger;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class MusicUnlock
{
    @Inject private ApiCommunication apiCommunication;

    public void chatMusicUnlock(String message)
    {
        //EXTRACT THE UNLOCKED MUSIC
        Pattern MusicUnlockPatern = Pattern.compile("You have unlocked a new music track: <col=ff0000>(.*?)(</col>|$)");
        Matcher MusicUnlockMatcher = MusicUnlockPatern.matcher(message);

        //COMMUNICATE UNLOCKED MUSIC TRACK//
        if (MusicUnlockMatcher.find()) apiCommunication.sendMusicUnlock(MusicUnlockMatcher.group(1));
    }
}
