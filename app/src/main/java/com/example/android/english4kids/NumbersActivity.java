package com.example.android.english4kids;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {


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
        setContentView(R.layout.activity_numbers);
       // setTitle("Numbers");
        am=(AudioManager) getSystemService(Context.AUDIO_SERVICE);



        final ArrayList<Word> Num=new ArrayList<Word>();
        Num.add(new Word("one","واحد", R.drawable.number_one, R.raw.one));
        Num.add(new Word("two","اثنان", R.drawable.number_two, R.raw.two));
        Num.add(new Word("three","ثلاثة", R.drawable.number_three, R.raw.three));
        Num.add(new Word("four","أربعة", R.drawable.number_four, R.raw.four));
        Num.add(new Word("five","خمسة", R.drawable.number_five, R.raw.five));
        Num.add(new Word("six","ستة", R.drawable.number_six, R.raw.six));
        Num.add(new Word("seven","سبعة", R.drawable.number_seven, R.raw.seven));
        Num.add(new Word("eight","ثمانية", R.drawable.number_eight, R.raw.eight));
        Num.add(new Word("nine","تسعة", R.drawable.number_nine, R.raw.nine));
        Num.add(new Word("ten","عشرة", R.drawable.number_ten, R.raw.ten));

        WordAdapter itemsAdapter = new WordAdapter(this, Num, R.color.colorNum);
        ListView listNums = (ListView) findViewById(R.id.ListNum);
        listNums.setAdapter(itemsAdapter);


        listNums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int result = am.requestAudioFocus(af, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    m = MediaPlayer.create(NumbersActivity.this, Num.get(i).getSong());
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
