package edu.p.lodz.pl.studentshenchman.dashboard;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import edu.p.lodz.pl.studentshenchman.R;
import edu.p.lodz.pl.studentshenchman.abstract_ui.StudentShenchmanMainActivity;
import edu.p.lodz.pl.studentshenchman.database.DatabaseHelper;
import edu.p.lodz.pl.studentshenchman.qr_scanner.SimpleScanner;
import edu.p.lodz.pl.studentshenchman.settings.SettingsActivity;
import edu.p.lodz.pl.studentshenchman.settings.datastore.SettingsDataStoreHelper;
import edu.p.lodz.pl.studentshenchman.timetable_plan.activity.TimetableActivity;
import edu.p.lodz.pl.studentshenchman.workers.DownloadTeachersWorker;
import edu.p.lodz.pl.studentshenchman.workers.SetUserPreferencesWorker;
import edu.p.lodz.pl.studentshenchman.workers.helpers.WorkerRunnerManager;
import edu.p.lodz.pl.studentshenchman.workers.utils.WorkerType;

import static edu.p.lodz.pl.studentshenchman.workers.AbstractWorker.WORKER_NAME;

public class DashboardActivity extends StudentShenchmanMainActivity {
	private static final String TAG = DashboardActivity.class.getName();
	private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

	private Toolbar toolbar;
	private DrawerLayout mDrawerLayout;
	private NavigationView mNavDrawer;
	private TextView mLessonName;
	private TextView mTeacher;
	private TextView mBuilding;
	private TextView mRoom;
	private TextView mLessonTime;
	private ActionBarDrawerToggle mDrawerToggle;

	private TextView mTodosCounter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);

		toolbar = (Toolbar) findViewById(R.id.tool_bar);
		setSupportActionBar(toolbar);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mNavDrawer = (NavigationView) findViewById(R.id.nav_view);
		mLessonName = (TextView) findViewById(R.id.item_lesson_name);
		mTeacher = (TextView) findViewById(R.id.item_teacher_name);
		mBuilding = (TextView) findViewById(R.id.item_building_name);
		mRoom = (TextView) findViewById(R.id.item_room_name);
		mLessonTime = (TextView) findViewById(R.id.item_lesson_time);
		mTodosCounter = (TextView) MenuItemCompat.getActionView(mNavDrawer.getMenu().
				findItem(R.id.todos_for_today));
		mTodosCounter.setText("10");
		setCountersProperties(mTodosCounter);
		mDrawerToggle = setupDrawerToggle();
		setupDrawerContent(mNavDrawer);
		mDrawerLayout.addDrawerListener(mDrawerToggle);
		//mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, mDrawerList);
//		mDrawerLayout.setScrimColor(Color.TRANSPARENT);

		mLessonName.setText("Projektowanie aplikacji internetowych");
		mTeacher.setText("Dr. inz Rafal Kielbik");
		mBuilding.setText("CTI");
		mRoom.setText("303");
		mLessonTime.setText("8:15 - 10:00");

		//setAnimation();

		DatabaseHelper.getInstance(getApplicationContext());

		ImageButton buttonTimetable = (ImageButton) findViewById(R.id.timetable_icon);

		buttonTimetable.setOnClickListener((view) ->
				goToTimetable()
		);

		ImageButton settingsButton = (ImageButton) findViewById(R.id.settings_icon);

		settingsButton.setOnClickListener((view) ->
				goToSettings()
		);

		ImageButton scanQRCode = (ImageButton) findViewById(R.id.qrcode_scanner_icon);

		scanQRCode.setOnClickListener((view) ->
				goToScanQRCode()
		);

		ImageButton testButton = (ImageButton) findViewById(R.id.test_button);
		testButton.setOnClickListener((view) -> {

		});


	}

	private void setCountersProperties(TextView view) {
		view.setTextColor(getResources().getColor(R.color.light_blue_color));
		view.setGravity(Gravity.CENTER_VERTICAL);
		view.setTypeface(null, Typeface.BOLD);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onPause() {
		super.onPause();
		//mLessonName.clearAnimation();
	}

	private ActionBarDrawerToggle setupDrawerToggle() {
		return new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.ok, R.string.no);
	}

	private void setupDrawerContent(NavigationView navigationView) {
		navigationView.setNavigationItemSelectedListener(
				new NavigationView.OnNavigationItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected(MenuItem menuItem) {
						selectDrawerItem(menuItem);
						return true;
					}
				});
	}

	public void selectDrawerItem(MenuItem menuItem) {
		Bundle bundle;
		switch (menuItem.getItemId()) {
			case R.id.nav_download_timetable:
				bundle = new Bundle();
				bundle.putString(WORKER_NAME, WorkerType.DOWNLOAD_COURSES.name());
				WorkerRunnerManager.getInstance(getApplicationContext()).startWorker(bundle);
				break;
			case R.id.nav_download_settings:
				bundle = new Bundle();
				bundle.putString(WORKER_NAME, WorkerType.DOWNLOAD_SETTINGS.name());
				WorkerRunnerManager.getInstance(getApplicationContext()).startWorker(bundle);

				Bundle bundleTeachers = new Bundle();
				bundleTeachers.putString(WORKER_NAME, WorkerType.DOWNLOAD_TEACHERS.name());
				WorkerRunnerManager.getInstance(getApplicationContext()).startWorker(bundleTeachers);
				break;
			case R.id.nav_customize:

				break;
			case R.id.nav_about:

				break;
			case R.id.todos_for_today:

				break;
			case R.id.nav_logout:
				finish();
				break;
			default:
				break;
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			Log.i(TAG, "tooootle");
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void setAnimation() {
		int colorFrom = getResources().getColor(android.R.color.white);
		int colorTo = getResources().getColor(R.color.light_blue_color);
		ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
		colorAnimation.setDuration(3000); // milliseconds
		colorAnimation.setRepeatCount(Animation.INFINITE);
		colorAnimation.addUpdateListener((animator) ->
				mLessonName.setTextColor((int) animator.getAnimatedValue())

		);
		colorAnimation.start();
	}


	private void goToTimetable() {
		Intent intent = new Intent(DashboardActivity.this, TimetableActivity.class);
		finish();
		startActivity(intent);
	}

	private void goToSettings() {
		Intent intent = new Intent(DashboardActivity.this, SettingsActivity.class);
		finish();
		startActivity(intent);
	}

	private void goToScanQRCode() {
		if (ContextCompat.checkSelfPermission(DashboardActivity.this,
				Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(DashboardActivity.this,
					new String[]{Manifest.permission.CAMERA},
					MY_PERMISSIONS_REQUEST_CAMERA);
		} else {
			Intent intent = new Intent(DashboardActivity.this, SimpleScanner.class);
			finish();
			startActivity(intent);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
	                                       @NonNull String permissions[], @NonNull int[] grantResults) {
		switch (requestCode) {
			case MY_PERMISSIONS_REQUEST_CAMERA: {
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					Intent intent = new Intent(DashboardActivity.this, SimpleScanner.class);
					finish();
					startActivity(intent);
				} else {
					Toast.makeText(this, "Brak uprawnień do aparatu!", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}
