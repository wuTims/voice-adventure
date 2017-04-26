package com.example.tim_w.voiceadventure;

import android.annotation.TargetApi;
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
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private TextView txtFromSpeech;
    private TextView txtFromDesc;
    private final int REQ_CODE_SPEECH_INPUT = 1;
    private final String UTTERANCE_ID = "VoiceAdventure";

    private ShakeListener shaker;

    private TextToSpeech tts;

    private AdventureMap map;
    private Scene currentScene;
    private Inventory inventory;

    @TargetApi(21)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        txtFromSpeech = (TextView) findViewById(R.id.txtFromSpeech);
        txtFromDesc = (TextView) findViewById(R.id.txtFromDesc);

        tts = new TextToSpeech(this, this);



        shaker = new ShakeListener(this);
        shaker.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                vibe.vibrate(100);
                promptSpeechInput();
            }
        });

        inventory = new Inventory();

        map = new AdventureMap(3, 5);
        loadMap();
        currentScene.load(txtFromDesc);
        speakOut();
    }

    private void loadMap(){
        Scene intro = new IntroScene(this.inventory);
        Scene frontHouse = new FrontHouseScene(this.inventory);
        Scene enterHouse = new EnterHouseScene();
        Scene articuno = new EnterHouseScene();
        Scene charmander = new EnterHouseScene();
        Scene pikachu = new EnterHouseScene();
        map.setSceneAtPosition(intro, 0, 1);
        map.setSceneAtPosition(frontHouse, 1, 1);
        map.setSceneAtPosition(enterHouse, 2, 1);
        map.setSceneAtPosition(articuno, 4, 0);
        map.setSceneAtPosition(charmander, 3, 1);
        map.setSceneAtPosition(pikachu, 4, 2);

        currentScene = intro;
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
            String text = result.get(0);
            txtFromSpeech.setText(text);
            text = text.toUpperCase();
            String[] speechInput = text.split(" ", 2);
            String keyword = speechInput[0];
            String command = "";
            String output = "";
            if(speechInput.length > 1) command = speechInput[1];

            Scene tempScene = null;
            switch(keyword){
                case "GO":
                case "NAVIGATE":
                    tempScene = this.currentScene.navigate(command, this.map);
                    if(tempScene != null){
                        this.currentScene = tempScene;
                        this.currentScene.load(txtFromDesc);
                    }
                    break;
                case "OPEN":
                case "ENTER":
                    tempScene = this.currentScene.navigate(keyword, command, this.map);
                    if(tempScene != null){
                        this.currentScene = tempScene;
                        this.currentScene.load(txtFromDesc);
                    }
                    break;
                default:
                    output = this.currentScene.performAction(keyword, command);
                    txtFromDesc.setText(output);
                    break;

            }
            speakOut();
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


    @TargetApi(21)
    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            int result = tts.setLanguage(Locale.US);

            if(result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "This Language is not supported");
            }

            for(Voice v : tts.getVoices()){
                Log.v("VOICE", v.getName());
            }
            tts.setSpeechRate(2f);
            tts.setPitch(0.3f);


            speakOut();
        }else{
            Log.e("TTS", "INIT FAILED!");
        }
    }

    @TargetApi(21)
    private void speakOut() {
        String exScene = "Team rocket had stolen four of your Poke e mawn. You followed them to a haunted house. The front door is locked. There is a mailbox to the right and a lantern to the left.";
        String testScene = "Articuno, Pikachu, Charmander, Onyx";
        tts.speak(txtFromDesc.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, UTTERANCE_ID);
    }

    public AdventureMap getMap() {
        return this.map;
    }
}
