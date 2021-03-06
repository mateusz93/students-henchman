package edu.p.lodz.pl.studentshenchman.workers.endpoints;

import cdm.SettingsRS;
import model.Date;
import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Michał on 2016-10-26.
 */

public interface SettingsEndpoints {

	@GET("settings")
	Observable<Response<SettingsRS>> getSettings();

	@GET("settings/date")
	Observable<Response<Date>> getDate();
}
