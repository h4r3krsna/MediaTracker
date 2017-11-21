package co.miniforge.corey.mediatracker.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by msree on 11/20/2017.
 */

public class YoutubeModel extends MediaItem {
    public String publisher;
    public int numViews;
    public String publishedDate;

    public YoutubeModel(JSONObject jsonObject) {
        super(jsonObject);
        this.type = MediaItemType.YouTube;
        try {
            this.publisher = jsonObject.getString("publisher");
            this.publishedDate = jsonObject.getString("publishedDate");
            this.numViews = jsonObject.getInt("numViews");
        } catch (JSONException e) {
            Log.e("YOUTUBECONSTRUCTOR", e.getMessage());
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject result = super.toJson();
        try {
            result.put("publisher", this.publisher);
            result.put("publishedDate", this.publishedDate);
            result.put("numViews", this.numViews);
        } catch (JSONException e) {
            Log.e("YOUTUBEJSON", e.getMessage());
        }
        return result;
    }
}
