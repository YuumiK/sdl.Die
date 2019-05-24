package jp.ac.titech.itpro.sdl.die;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, SensorEventListener {
    private final static String TAG = MainActivity.class.getSimpleName();

    private GLSurfaceView glView;
    private SimpleRenderer renderer;

    private Cube cube;
    private Pyramid pyramid;
    private Stand stand;

    private SensorManager manager;
    private Sensor gyroscope;
    private long prevTimestamp=0;

    private SeekBar seekBarX;
    private SeekBar seekBarY;
    private SeekBar seekBarZ;
    private int[] rot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        glView = findViewById(R.id.gl_view);
        seekBarX = findViewById(R.id.seekbar_x);
        seekBarY = findViewById(R.id.seekbar_y);
        seekBarZ = findViewById(R.id.seekbar_z);
        seekBarX.setMax(360);
        seekBarY.setMax(360);
        seekBarZ.setMax(360);
        seekBarX.setOnSeekBarChangeListener(this);
        seekBarY.setOnSeekBarChangeListener(this);
        seekBarZ.setOnSeekBarChangeListener(this);

        rot = new int[3];

        renderer = new SimpleRenderer();
        cube = new Cube();
        pyramid = new Pyramid();
        stand = new Stand();
        renderer.setObj(cube);
        glView.setRenderer(renderer);

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (manager == null) {
            Toast.makeText(this, R.string.toast_no_sensor_manager, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        gyroscope = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (gyroscope == null) {
            Toast.makeText(this, R.string.toast_no_gyroscope, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        glView.onResume();
        manager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        glView.onPause();
        manager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
        case R.id.menu_cube:
            renderer.setObj(cube);
            break;
        case R.id.menu_pyramid:
            renderer.setObj(pyramid);
            break;
        case R.id.menu_stand:
            renderer.setObj(stand);
            break;
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
        case R.id.seekbar_x:
            renderer.rotateObjX(progress);
            break;
        case R.id.seekbar_y:
            renderer.rotateObjY(progress);
            break;
        case R.id.seekbar_z:
            renderer.rotateObjZ(progress);
            break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        rot[0] = seekBarX.getProgress();
        rot[1] = seekBarY.getProgress();
        rot[2] = seekBarZ.getProgress();

        float omegaX = event.values[0];
        float omegaY = event.values[1];
        float omegaZ = event.values[2];

        double passedtime = ((double)(event.timestamp - prevTimestamp) / 1000000000.0);

        rot[0] = (rot[0] + 360 + (int)(omegaX * (float)passedtime * 180.0f / Math.PI))%360;
        rot[1] = (rot[1] + 360 + (int)(omegaY * (float)passedtime * 180.0f / Math.PI))%360;
        rot[2] = (rot[2] + 360 + (int)(omegaZ * (float)passedtime * 180.0f / Math.PI))%360;

        prevTimestamp = event.timestamp;
        Log.d("TAG", "X:"+rot[0]+ "sensor" + omegaX);
        Log.d("TAG", "Y:"+rot[1]+ "sensor" + omegaY);
        Log.d("TAG", "Z:"+rot[2]+ "sensor" + omegaZ);

        seekBarX.setProgress(rot[0]);
        seekBarY.setProgress(rot[1]);
        seekBarZ.setProgress(rot[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "onAccuracyChanged: accuracy=" + accuracy);
    }
}
