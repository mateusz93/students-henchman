package edu.p.lodz.pl.studentshenchman.workers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import cdm.TeacherRS;
import edu.p.lodz.pl.studentshenchman.database.DatabaseHelper;
import edu.p.lodz.pl.studentshenchman.database.models.Teacher;
import edu.p.lodz.pl.studentshenchman.workers.endpoints.TeachersEndpoints;
import edu.p.lodz.pl.studentshenchman.workers.factories.ServiceFactory;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by Michał on 2016-11-20.
 */

public class DownloadTeachersWorker extends AbstractWorker<Response<TeacherRS>> {
	private static final String TAG = DownloadTeachersWorker.class.getName();

	public DownloadTeachersWorker(Context context, Bundle bundle) {
		super(context, bundle);
	}

	@Override
	public Subscription run() {
		Toast.makeText(mContext, "Starting download teachers", Toast.LENGTH_SHORT).show();
		TeachersEndpoints settingsEndpoints = ServiceFactory.produceService(TeachersEndpoints.class, false);
		Observable<Response<TeacherRS>> call = settingsEndpoints.getTeachers();

		Subscription subscription = call.subscribeOn(Schedulers.newThread())
				.observeOn(Schedulers.newThread())
				.subscribe(this);

		return subscription;
	}

	@Override
	public void onCompleted() {
		Log.i(TAG, "Teachers downloaded successfully");
		notifyTaskFinished(FinishedWorkerStatus.SUCCESS);
	}

	@Override
	public void onError(Throwable e) {
		onError(mContext, e);
		notifyTaskFinished(FinishedWorkerStatus.FAIL);
	}

	@Override
	public void onNext(Response<TeacherRS> teacherRS) {
		if (teacherRS.isSuccessful()) {
			Log.i(TAG, "Saving teachers downloaded from server");
			SQLiteDatabase db = DatabaseHelper.getInstance(mContext).getWritableDatabase();
			deleteOldTeachers(db);
			saveNewTeachers(db, teacherRS.body().getTeachers());
		} else
			onError(new HttpException(teacherRS));
	}

	private void saveNewTeachers(SQLiteDatabase db, List<model.Teacher> teachers) {
		for (model.Teacher teacherDto : teachers)
			db.insert(Teacher.TABLE_NAME, null, Teacher.fromDTO2CV(teacherDto));
	}

	private void deleteOldTeachers(SQLiteDatabase db) {
		db.delete(Teacher.TABLE_NAME, null, null);
	}

}
