package co.miniforge.corey.mediatracker.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by corey on 10/20/17.
 */

public class MovieModel extends MediaItem {
    public int myRating;
    public String genre;
    public MovieModel(JSONObject jsonObject) {
        super(jsonObject);
        try {
            this.myRating = jsonObject.getInt("myRating");
            this.genre = jsonObject.getString("genre");
        } catch (JSONException e) {
            Log.e("MOVIECONSTRUCTOR", e.getMessage());
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject result = super.toJson();
        try {
            result.put("myRating", this.myRating);
            result.put("genre", this.genre);
        } catch (JSONException e) {
            Log.e("MOVIEJSON", e.getMessage());
        }
        return result;
    }
}
