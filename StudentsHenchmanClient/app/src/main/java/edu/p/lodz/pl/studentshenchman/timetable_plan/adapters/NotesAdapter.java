package edu.p.lodz.pl.studentshenchman.timetable_plan.adapters;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.p.lodz.pl.studentshenchman.R;
import edu.p.lodz.pl.studentshenchman.database.models.Note;
import edu.p.lodz.pl.studentshenchman.timetable_plan.utils.TimeTableUtils;
import edu.p.lodz.pl.studentshenchman.utils.animation.AnimationHelper;
import edu.p.lodz.pl.studentshenchman.utils.dialog.helper.AlertDialogHelper;
import edu.p.lodz.pl.studentshenchman.utils.dialog.interfaces.AlertDialogCallback;

import static edu.p.lodz.pl.studentshenchman.utils.dialog.helper.UniqueYesNoDialogTAG.DELETE_NOTE_TAG;

/**
 * Created by Michał on 2016-10-20.
 */

public class NotesAdapter extends BaseAdapter implements AlertDialogCallback {
	private static final String TAG = NotesAdapter.class.getName();

	private final Context mContext;
	private final LayoutInflater mInflater;
	private List<Note> mValues;

	public NotesAdapter(Context context, FragmentManager fm) {
		mContext = context;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		AlertDialogHelper.readjustYesNoCallback(fm, this, DELETE_NOTE_TAG);
		init();
	}

	private void init() {
		mValues = new ArrayList<>();
		mValues = TimeTableUtils.loadNotesForCourse(mContext, 1L);
	}

	@Override
	public int getCount() {
		return mValues.size();
	}

	@Override
	public Note getItem(int position) {
		return mValues.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mValues.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (null == convertView) {
			convertView = mInflater.inflate(R.layout.note_item_list, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		Note note = getItem(position);

		viewHolder.noteContent.setText(note.getContent());
		viewHolder.addedDate.setText(DateFormat.getDateFormat(mContext).format(new Date(note.getActivationDate())));
		viewHolder.deleteNote.setOnClickListener(new DelNoteOnClickListener(mContext));

		return convertView;
	}

	private class ViewHolder {
		public TextView noteContent;
		public TextView addedDate;
		public ImageButton deleteNote;

		public ViewHolder(View view) {
			noteContent = (TextView) view.findViewById(R.id.note_item_content);
			addedDate = (TextView) view.findViewById(R.id.note_item_added_date);
			deleteNote = (ImageButton) view.findViewById(R.id.delete_note);
		}
	}

	private class DelNoteOnClickListener implements View.OnClickListener {
		Context mContext;

		public DelNoteOnClickListener(Context context) {
			this.mContext = context;
		}

		@Override
		public void onClick(View v) {
			AnimationHelper.startShockAnimation(v);
			AlertDialogHelper.showYesNoDialog(mContext.getString(R.string.delete_note),
					mContext.getString(R.string.del_note_msg), NotesAdapter.this, DELETE_NOTE_TAG);
		}
	}

	@Override
	public void positiveCallback() {
		Toast.makeText(mContext, "notatka do usuniecia toast yes", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void negativeCallback() {
		Toast.makeText(mContext, "notatka do usuniecia toast no", Toast.LENGTH_SHORT).show();
	}
}
