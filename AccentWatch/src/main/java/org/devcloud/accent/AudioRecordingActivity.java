package org.devcloud.accent;

import java.io.File;
import java.io.IOException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AudioRecordingActivity extends Activity {
  private static final String AUDIO_RECORDER_FILE_EXT_3GP = "3gp";
  private static final String AUDIO_RECORDER_FILE_EXT_MP4 = "mp4";
  private static final String AUDIO_RECORDER_FOLDER = "AccentWatch";

  private MediaRecorder recorder = null;
  private int currentFormat = 0;
  private int output_formats[] = {
    MediaRecorder.OutputFormat.MPEG_4,
    MediaRecorder.OutputFormat.THREE_GPP
  };
  private String file_exts[] = {
    AUDIO_RECORDER_FILE_EXT_MP4,
    AUDIO_RECORDER_FILE_EXT_3GP
  };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    setButtonHandlers();
    enableButtons(false);
  }

  private void setButtonHandlers() {
    ((Button) findViewById(R.id.btnStart)).setOnClickListener(btnClick);
    ((Button) findViewById(R.id.btnStop)).setOnClickListener(btnClick);
  }

  private void enableButton(int id, boolean isEnable) {
    ((Button) findViewById(id)).setEnabled(isEnable);
  }

  private void enableButtons(boolean isRecording) {
    enableButton(R.id.btnStart, !isRecording);
    enableButton(R.id.btnStop, isRecording);
  }

  private String getFilename() {
    String filepath = Environment.getExternalStorageDirectory().getPath();
    File file = new File(filepath, AUDIO_RECORDER_FOLDER);
    String filename = String.format("%s.%s", System.currentTimeMillis(), file_exts[currentFormat]);

    if (!file.exists()) {
      file.mkdirs();
    }

    return String.format("%s/%s", file.getAbsolutePath(), filename);
  }

  private void startRecording() {
    String filename = getFilename();
    recorder = new MediaRecorder();

    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    recorder.setOutputFormat(output_formats[currentFormat]);
    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    recorder.setOutputFile(filename);

    recorder.setOnErrorListener(errorListener);
    recorder.setOnInfoListener(infoListener);

    try {
      recorder.prepare();
      recorder.start();
      Log.e(this.getLocalClassName(), "Started recording to: " + filename);
    } catch (IllegalStateException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void stopRecording() {
    if (null != recorder) {
      recorder.stop();
      recorder.reset();
      recorder.release();

      recorder = null;
    }
  }

  private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
    @Override
    public void onError(MediaRecorder mr, int what, int extra) {
      Toast.makeText(AudioRecordingActivity.this, "Error: " + what + ", " + extra, Toast.LENGTH_SHORT).show();
    }
  };

  private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {
      Toast.makeText(AudioRecordingActivity.this, "Warning: " + what + ", " + extra, Toast.LENGTH_SHORT).show();
    }
  };

  private View.OnClickListener btnClick = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      switch (v.getId()) {
        case R.id.btnStart:
          Toast.makeText(
              AudioRecordingActivity.this,
              "Start Recording",
              Toast.LENGTH_SHORT).show();
          enableButtons(true);
          startRecording();
          break;
        case R.id.btnStop:
          Toast.makeText(
              AudioRecordingActivity.this,
              "Stop Recording",
              Toast.LENGTH_SHORT).show();
          enableButtons(false);
          stopRecording();
          break;
      }
    }
  };
}
