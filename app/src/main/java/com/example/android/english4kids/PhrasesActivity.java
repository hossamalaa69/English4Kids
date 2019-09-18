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

public class PhrasesActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_phrases);
     //  setTitle("Phrases");

        am=(AudioManager) getSystemService(Context.AUDIO_SERVICE);



        final ArrayList<Word> Phrases=new ArrayList<Word>();
        Phrases.add(new Word("Where are you going?","أين تذهب؟", R.raw.where_are_you_going));
        Phrases.add(new Word("What is your name?","ما اسمك؟", R.raw.what_is_your_name));
        Phrases.add(new Word("My name is..","اسمي...", R.raw.my_name_is));
        Phrases.add(new Word("How are you feeling?","كيف حالك؟", R.raw.how_are_you_feeling));
        Phrases.add(new Word("I’m feeling good.","أنا بخير.", R.raw.am_feeling_good));
        Phrases.add(new Word("Are you coming?","هل أنت قادم؟", R.raw.are_you_coming));
        Phrases.add(new Word("Yes, I’m coming.","نعم, أنا قادم.", R.raw.yes_am_coming));
        Phrases.add(new Word("I’m coming.","أنا قادم.", R.raw.am_coming));
        Phrases.add(new Word("Let’s go.","هيا لنذهب", R.raw.lets_go));
        Phrases.add(new Word("Come here.","تعالى هنا.", R.raw.come_here));

        WordAdapter itemsAdapter = new WordAdapter(this, Phrases, R.color.colorPhrases);
        ListView listPhrases = (ListView) findViewById(R.id.ListPhra);
        listPhrases.setAdapter(itemsAdapter);

        listPhrases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                int result=am.requestAudioFocus(af,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    m = MediaPlayer.create(PhrasesActivity.this, Phrases.get(i).getSong());
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
