/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prototyes;

import static Prototyes.DashBoard.filePath;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.bson.Document;

/**
 *
 * @author akvnr
 */
class SimpleAudioPlayer {
    Long currentFrame;
	Clip clip;
	String status;	
	AudioInputStream audioInputStream;
	String path,His="";
        int his;
       public void getpath()
       {
           ConnectionString connectionString = new ConnectionString("mongodb+srv://saran2521:Ak2521@cluster0.ohqpxtl.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        com.mongodb.client.MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("VoiceMail");
        MongoCollection col = database.getCollection("History");
        MongoCursor<Document> cursor = col.find().cursor();
        try {
            while (cursor.hasNext()) {
                Document obj = (Document) cursor.next();
                His = (String) obj.get("_id");
                
            }
     
        } catch (Exception e) {
        }
           if(His.equals(""))
        {
            his=0;
        }
        else
        {
            his=Integer.parseInt(His);
        }
        his=his+1;
        path="C:\\Users\\akvnr\\Documents\\NetBeansProjects\\VoiceChat\\"+String.valueOf(his)+".mp3";
       }
        
	public SimpleAudioPlayer()
		throws UnsupportedAudioFileException,
		IOException, LineUnavailableException
	{
                getpath();
		audioInputStream =
				AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
        public void play()
	{
		//start the clip
		clip.start();
		
		status = "play";
	}
	
	// Method to pause the audio
	public void pause()
	{
		if (status.equals("paused"))
		{
			System.out.println("audio is already paused");
			return;
		}
		this.currentFrame =
		this.clip.getMicrosecondPosition();
		clip.stop();
		status = "paused";
	}
	
	
	
	// Method to stop the audio
	public void stop() throws UnsupportedAudioFileException,
	IOException, LineUnavailableException
	{
            getpath();
		currentFrame = 0L;
		clip.stop();
		clip.close();
	}
}
