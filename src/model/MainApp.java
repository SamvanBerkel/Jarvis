package model;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApp extends Application {
    private Logger logger = Logger.getLogger(getClass().getName());
    private static String speech;
	static Voice voice;
	private String result;
	private static Scene scene;
	Thread	speechThread;
	Thread	resourcesThread;
	private LiveSpeechRecognizer recognizer;

    /**
     * Java MainApp Application Method
     *
     * @param args
     */
    public static void main(String[] args) {
        //launch(args);
        voice = new Voice("kevin16");

        // // Be sure that the user can't start this application by not giving
        // the
        // // correct entry string
        // if (args.length == 1 && "SPEECH".equalsIgnoreCase(args[0]))
        new MainApp();
        // else
        // Logger.getLogger(MainApp.class.getName()).log(Level.WARNING, "Give me
        // the correct entry string..");
    }

    public static Voice getVoice(){
        return voice;
    }

	/**
	 * Constructor
	 */
	public MainApp() {
		// Loading Message
		logger.log(Level.INFO, "Loading..\n");

		// Configuration
		Configuration configuration = new Configuration();

		// Load model from the jar
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");

		// if you want to use LanguageModelPath disable the 3 lines after which
		// are setting a custom grammar->

		// configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin")

		// Grammar
		configuration.setGrammarPath("Resources/grammars");
		configuration.setGrammarName("grammar");
		configuration.setUseGrammar(true);

		try {
			recognizer = new LiveSpeechRecognizer(configuration);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, null, ex);
		}

		// Start recognition process pruning previously cached data.
		recognizer.startRecognition(true);

		// Start the Thread
		startSpeechThread();
		startResourcesThread();
		Arduino.startDataSendThread();
	}

	/**
	 * Starting the main Thread of speech recognition
	 */
	protected void startSpeechThread() {

		// alive?
		if (speechThread != null && speechThread.isAlive())
			return;

		// initialise
		speechThread = new Thread(() -> {
			logger.log(Level.INFO, "You can start to speak...\n");
			try {
				while (true) {
					/*
					 * This method will return when the end of speech is
					 * reached. Note that the end pointer will determine the end
					 * of speech.
					 */
					SpeechResult speechResult = recognizer.getResult();
					if (speechResult != null) {
						result = speechResult.getHypothesis();
						System.out.println("You said: [" + result + "]\n");
						makeDecision(result);
						// logger.log(Level.INFO, "You said: " + result + "\n")

					} else
						logger.log(Level.INFO, "I can't understand what you said.\n");
				}
			} catch (Exception ex) {
				logger.log(Level.WARNING, null, ex);
			}
			logger.log(Level.INFO, "SpeechThread has exited...");
		});

		// Start
		speechThread.start();
	}

	/**
	 * Starting a Thread that checks if the resources needed to the
	 * SpeechRecognition library are available
	 */
	protected void startResourcesThread() {

		// alive?
		if (resourcesThread != null && resourcesThread.isAlive())
			return;

		resourcesThread = new Thread(() -> {
			try {

				// Detect if the microphone is available
				while (true) {
					if (AudioSystem.isLineSupported(Port.Info.MICROPHONE)) {
						// logger.log(Level.INFO, "Microphone is available.\n")
					} else {
						// logger.log(Level.INFO, "Microphone is not
						// available.\n")

					}

					// Sleep some period
					Thread.sleep(350);
				}

			} catch (InterruptedException ex) {
				logger.log(Level.WARNING, null, ex);
				resourcesThread.interrupt();
			}
		});
		// Start
		resourcesThread.start();
	}

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Resources/view/MainMenu.fxml"));

        scene = new Scene(root, 1920, 1080);

        stage.setFullScreen(true);
        stage.setTitle("Jarvis");
        stage.setScene(scene);
        stage.show();
    }

	/**
	 * Takes a decision based on the given result
	 */
	public static void makeDecision(String speech) throws IOException, JSONException {
		// Split the sentence
		String[] array = speech.split(" ");

		weatherRequests(speech);

		if (speech.contains("what") && speech.contains("time") && !speech.contains("bus")){
			timeRequest();
		}

		if (speech.contains("date")) {
			dateRequest();
		}

		if (speech.contains("on") && (speech.contains("tv") || speech.contains("television"))){
		    Television.tvActivation();
		}

		if (speech.contains("off") && (speech.contains("tv") || speech.contains("television"))){
			Television.tvDeactivation();
		}

        if (speech.contains("on") && (speech.contains("set top box"))){
            Television.stbActivation();
        }

        if (speech.contains("off") && (speech.contains("set top box"))){
            Television.stbDeactivation();
        }

		if(speech.contains("next channel")){
			Arduino.setData("next channel");
		}
		if(speech.contains("previous channel")){
			Arduino.setData("previous channel");
		}

		if(speech.contains("i want to watch")){
		    Television.wantToWatchRequest(speech);
        }


		if(speech.contains("fuck off")){
			voice.say("you can fuck off yourself");

		}

		if(speech.contains("hello") || speech.contains("good morning")){
			voice.say("hello sir");
		}

		if(speech.contains("plus") || speech.contains("minus") || speech.contains("times")
				|| speech.contains("divided by") || speech.contains("to the power of")){
			double result = Arithmetic.calculation(Arithmetic.getFirstNumber(speech), Arithmetic.getOperator(speech), Arithmetic.getSecondNumber(speech));
			voice.say(String.valueOf(result));
		}
	}

	public static void timeRequest() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String time = dateFormat.format(date);
		System.out.println(dateFormat.format(date));
		voice.say(time.substring(0, time.length() - 2));
	}

	public static void dateRequest() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
	}

	private static void weatherRequests(String speech) throws IOException, JSONException {
		if (speech.contains("temperature")) {
			voice.say(String.valueOf(Weather.getTemperature()) + "degrees celsius");
		} if (speech.contains("weather")) {
			voice.say(Weather.getWeatherDescription());
		} if (speech.contains("wind speed")) {
			voice.say(Weather.getWindSpeed());
		} if (speech.contains("weather") && speech.contains("tomorrow")) {
			voice.say(Weather.getWeatherTomorrow());
		}
	}
}