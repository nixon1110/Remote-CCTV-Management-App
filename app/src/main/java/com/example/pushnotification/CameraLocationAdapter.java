package com.example.pushnotification;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CameraLocationAdapter extends RecyclerView.Adapter<CameraLocationAdapter.ViewHolder> {

    private List<String> locations;
    private OnLocationClickListener listener;

    public CameraLocationAdapter(List<String> locations, OnLocationClickListener listener) {
        this.locations = locations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_camera_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String location = locations.get(position);
        holder.bind(location);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            itemView.setOnClickListener(this);
        }

        public void bind(String location) {
            textViewLocation.setText(location);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    String location = locations.get(position);
                    listener.onLocationClick(location);
                }
            }
        }
    }

    public interface OnLocationClickListener {
        void onLocationClick(String location);
    }
}
