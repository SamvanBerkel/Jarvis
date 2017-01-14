package model;

import com.sun.speech.freetts.VoiceManager;

/**
 * Created by Wessel-GamePC on 1/13/2017.
 */
public class Voice {

    private String name; // Name of the voice that we want to use

    private com.sun.speech.freetts.Voice voice; // Create an empty instance of the voice class within sun.speech

    public Voice(String name) {
        this.name = name;
        this.voice = VoiceManager.getInstance().getVoice(this.name); // gets the voice correspondingly
        this.voice.allocate();
    }

    public void say(String result) {
        this.voice.speak(result);
    }
}
