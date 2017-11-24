package co.miniforge.corey.mediatracker;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

/**
 * This activity will display the contents of a media item and allow the user to update the contents
 * of the item. When the user clicks the save button, the activity should create an intent that goes
 * back to MyListActivity and puts the MediaItem into the intent (If you are stuck on that, read through
 * the code in MyListActivity)
 */
public class MediaItemDetailActivity extends AppCompatActivity {
    private JSONObject JSONMediaDetail;
    private MediaItem mediaItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_item_detail);

        mediaItem = parseMediaItemFromIntent();

        createFragmentForMediaItemType(mediaItem);
    }

    public void createFragmentForMediaItemType(MediaItem item) {
        Fragment fragment = null;

        switch(item.type){
            case Movie:
                break;
            case TV:
                break;
            case YouTube:
                break;
            case Generic:
            default:
                fragment = MediaItemDetailFragment.create(item);
                break;

        }
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private MediaItem parseMediaItemFromIntent() {
        String receivedMediaDetail = getIntent().getExtras().getString(MyListActivity.mediaExtra);
        MediaItem result = null;

        try {
            JSONMediaDetail = new JSONObject(receivedMediaDetail);
            result = new MediaItem(JSONMediaDetail);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

}
