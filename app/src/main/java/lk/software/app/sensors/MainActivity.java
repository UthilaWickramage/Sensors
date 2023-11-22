package lk.software.app.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventCallback;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    public static final String TAG = MainActivity.class.getName();
    private SensorManager sensorManager;

    private Sensor accelerometer;
    private Sensor stepCounter;

    private int stepCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions(new String[]{
                Manifest.permission.ACTIVITY_RECOGNITION
        },100);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            //accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            Log.d(TAG, stepCounter.toString());
        }

        if (accelerometer != null) {
            sensorManager.registerListener(MainActivity.this, stepCounter, SensorManager.SENSOR_DELAY_UI);
        }
    }
    TextView textView = findViewById(R.id.textView9);
    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()){
            case  Sensor.TYPE_STEP_COUNTER:
                int x = (int) event.values[0];
                textView.setText(String.valueOf(x));
                break;

            case Sensor.TYPE_PROXIMITY:
                if(event.values[0]<stepCounter.getMaximumRange()){
                    //textView.setText();

                }else{
                   // textView.setText("");

                }
                break;

            case Sensor.TYPE_LIGHT:
                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.screenBrightness = event.values[0]/255.0f;
                getWindow().setAttributes(layoutParams);
                break;

        }
//        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
//            int x = (int) event.values[0];
////            float y = event.values[1];
////            float z = event.values[2];
//
////            double value = Math.sqrt(x * x + y * y + z * z);
////            if (value > 15) {
////                stepCount++;
//
////            }
//        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}