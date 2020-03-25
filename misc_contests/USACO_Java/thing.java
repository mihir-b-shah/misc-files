package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;
import android.widget.Toast;

import com.google.android.gms.vision.text.TextBlock;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.Block;
import com.google.api.services.vision.v1.model.BoundingPoly;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.LocalizedObjectAnnotation;
import com.google.api.services.vision.v1.model.Page;
import com.google.api.services.vision.v1.model.Paragraph;
import com.google.api.services.vision.v1.model.Symbol;
import com.google.api.services.vision.v1.model.TextAnnotation;
import com.google.api.services.vision.v1.model.Vertex;
import com.google.api.services.vision.v1.model.Word;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Queue;

public class MainActivity extends AppCompatActivity implements SensorEventListener, AsyncResponse<AnnotateImageResponse> {

    TextToSpeech tts;
    public static final int SPEECH_REQUEST_CODE = 0;
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_TAKE_PHOTO = 1;
    private String wordCurr;
    private String secPath;
    private static String spkText;
    private String mCurrentPhotoPath;
    private SensorManager mSensorManager;
    private SparseArray<TextBlock> arr;
    private String[] tb_values;
    private Sensor[] sensors = new Sensor[3];
    private float[] magData;
    private float[] accData;
    public static int WIDTH = 1024;
    public static int HEIGHT = 768;
    private double deflect_X;
    private double deflect_Y;
    private static final double VIEW_ANGLE = Math.acos((35.5 * 35.5 - 57.25 * 57.25 - 56.25 * 56.25) / (-2 * 57.25 * 56.25));
    private static final double NEGLIGIBLE_ANGLE = 0.01;
    private boolean runnable = true;
    public static final int SHIFT = 24;
    private long updateTime = 0;
    private int nlp_result;
    private Bitmap b;
    private TextAnnotation vision_result;
    private List<AnnotateImageResponse> list;
    private ArrayList<ArrayList<String>> words;
    private List<AnnotateImageResponse> list_1;
    private long time = 0;
    private float[] stp;
    private ArrayList<Annotation> queue;
    private Iterator<Annotation> iter;
    private boolean first = false;
	private float[] priorData;
	private double omegaPrior;
	private double X_comp;
	private double Y_comp;
	private double d_walk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR)
                    tts.setLanguage(Locale.US);
            }
        });

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensors[0] = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensors[1] = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensors[2] = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        for(Sensor s: sensors)
            mSensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);

    }

    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            spkText = results.get(0);
            super.onActivityResult(requestCode, resultCode, data);
            dispatchTakePictureIntent();
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            System.out.println("Line 140: " + mCurrentPhotoPath);
            System.out.println("Line 140: " + secPath);
            CallAPI asynctask = new CallAPI();
            asynctask.deleg = this;
            asynctask.execute(mCurrentPhotoPath, BitmapFactory.decodeFile(mCurrentPhotoPath));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle b) {
        super.onSaveInstanceState(b);
        b.putString("path", mCurrentPhotoPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle saved) {
        super.onRestoreInstanceState(saved);
        mCurrentPhotoPath = saved.getString("path");
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                System.out.println(153);
                photoFile = createImageFile();
                System.out.println(mCurrentPhotoPath);
            } catch (IOException ex) {

            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                System.out.println("Line 169: " + mCurrentPhotoPath);
                secPath = new String(mCurrentPhotoPath);
                System.out.println("Line 169: " + secPath);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        String imageFileName = "JPEG_test";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        System.out.println(181);
        System.out.println(image);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        System.out.println(mCurrentPhotoPath);

        return image;
    }

    @Override
    protected void onPause() {
        super.onPause();

        for (int i = 0; i < sensors.length; i++) {
            mSensorManager.unregisterListener(this, sensors[i]);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        for(Sensor s: sensors)
            mSensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void respond(View v) {
        // Convert commands to text
        // Native command in Java
        displaySpeechRecognizer();

        // Take the photo
    }

    // https://code.tutsplus.com/tutorials/how-to-use-the-google-cloud-vision-api-in-android-apps--cms-29009

    public void tempContinueProgramV2(List<AnnotateImageResponse> annotations) {

        // Need to multithread
        // Doing too much work on the main thread

        System.out.println("TIME 214: " + (time - System.currentTimeMillis()));
        time = System.currentTimeMillis();

        List<Page> pages;
        BoundingPoly opt = null;
        String descr;
        float confidence = 0.0f;
        queue = new ArrayList<>();

        System.out.println(227);

        System.out.println("TIME 225: " + (time - System.currentTimeMillis()));
        time = System.currentTimeMillis();

        if (!annotations.isEmpty()) {
            for (AnnotateImageResponse resp : annotations) {

                List<LocalizedObjectAnnotation> objects = resp.getLocalizedObjectAnnotations();

                if (objects != null) {

                    opt = objects.get((int) 0).getBoundingPoly();

                    for (LocalizedObjectAnnotation ea : objects) {
                        String name = ea.getName();
                        float score = ea.getScore();
                        System.out.println(name);
                        queue.add(new Annotation("o", name, score, ea.getBoundingPoly()));

                        float conf_loc = genScore(score, compare(name, spkText));

                        if (conf_loc > confidence && compare(name, spkText) == 1.0) {
                            opt = ea.getBoundingPoly();
                            descr = name;
                            confidence = conf_loc;
                        }
                    }
                }

                List<EntityAnnotation> logos = resp.getLogoAnnotations();

                if (logos != null) {

                    opt = logos.get(0).getBoundingPoly();

                    for (EntityAnnotation ea : logos) {
                        String name = ea.getDescription();
                        float score = ea.getScore();
                        System.out.println(name);
                        queue.add(new Annotation("l", name, score, ea.getBoundingPoly()));

                        float conf_loc = genScore(score, compare(name, spkText));

                        if (conf_loc > confidence && compare(name, spkText) == 1.0) {
                            opt = ea.getBoundingPoly();
                            descr = name;
                            confidence = conf_loc;
                        }
                    }
                }

                TextAnnotation ta = resp.getFullTextAnnotation();

                if (ta != null) {
                    pages = ta.getPages();
                    List<Block> blocks;

                    for (Page pg : pages) {

                        blocks = pg.getBlocks();
                        List<Paragraph> paragraphs;

                        for (Block b : blocks) {

                            paragraphs = b.getParagraphs();
                            List<Word> words;

                            for (Paragraph p : paragraphs) {

                                words = p.getWords();
                                List<Symbol> symbols;
                                StringBuilder text = new StringBuilder();

                                for (Word w : words) {
                                    symbols = w.getSymbols();

                                    for (Symbol s : symbols) {
                                        text.append(s.getText());
                                    }

                                    text.append(' ');
                                }

                                double val = compare(text.toString(), spkText);
                                System.out.println(text);
                                queue.add(new Annotation("t", text.toString(), (float) val, p.getBoundingBox()));
                                //getSuggestions(text.toString());
                                System.out.println(spkText);

                                if (Double.compare(val, confidence) > 0) {
                                    confidence = (float) val;
                                    opt = p.getBoundingBox();
                                    descr = text.toString();
                                }

                            }

                        }
                    }
                }

                int Xmin = WIDTH;
                int Xmax = 0;

                
                for (Vertex v : opt.getVertices()) {
                    if (v.getX() > Xmax)
                        Xmax = v.getX();
                    if (v.getX() < Xmin)
                        Xmin = v.getX();
                }
					
                deflect_X = (double) (Xmax + Xmin) / 2;

                System.out.println("TIME 346: " + (time - System.currentTimeMillis()));
                time = System.currentTimeMillis();
                iter = queue.iterator();
                StringBuilder out = new StringBuilder();

                for(Annotation a: queue)
                    out.append(a.toString());

                tts.speak("DONE", TextToSpeech.QUEUE_FLUSH, null, "out");

                System.out.println(out);
                tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

                    @Override
                    public void onStart(String utteranceId) {

                    }

                    @Override
                    public void onDone(String utteranceId) {
                        navigate();
                    }

                    @Override
                    public void onError(String utteranceId) {

                    }
                });

                if(queue.size() == 0)
                    Toast.makeText(getApplicationContext(), "NOTHING FOUND", Toast.LENGTH_LONG).show();
            }
        }
    }
    private float genScore(float score, float compare) {
        return score*(float)compare;
    }

    private float compare(String text, String spkText) {

        String ref = text.length() > spkText.length() ? text : spkText;
        String other = text.equals(ref) ? spkText : text;

        float count = 0;

        for(int i = 0; i<ref.length(); i++) {
            if(other.contains(ref.substring(i,i+1)))
                count++;
        }

        return (float) Math.pow(count/ref.length(), 0.25);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float[] acc = new float[3];
        float[] mag = new float[3];
        float[] stpLoc = new float[3];
        int which = -1;


        if (runnable) {

            int type = event.sensor.getType();

            if (type == Sensor.TYPE_ACCELEROMETER) {
                acc = event.values.clone();
                which = 0;
            } else if (type == Sensor.TYPE_MAGNETIC_FIELD) {
                mag = event.values.clone();
                which = 1;
            } else if (type == Sensor.TYPE_STEP_COUNTER) {
                stp = event.values.clone();
                which = 2;
            }


            long t = System.currentTimeMillis();
			
            // https://code.tutsplus.com/tutorials/using-the-accelerometer-on-android--mobile-22125
            if (t - updateTime > 100) {
                long diff = t - updateTime;
                updateTime = t;

                if (which == 0)
                    accData = acc.clone();
                else if (which == 1)
                    magData = mag.clone();
                else if (which == 2) {
                    stp = stpLoc.clone();
					if(d_walk*stp > d_new)
						tts.speak("YOU HAVE ARRIVED. PLEASE CLOSE THE APP." + omega, TextToSpeech.QUEUE_FLUSH, null, "out");
				}
            }

            if(first)
                navTwo();
			if(second)
				navThree();
        }
    }

    /**
     * NAVIGATION INFORMATION
     * 
     * 1. Location within the photo frame
     * 2. Azimuth, pitch, and roll
     * 
     * AZIMUTH: Angle between device y axis, along long side of the phone and the magnetic north pole
     * PITCH: Angle between a plane parallel to the device screen and one parallel to the ground
     * ROLL: Angle between a plane parallel to device screen and one normal to the ground
     */
 
	public void obstacle_manage(double X, double Y) {
		double R = (Math.pow(X, 2) + Math.pow(Y, 2))/(2*Y);
		String form = String.format("WALK %f meters in your current direction, %f perpendicular to your current direction.", R-X, R-Y);
		tts.speak(form, TextToSpeech.QUEUE_FLUSH, null, "out");
		omega = Math.PI/2 - omega;
		
		x_comp += (R-X)*Math.cos(omega);
		y_comp += (R-Y)*Math.sin(omega);
	} 
	 
	public void navThree() {
		final double HEIGHT = 1.6764; // For me
		d_walk = 0.46*HEIGHT*stp;
		
		omegaPrior = omega;
		navTwo();
		
		double d_new = d_walk*Math.sin(omegaPrior)/Math.sin(omega - omegaPrior);
		
		X_comp = d_new*Math.sin(omega);
		Y_comp = d_new*Math.cos(omega);
	} 
	 
    public void navTwo() {
        float[] rotationMatrix = new float[9];
        boolean canRotate = SensorManager.getRotationMatrix(rotationMatrix, null, accData, magData);

        float[] orientation_angles = new float[3];

        if (canRotate) {
            SensorManager.getOrientation(rotationMatrix, orientation_angles);
        }

        // Add adaptive functionality

        float AZIMUTH = orientation_angles[0];
        float PITCH = orientation_angles[1];
        float ROLL = orientation_angles[2];

        System.out.printf("AZIMUTH: %f, PITCH: %f,\\  ROLL: %f", AZIMUTH, PITCH, ROLL);

        // Find the normal vector of the plane
        // Vector is <-tan(PITCH), 1> in 2D space

        double[] normal_vector = new double[2];

        normal_vector[0] = -Math.tan(PITCH) * Math.cos(ROLL);
        normal_vector[1] = -Math.tan(PITCH) * Math.sin(ROLL);

        double calib_pitch = 0;
        double calib_roll = 0;
        double calib_azimuth = 0;
        double[] calib_vector = new double[]{0,Math.PI/2};

        deflect_X *= (Math.cos(AZIMUTH)*Math.cos(ROLL));
        deflect_X *= Math.cos(AZIMUTH);
        deflect_X *= Math.cos(PITCH/2);

        double alpha = Math.atan(deflect_X * Math.tan(VIEW_ANGLE / 2));
        double theta = VIEW_ANGLE;
        double phi = PITCH + calib_pitch;

        double omega = phi + theta/2 + alpha;
		tts.speak("ANGLE IS " + omega, TextToSpeech.QUEUE_FLUSH, null, "out");
		
		priorData = orientation_angles.clone();
		
		second = true;
		runnable = true;
    }

    public void navigate() {

        // https://google-developer-training.gitbooks.io/android-developer-advanced-course-concepts/content/unit-1-expand-the-user-experience/lesson-3-sensors/3-2-c-motion-and-position-sensors/3-2-c-motion-and-position-sensors.html#devicerotation
        System.out.println("GOT TO NAVIGATE");
        runnable = true;
		first = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Unused
    }

    @Override
    public void finish(Collection<AnnotateImageResponse> list) {

        System.out.println(-1*time + (time = System.currentTimeMillis()));
        list_1 = (List<AnnotateImageResponse>) list;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                System.out.println(-1*time + (time = System.currentTimeMillis()));
                tempContinueProgramV2(list_1);
            }
        });
    }

    // https://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
    private class CallAPI extends AsyncTask<Object, Integer, String> {
        // Do the long-running work in here

        private AsyncResponse deleg = null;
        private List<AnnotateImageResponse> annotations;
        private List<AnnotateImageResponse> second;

        @Override
        protected String doInBackground(Object... params) {

            System.out.println(500);

            System.out.println("BEFORE OPEN: 0");
            time = System.currentTimeMillis();

            Vision.Builder vb = new Vision.Builder(new NetHttpTransport(), new AndroidJsonFactory(), null);
            vb.setVisionRequestInitializer(new VisionRequestInitializer(    "AIzaSyDnCi0AaR5qNAc6S5gkGWfpn0XwgHJg1zc"));
            vb.setApplicationName("MyFirstApp");

            System.out.println("LINE 543: " + (System.currentTimeMillis() - time));
            time = System.currentTimeMillis();

            Vision vision = vb.build();
            Feature df = new Feature();
            df.setType("TEXT_DETECTION");
            Feature df2 = new Feature();
            df2.setType("LOGO_DETECTION");
            Feature df3 = new Feature();
            df3.setType("OBJECT_LOCALIZATION");

                /*
                params[0] = absolute path of the photo
                params[1] = bitmap
                 */

            try {

                Bitmap b = (Bitmap) params[1];

                System.out.println(mCurrentPhotoPath);
                System.out.println(b);

                b = Bitmap.createScaledBitmap((Bitmap) b, 1920, 1080, true);

                System.out.println("BEFORE BITMAP COMPRESS: " + (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                byte[] data = stream.toByteArray();
                stream.close();

                System.out.println("AFTER BITMAP COMPRESS: " + (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();

                Image img = new Image();
                img.encodeContent(data);
                AnnotateImageRequest request = new AnnotateImageRequest();

                System.out.println("TIME BEFORE SET IMG: " + (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();

                request.setImage(img);
                request.setFeatures(Arrays.asList(df, df2, df3));
                BatchAnnotateImagesRequest batchRequest = new BatchAnnotateImagesRequest();
                batchRequest.setRequests(Arrays.asList(request));

                System.out.println("TIME AFTER SET IMG: " + (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();

                Vision.Images vi = vision.images();
                Vision.Images.Annotate via = vi.annotate(batchRequest);

                System.out.println("TIME BEFORE REQUEST: " + (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();

                BatchAnnotateImagesResponse batchResponse = via.execute();

                System.out.println("TIME AFTER EXECUTE: " + (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();

                annotations = batchResponse.getResponses();

                System.out.println("TIME AFTER GET RESPONSES: " + (System.currentTimeMillis() - time));
                time = System.currentTimeMillis();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return ""; // change
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println("TIME 607: " + (time - System.currentTimeMillis()));
            time = System.currentTimeMillis();
            deleg.finish(annotations);
        }
    }
}