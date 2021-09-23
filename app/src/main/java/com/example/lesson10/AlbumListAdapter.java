package com.example.lesson10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumListViewHolder> {

    private List<Album> albumList;
    private Context context;

    public AlbumListAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Album> list) {
        this.albumList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public AlbumListViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_layout, parent, false);
        return new AlbumListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AlbumListAdapter.AlbumListViewHolder holder, int position) {
        Album album = albumList.get(position);

        if (album == null) {
            return;
        } else {
            String strId = String.valueOf(album.getId());
            holder.tvId.setText(strId);
            holder.tvTitle.setText(album.getTitle());
            Picasso.get().load(album.getUrl()).fit().into(holder.imgAlbum);
        }
    }

    @Override
    public int getItemCount() {
        if (albumList != null) {
            return albumList.size();
        } else {
            return 0;
        }
    }

    public class AlbumListViewHolder extends RecyclerView.ViewHolder {

        private TextView tvId;
        private TextView tvTitle;
        private ImageView imgAlbum;

        public AlbumListViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            imgAlbum = itemView.findViewById(R.id.img_album);
            tvId = itemView.findViewById(R.id.tv_id);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
