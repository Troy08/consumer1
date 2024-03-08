package com.example;

/**
 * @Date 2024/2/22 19:19
 * @Created by weixiao
 */
public class MessageData {
    String resortID;
    String seasonID;
    String dayID;
    String skierID;
    String time;
    String liftID;

    public MessageData(String resortID, String seasonID,
                       String dayID, String skierID, String time,
                       String liftID) {
        this.resortID = resortID;
        this.seasonID = seasonID;
        this.dayID = dayID;
        this.skierID = skierID;
        this.time = time;
        this.liftID = liftID;
    }

    public String getResortID() {
        return resortID;
    }

    public String getSeasonID() {
        return seasonID;
    }

    public String getDayID() {
        return dayID;
    }

    public String getSkierID() {
        return skierID;
    }

    public String getTime() {
        return time;
    }

    public String getLiftID() {
        return liftID;
    }
}
