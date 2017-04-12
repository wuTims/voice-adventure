package com.example.tim_w.voiceadventure;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private TextView txtFromSpeech;
    private final int REQ_CODE_SPEECH_INPUT = 1;
    private final String UTTERANCE_ID = "VoiceAdventure";

    private ShakeListener shaker;

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        txtFromSpeech = (TextView) findViewById(R.id.txtFromSpeech);

        tts = new TextToSpeech(this, this);

        shaker = new ShakeListener(this);
        shaker.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                vibe.vibrate(100);
                promptSpeechInput();
            }
        });
    }

    private void promptSpeechInput(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));

        try{
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        }catch(ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null){
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            txtFromSpeech.setText(result.get(0));
            if(result.get(0).equalsIgnoreCase("start game")){
                speakOut();
            }

        }
    }

    @Override
    protected void onResume(){
        shaker.resume();
        super.onResume();
    }
    @Override
    protected void onPause() {
        shaker.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if(tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            int result = tts.setLanguage(Locale.US);

            if(result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "This Language is not supported");
            }
        }else{
            Log.e("TTS", "INIT FAILED!");
        }
    }

    private void speakOut() {
        String text = txtFromSpeech.getText().toString();
        String exScene = "Team rocket had stolen four of your Poke e mawn. You followed them to a haunted house. The front door is locked. There is a mailbox to the right and a lantern to the left.";
        String testScene = "Articuno, Pikachu, Charmander, Onyx";
        tts.speak(exScene, TextToSpeech.QUEUE_FLUSH, null);
    }
}
