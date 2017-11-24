package co.miniforge.corey.mediatracker;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import co.miniforge.corey.mediatracker.model.MediaItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class MediaItemDetailFragment extends Fragment {

    ViewGroup container;

    TextView lblTitle, lblDescription, lblURL;
    EditText txtTitle, txtDescription, txtURL;
    Button btnSubmit;

    private MediaItem mediaItem;

    public static MediaItemDetailFragment create(MediaItem item) {
        MediaItemDetailFragment fragment = new MediaItemDetailFragment();
        fragment.mediaItem = item;
        return fragment;
    }

    public MediaItemDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.container = container;

        // Inflate the layout for this fragment
        View inflated = inflater.inflate(R.layout.fragment_media_item_detail, container, false);

        locateViews(inflated);
        return inflated;
    }

    private void locateViews(View inflated) {
        lblTitle = inflated.findViewById(R.id.lblTitle);
        lblDescription = inflated.findViewById(R.id.lblDescription);
        lblURL = inflated.findViewById(R.id.lblURL);

        txtTitle = inflated.findViewById(R.id.txtTitle);
        txtDescription = inflated.findViewById(R.id.txtDescription);
        txtURL = inflated.findViewById(R.id.txtURL);

        btnSubmit = inflated.findViewById(R.id.btnSubmit);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        bindData();
    }

    private void bindData() {
        if (mediaItem != null) {
            lblTitle.setText("Current Title: " + mediaItem.title);
            lblDescription.setText("Current Description: " + mediaItem.description);
            lblURL.setText("Current URL: " + mediaItem.url);


            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!txtTitle.getText().toString().equals(""))
                        mediaItem.title = txtTitle.getText().toString();
                    if (!txtDescription.getText().toString().equals(""))
                        mediaItem.description = txtDescription.getText().toString();
                    if (!txtURL.getText().toString().equals(""))
                        mediaItem.url = txtURL.getText().toString();



//                    Intent listIntent = new Intent(getApplicationContext(), MyListActivity.class);
                    Intent listIntent = new Intent(container.getContext(), MyListActivity.class);


//                    Intent listIntent = new Intent(null, MyListActivity.class);
                    listIntent.putExtra(MyListActivity.mediaExtra, mediaItem.toJson().toString());

                    startActivity(listIntent);
                }
            });
        }
    }

}
