package co.miniforge.corey.mediatracker.media_store;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import co.miniforge.corey.mediatracker.model.MediaItem;
import co.miniforge.corey.mediatracker.model.MovieModel;
import co.miniforge.corey.mediatracker.model.TVModel;
import co.miniforge.corey.mediatracker.model.YoutubeModel;

/**
 * Created by corey on 10/15/17.
 */

public class MediaStorageUtil {
    private Context context;

    private final String cacheName = "mediaCache.dat";

    public MediaStorageUtil(Context context){
        this.context = context;
    }

    /**
     * Serializes MediaItems list to a JSON cache
     * @param mediaItems
     */
    public void saveMediaData(List<MediaItem> mediaItems){
        try {
            FileOutputStream outputStream = context.openFileOutput(cacheName, Context.MODE_PRIVATE);

            JSONArray jsonArray = new JSONArray();

            for(MediaItem mediaItem : mediaItems){
                jsonArray.put(mediaItem.toJson());
            }

            JSONObject object = new JSONObject();
            object.put("array", jsonArray);

            outputStream.write(object.toString().getBytes());

            outputStream.close();
        } catch (Exception e){
            Log.e("saveDataError", String.format("There was an error: %s", e.getMessage()));
        }
    }

    /**
     * Converts JSON cache containing MediaItems to native objects
     * @return List of native MediaItem objects from cache
     */
    public List<MediaItem> getMediaDataList(){
        List<MediaItem> mediaList = new LinkedList<>();

        try {
            FileInputStream inputStream = context.openFileInput(cacheName);

            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                // Loop through lines
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }
                // close and set result
                inputStream.close();
                JSONObject serializedMediaItems = new JSONObject(stringBuilder.toString());

                JSONArray jsonArray = serializedMediaItems.getJSONArray("array");

                for(int i = 0; i < jsonArray.length(); i++){
                    MediaItem deserialized = null;
                    JSONObject current = jsonArray.getJSONObject(i);
                    switch (current.getString("type")) {
                        case "TV":
                            deserialized = new TVModel(current);
                            break;
                        case "Movie":
                            deserialized = new MovieModel(current);
                            break;
                        case "YouTube":
                            deserialized = new YoutubeModel(current);
                            break;
                        case "Generic":
                        default:
                            deserialized = new MediaItem(current);
                    }
                    mediaList.add(deserialized);
                }
            }
        } catch (Exception e) {
            Log.e("readDataError", String.format("There was an error: %s", e.getMessage()));
        }

        return mediaList;
    }
}
