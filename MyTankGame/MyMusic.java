package MyTankGame;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MyMusic {
    AePlayWave start = new AePlayWave("F:\\Learn\\src\\Music\\startMusic.wav");
    AePlayWave success = new AePlayWave("F:\\Learn\\src\\Music\\successMusic.wav");
    AePlayWave explode = new AePlayWave("F:\\Learn\\src\\Music\\explodeMusic.wav");
    AePlayWave fight = new AePlayWave("F:\\Learn\\src\\Music\\I've - 太陽と海のメロディ.wav");
    AePlayWave fruit = new AePlayWave("F:\\Learn\\src\\Music\\fruitMusic.wav");
    boolean isPlay;
    public MyMusic(){
        isPlay = true;
    }
}

class AePlayWave extends Thread {

    private String filename;
    public AePlayWave(String wavfile) {
        filename = wavfile;

    }

    public void run() {

        File soundFile = new File(filename);

        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (Exception e1) {
            e1.printStackTrace();
            return;
        }

        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        auline.start();
        int nBytesRead = 0;
        //这是缓冲
        byte[] abData = new byte[1024];

        try {
            while (nBytesRead != -1) {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0)
                    auline.write(abData, 0, nBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            auline.drain();
            auline.close();
        }

    }
}
