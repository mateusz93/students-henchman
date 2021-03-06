package edu.p.lodz.pl.studentshenchman.workers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import cdm.PreferencesRQ;
import edu.p.lodz.pl.studentshenchman.workers.endpoints.UserEndpoints;
import edu.p.lodz.pl.studentshenchman.workers.factories.ServiceFactory;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by Michał on 2016-11-21.
 */

public class SetUserPreferencesWorker extends AbstractWorker<Response<Void>> {
	private static final String TAG = SetUserPreferencesWorker.class.getName();

	public static final String EXTERNAL_DEPARTMENT_ID = ":external_department_id";
	public static final String EXTERNAL_FIELD_ID = ":external_field_id";
	public static final String EXTERNAL_GROUPS_IDS = ":external_groups_ids";
	public static final String TERM = ":term";
	public static final String DEGREE = ":degree";

	public SetUserPreferencesWorker(Context context, Bundle bundle) {
		super(context, bundle);
	}

	@Override
	public Subscription run() {
		PreferencesRQ preferencesRQ = createPreferencesRQ();
		UserEndpoints userEndpoints = ServiceFactory.produceService(UserEndpoints.class, false);
		Observable<Response<Void>> call = userEndpoints.setUserPreferences(preferencesRQ);

		Subscription subscription = call.subscribeOn(Schedulers.newThread())
				.observeOn(Schedulers.newThread())
				.subscribe(this);

		return subscription;
	}

	private PreferencesRQ createPreferencesRQ() {
		PreferencesRQ preferencesRQ = new PreferencesRQ();

		preferencesRQ.setDepartmentId(mBundle.getLong(EXTERNAL_DEPARTMENT_ID));
		preferencesRQ.setFieldId(mBundle.getLong(EXTERNAL_FIELD_ID));
		preferencesRQ.setDeanGroupIds(fromStringToLongList(mBundle.getString(EXTERNAL_GROUPS_IDS), ";"));
		preferencesRQ.setTerm(mBundle.getInt(TERM));
		preferencesRQ.setDegree(mBundle.getInt(DEGREE));

		return preferencesRQ;
	}

	@Override
	public void onCompleted() {
		Log.i(TAG, "User preferences uploaded on server successfully");
		notifyTaskFinished(FinishedWorkerStatus.SUCCESS);
	}

	@Override
	public void onError(Throwable e) {
		Log.i(TAG, "User preferences uploaded on server failure");
		onError(mContext, e);
		notifyTaskFinished(FinishedWorkerStatus.FAIL);
	}

	@Override
	public void onNext(Response<Void> response) {
		Log.i(TAG, "Saved user preferences response code: " + response.code());
		if (response.isSuccessful()) {
			// now do nothing
			// this worker just set user preferences
		} else
			onError(mContext, new HttpException(response));

	}
}
