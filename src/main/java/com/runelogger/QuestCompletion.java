package com.runelogger;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.widgets.WidgetInfo;

import javax.inject.Inject;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class QuestCompletion
{
    @Inject private Client client;

    @Inject private ApiCommunication apiCommunication;

    String lastQuestCompleted;

    public void gametickQuestCompletion()
    {
        //PLAYER LEVELED
        if (client.getWidget(WidgetInfo.QUEST_COMPLETED_NAME_TEXT) != null)
        {
            //GET WIDGET
            String questCompleteWidgetText = client.getWidget(WidgetInfo.QUEST_COMPLETED_NAME_TEXT).getText();

            //EXTRACT THE QUEST
            Pattern questCompletePattern1 = Pattern.compile(".+?ve\\.*? (?<verb>been|rebuilt|.+?ed)? ?(?:the )?'?(?<quest>.+?)'?(?: [Qq]uest)?[!.]?$");  //FROM SCREENSHOT PLUGIN
            Matcher questCompleteMatcher1 = questCompletePattern1.matcher(questCompleteWidgetText);

            Pattern questCompletePattern2 = Pattern.compile("'?(?<quest>.+?)'?(?: [Qq]uest)? (?<verb>[a-z]\\w+?ed)?(?: f.*?)?[!.]?$");  //FROM SCREENSHOT PLUGIN
            Matcher questCompleteMatcher2 = questCompletePattern2.matcher(questCompleteWidgetText);

            Matcher questCompleteMatcher = questCompleteMatcher1.matches() ? questCompleteMatcher1 : questCompleteMatcher2;

            String quest = questCompleteMatcher.group("quest");

            //CHECK IF THIS LEVEL UP DIFFERS FROM LAST LEVEL UP (TO AVOID DUPLICATE ENTRIES)
            if(!Objects.equals(lastQuestCompleted, quest))
            {
                apiCommunication.sendQuestCompletionInfo(quest);
                lastQuestCompleted = quest; //SET CURRENT LEVEL UP AS LAST LEVEL UP
            }
        }
    }
}
