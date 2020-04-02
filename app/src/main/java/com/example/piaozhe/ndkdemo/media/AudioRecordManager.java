package com.example.piaozhe.ndkdemo.media;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * @Author piaozhe
 * @Date 2020/4/1 15:46
 */
public class AudioRecordManager {

    private MediaRecorder mediaRecorder;

    public void startRecord() {

        if (mediaRecorder == null) {
            mediaRecorder = new MediaRecorder();
        }

        try {

            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置麦克风

            //设置输出文件格式
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

            //设置音频文件编码
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);


            String s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ss.m4a";

            File file = new File(s);
            if (file.exists()){
                Log.i("AudioRecordManager","--------------FILE=");
                file.delete();
            }
            mediaRecorder.setOutputFile(s);

            mediaRecorder.prepare();


            mediaRecorder.start();


        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (RuntimeException e) {
            e.printStackTrace();
            Log.i("AudioRecordManager","---------e="+e.getMessage());
        }

    }



    public void stop(){
        try{
            mediaRecorder.stop();
            mediaRecorder.release();
        }catch (RuntimeException e){
            e.printStackTrace();
        }

    }
}
