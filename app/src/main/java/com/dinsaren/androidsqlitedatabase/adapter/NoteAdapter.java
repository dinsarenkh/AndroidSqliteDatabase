package com.dinsaren.androidsqlitedatabase.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dinsaren.androidsqlitedatabase.CreateNoteActivity;
import com.dinsaren.androidsqlitedatabase.R;
import com.dinsaren.androidsqlitedatabase.models.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private final Context context;
    private final List<Note> noteList;
    private final OnClickListener onClickListener;

    public NoteAdapter(Context context, List<Note> noteList, OnClickListener onClickListener) {
        this.context = context;
        this.noteList = noteList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View currentView = LayoutInflater.from(context).inflate(R.layout.note_item_layout, null, false);
        return new NoteViewHolder(currentView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        if (note != null) {
            if (note.getTitle() != null) {
                holder.title.setText(note.getTitle());
            }
            if (note.getDescription() != null) {
                holder.description.setText(note.getDescription());
            }
            holder.itemCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClickItem(view, note);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView description;
        private final CardView itemCard;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            itemCard = itemView.findViewById(R.id.itemCard);
        }
    }

    public interface OnClickListener{
        void onClickItem(View view, Object object);
    }
}
