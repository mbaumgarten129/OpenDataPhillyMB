package edu.upenn.cit594.logging;
/**
 * this class uses the Singleton design pattern to log program activity
 */

import java.io.FileWriter;
import java.io.IOException;

public class ActivityLogger {
    private static ActivityLogger onlyLogger=null;
    private FileWriter fileWriter;
    private static String fileName;



    private ActivityLogger(String fileName){
        try {
            fileWriter=new FileWriter(fileName, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ActivityLogger getInstance(){
        if(onlyLogger==null){
            onlyLogger=new ActivityLogger(fileName);
        }
        return onlyLogger;
    }

    /**
     * method takes in a string and logs the time of the activity in milliseconds
     * along with the string associated with the activity
     * @param message the message/string
     */
    public void mustLog(String message){
        try {
            fileWriter.write(System.currentTimeMillis() + " " + message + "\n");
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println("Could not log " + message);
        }

    }
    
    public static void setFilename(String fileName) {
		ActivityLogger.fileName = fileName;
	}

}
