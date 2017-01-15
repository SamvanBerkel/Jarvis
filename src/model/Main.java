package model;

import com.fazecast.jSerialComm.SerialPort;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import org.json.JSONException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.fazecast.jSerialComm.SerialPort.getCommPort;
//import static model.DataToArduino.setSpeech;

public class Main {
	private static String speech;

	static SerialPort serialPort;

	static Voice voice;


	// Logger
	private Logger logger = Logger.getLogger(getClass().getName());

	// Variables
	private String result;

	// Threads
	Thread	speechThread;
	Thread	resourcesThread;

	// LiveRecognizer
	private LiveSpeechRecognizer recognizer;

	/**
	 * Constructor
	 */
	public Main() {

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

		//start connection to arduino
		startConnection();

		// Start the Thread
		startSpeechThread();
		startResourcesThread();
		startArduinoDataThread();
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

	public void startArduinoDataThread(){

	}

	/**
	 * Takes a decision based on the given result
	 */
	public void makeDecision(String speech) throws IOException, JSONException {

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
			System.out.println("turning on the tv");
			speech = "tvon";
		}

		if (speech.contains("off") && (speech.contains("tv") || speech.contains("television"))){
			System.out.println("turning on the tv");
			//setSpeech("tvoff");
		}

		// return if user said only one number
		if (array.length != 3)
			return;

	}

	public static void startConnection() {
		speech = "";

		serialPort = getCommPort("COM3");

		serialPort.openPort();
		serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

		if (serialPort.openPort()) {
			System.out.println("port is open");
		} else {
			System.out.println("port is closed");
		}
	}

	public void timeRequest() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String time = dateFormat.format(date);
		System.out.println(dateFormat.format(date));
		voice.say(time.substring(0, time.length() - 2));
	}
	public void dateRequest() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
	}

	private void weatherRequests(String speech) throws IOException, JSONException {
		if (speech.contains("temperature")) {
			voice.say(String.valueOf(Weather.getTemperature()) + "degrees celsius");
		} if (speech.contains("what") && speech.contains("weather")) {
			voice.say(Weather.getWeatherDescription());
		}
	}


	/**
	 * Java Main Application Method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		voice = new Voice("kevin16");

		// // Be sure that the user can't start this application by not giving
		// the
		// // correct entry string
		// if (args.length == 1 && "SPEECH".equalsIgnoreCase(args[0]))
		new Main();
		// else
		// Logger.getLogger(Main.class.getName()).log(Level.WARNING, "Give me
		// the correct entry string..");

	}

}