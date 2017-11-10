package britton.brandyn.notetaking.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import britton.brandyn.notetaking.R;
import britton.brandyn.notetaking.models.NoteItem;
import britton.brandyn.notetaking.ui.activities.MainActivity;

public class NoteArrayAdapter extends ArrayAdapter {

    private Context mContext;
    private ArrayList<NoteItem> notes;

    public NoteArrayAdapter(@NonNull Context context, int resource, ArrayList<NoteItem> data) {
        super(context, resource);
        mContext = context;
        this.notes = data;
    }

    public void setData(ArrayList<NoteItem> data) {
        this.notes = data;
        this.notifyDataSetInvalidated();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return this.notes.get(position);
    }

    @Override
    public int getCount() {
        return this.notes.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            // Inflate the layout
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.note_list_item, parent, false);

            // Init viewholder
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_note_title);
            viewHolder.description = (TextView) convertView.findViewById(R.id.tv_note_description);
            viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.iv_note_thumb);

            // Set tag
            convertView.setTag(viewHolder);

        } else {
            // Get viewholder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the viewholder with note data
        NoteItem noteItem = notes.get(position);
        viewHolder.title.setText(noteItem.getTitle());
        viewHolder.description.setText(noteItem.getDescription().trim());

        return convertView;

    }

    static class ViewHolder {
        TextView title;
        TextView description;
        ImageView thumbnail;
    }
}
