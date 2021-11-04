package com.runelogger;

import javax.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CollectionLog
{
    @Inject private ApiCommunication apiCommunication;

    public void chatCollectionLog(String message)
    {
        //EXTRACT THE UNLOCKED ITEM
        Pattern CollectionLogPatern = Pattern.compile("New item added to your collection log: <col=ff0000>(.*?)(</col>|$)");
        Matcher CollectionLogMatcher = CollectionLogPatern.matcher(message);

        // COMMUNICATE ITEM //
        if (CollectionLogMatcher.find()) apiCommunication.sendCollectionLog(CollectionLogMatcher.group(1));
    }
}
