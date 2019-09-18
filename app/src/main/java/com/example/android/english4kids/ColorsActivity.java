package com.example.android.english4kids;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {



    private MediaPlayer m;
    private AudioManager am;

    private AudioManager.OnAudioFocusChangeListener af = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                m.pause();
                m.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                m.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };



    private MediaPlayer.OnCompletionListener mcListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (m != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            m.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            m = null;
            am.abandonAudioFocus(af);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);
       // setTitle("Colors");
        am=(AudioManager) getSystemService(Context.AUDIO_SERVICE);


       final ArrayList<Word> Col=new ArrayList<Word>();
        Col.add(new Word("red","أحمر", R.drawable.color_red, R.raw.red));
        Col.add(new Word("green","أخضر", R.drawable.color_green, R.raw.green));
        Col.add(new Word("brown","بُني", R.drawable.color_brown, R.raw.brown));
        Col.add(new Word("gray","رماديّ", R.drawable.color_gray, R.raw.gray));
        Col.add(new Word("black","أسود", R.drawable.color_black, R.raw.black));
        Col.add(new Word("white","أبيض", R.drawable.color_white, R.raw.white));
        Col.add(new Word("dusty yellow","أصفر غامق", R.drawable.color_dusty_yellow, R.raw.dusty_yellow));
        Col.add(new Word("mustard yellow","أصفر", R.drawable.color_mustard_yellow, R.raw.yellow));

        WordAdapter itemsAdapter = new WordAdapter(this, Col, R.color.colorColors);
        ListView listColors = (ListView) findViewById(R.id.ListCol);
        listColors.setAdapter(itemsAdapter);

        listColors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int result = am.requestAudioFocus(af, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    m = MediaPlayer.create(ColorsActivity.this, Col.get(i).getSong());
                   // ImageView im=(ImageView) findViewById(R.id.playimage);
                    //im.setImageResource(R.drawable.baseline_pause_white_48);
                    m.start();
                    m.setOnCompletionListener(mcListener);

                }
            }
        });


        if (getSupportActionBar() != null){

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home) {

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
