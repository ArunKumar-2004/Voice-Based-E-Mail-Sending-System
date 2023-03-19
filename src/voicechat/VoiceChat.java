/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package voicechat;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JOptionPane;

/**
 *
 * @author akvnr
 */
public class VoiceChat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try
        {
            AudioFormat af =new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,44100,16,2,4,44100,false);
            DataLine.Info datainfo =new DataLine.Info(TargetDataLine.class, af);
            if(AudioSystem.isLineSupported(datainfo))
            {
                System.out.println("Not Supported");
            }
            TargetDataLine tl =(TargetDataLine)AudioSystem.getLine(datainfo);
            tl.open();
            tl.start();
            int i=0;
            i++;
            Thread audio =new Thread()
            { 
             
                @Override
                public void run()
                {
                    AudioInputStream rec =new AudioInputStream(tl);
                    File out =new File("Record.mp3");
                    try
                    {
                      AudioSystem.write(rec, AudioFileFormat.Type.WAVE, out);
                      
                    }
                    catch(IOException e1)
                    {
                        
                    }
                    
                }
            };
            audio.start();
            
            tl.stop();
            tl.close();
        }
        catch(HeadlessException | LineUnavailableException e)
        {
            
        }
    }
    
}
