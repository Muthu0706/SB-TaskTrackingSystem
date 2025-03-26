package com.tasktrackingsystem.util;


import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TaskServiceImplUtil {

    public static String convertMillisToSignificantTime(long millis) {
    	
        if (millis < 60000) {
            return millis / 1000 + " second" + (millis / 1000 != 1 ? "s" : "");
        }
        
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        if (days > 0) {
            return days + " day" + (days > 1 ? "s" : "") + ", " + (hours % 24) + " hour" + (hours % 24 != 1 ? "s" : "");
        }
        if (hours > 0) {
            return hours + " hour" + (hours != 1 ? "s" : "") + ", " + (minutes % 60) + " minute" + (minutes % 60 != 1 ? "s" : "");
        }
        return minutes + " minute" + (minutes != 1 ? "s" : "");
    }

}
