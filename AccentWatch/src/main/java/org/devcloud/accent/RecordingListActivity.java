package org.devcloud.accent;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RecordingListActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
        .add(R.id.container, new RecordingListFragment())
        .commit();
    }
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.list, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public static class RecordingListFragment extends ListFragment {
    private List<String> fileList = new ArrayList<String>();
    public RecordingListFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_list, container, false);
      File[] files = AudioRecordingActivity.getDirectory().listFiles();
      fileList.clear();
      for (File file : files) {
        fileList.add(file.getPath());
      }

      ArrayAdapter<String> directoryList;
      directoryList = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, fileList);
      setListAdapter(directoryList);
      return rootView;
    }

    @Override
    public void onListItemClick (ListView l, View v, int position, long id) {
      Log.d("RecordingList", "Tapped " + fileList.get((int) id));

      try {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(new File(fileList.get((int) id)).getAbsolutePath());
        mediaPlayer.prepare();
        mediaPlayer.start();
      } catch (IOException e) {
        Log.e("RecordingListActivity", "prepare() failed");
      }
    }
  }
}
