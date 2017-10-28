package co.miniforge.corey.mediatracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import co.miniforge.corey.mediatracker.model.MediaItem;
import co.miniforge.corey.mediatracker.MyListActivity;

/**
 * This activity will display the contents of a media item and allow the user to update the contents
 * of the item. When the user clicks the save button, the activity should create an intent that goes
 * back to MyListActivity and puts the MediaItem into the intent (If you are stuck on that, read through
 * the code in MyListActivity)
 */
public class MediaItemDetailActivity extends AppCompatActivity {
    public static final String MEDIAITEMDETAILACTIVITY = "MEDIAITEMDETAILACTIVITY";
    TextView lblTitle, lblDescription, lblURL;
    EditText txtTitle, txtDescription, txtURL;
    Button btnSubmit;
    private JSONObject JSONMediaDetail;
    private MediaItem mediaItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_item_detail);

        locateViews();

        receiveMediaDetail();

        bindFunctionality();
    }

    private void locateViews() {
        lblTitle = (TextView) findViewById(R.id.lblTitle);
        lblDescription = (TextView) findViewById(R.id.lblDescription);
        lblURL = (TextView) findViewById(R.id.lblURL);

        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        txtURL = (EditText) findViewById(R.id.txtURL);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
    }

    private void receiveMediaDetail() {
        String receivedMediaDetail = getIntent().getExtras().getString(MyListActivity.mediaExtra);
        Log.d(MEDIAITEMDETAILACTIVITY, receivedMediaDetail);
        mediaItem = null;

        try {
            JSONMediaDetail = new JSONObject(receivedMediaDetail);

            lblTitle.setText("Title: " + JSONMediaDetail.get("title"));
            lblDescription.setText("Description: " + JSONMediaDetail.get("description"));
            lblURL.setText("URL: " + JSONMediaDetail.get("url"));

            mediaItem = new MediaItem(JSONMediaDetail);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void bindFunctionality() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtTitle.getText().toString() != "")
                    mediaItem.title = txtTitle.getText().toString();
                if (txtDescription.getText().toString() != "")
                    mediaItem.description = txtDescription.getText().toString();
                if (txtURL.getText().toString() != "")
                    mediaItem.url = txtURL.getText().toString();

                Log.d(MEDIAITEMDETAILACTIVITY, mediaItem.toJson().toString());

                Intent listIntent = new Intent(getApplicationContext(), MyListActivity.class);
                listIntent.putExtra(MyListActivity.mediaExtra, mediaItem.toJson().toString());

                startActivity(listIntent);
            }
        });
    }
}
