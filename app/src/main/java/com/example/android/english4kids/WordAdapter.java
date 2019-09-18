package com.example.android.english4kids;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word>
{

    private int colorID;

    public WordAdapter(@NonNull Activity context, @NonNull ArrayList<Word> objects, int id) {
        super(context, 0, objects);
        colorID=id;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {


        View listItemView = convertView;
        Word curr = getItem(position);
        if(listItemView == null )
        {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

            TextView EnTextView = (TextView) listItemView.findViewById(R.id.en_number);
            EnTextView.setText(curr.getDefault());

            EnTextView.setTextSize(22);
            EnTextView.setTextColor(Color.WHITE);
            EnTextView.setTypeface(null, Typeface.BOLD);

            TextView MiTextView = (TextView) listItemView.findViewById(R.id.mi_number);
            MiTextView.setText(curr.getMiwok());

            MiTextView.setTextSize(22);
            MiTextView.setTextColor(Color.WHITE);

            if(curr.getImageID()!=0)
            {
                ImageView im = (ImageView) listItemView.findViewById(R.id.imaage);
                im.setImageResource(curr.getImageID());
            }
            else
            {
                ImageView im = (ImageView) listItemView.findViewById(R.id.imaage);
                im.setVisibility(View.GONE);
            }


            View textContainer=listItemView.findViewById(R.id.wordslayout);
            int colBack= ContextCompat.getColor(getContext(),colorID);
            textContainer.setBackgroundColor(colBack);
        return listItemView;
    }
}
