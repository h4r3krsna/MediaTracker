package co.miniforge.corey.mediatracker.media_recycler;

import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import co.miniforge.corey.mediatracker.R;
import co.miniforge.corey.mediatracker.model.MediaItem;

/**
 * Created by corey on 10/15/17.
 */

public class MediaRecyclerAdapter extends RecyclerView.Adapter {
    private List<MediaItem> mediaItems = new LinkedList<>();

    @Override
    public int getItemViewType(int position) {
        MediaItem mediaItem = mediaItems.get(position);
        int mediaItemType = -1;
        switch (mediaItem.type) {
            case TV:
                mediaItemType = 1;
                break;
            case Movie:
                mediaItemType = 2;
                break;
            case YouTube:
                mediaItemType = 3;
                break;
            case Generic:
            default:
                mediaItemType = 0;
        }
        return mediaItemType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutType = -1;
        switch (viewType) {
            case 1:
                layoutType = R.layout.tv_item;
                break;
            case 2:
                layoutType = R.layout.movie_item;
                break;
            case 3:
                layoutType = R.layout.youtube_item;
                break;
            case 0:
            default:
                layoutType = R.layout.media_item;
                break;
        }
        View inflated = LayoutInflater.from(parent.getContext()).inflate(layoutType, parent, false);
        return new MediaViewHolder(inflated);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MediaViewHolder)holder).bindData(mediaItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mediaItems.size();
    }

    public void updateList(List<MediaItem> mediaItems){
        this.mediaItems = mediaItems;
        notifyDataSetChanged();
    }
}
