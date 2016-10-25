package edu.p.lodz.pl.studentshenchman.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.p.lodz.pl.studentshenchman.database.DatabaseHelper;
import edu.p.lodz.pl.studentshenchman.database.models.Build;
import edu.p.lodz.pl.studentshenchman.database.models.Course;
import edu.p.lodz.pl.studentshenchman.database.models.Room;
import edu.p.lodz.pl.studentshenchman.database.models.Teacher;

/**
 * Created by Michał on 2016-10-09.
 */
public class Utils {

	public static SelectedCourseContext createCourseContext(Context context, long courseId) {
		SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
		SelectedCourseContext selectedCourseContext = new SelectedCourseContext();

		Cursor c = db.query(Course.TABLE_NAME, null, Course._ID + "=?", new String[]{courseId + ""}, null, null, null);
		Course course = Course.fromCursor2Course(c);

		c = db.query(Room.TABLE_NAME, null, Room.EXTERNAL_ROOM_ID + "=?", new String[]{course.getExternalRoomId() + ""}, null, null, null);
		Room room = Room.fromCursor2Room(c);

		c = db.query(Build.TABLE_NAME, null, Build.EXTERNAL_BUILD_ID + "=?", new String[]{room.getExternalBuildId() + ""}, null, null, null);
		Build build = Build.fromCursor2Build(c);

		c = db.query(Teacher.TABLE_NAME, null, Teacher.EXTERNAL_TEACHER_ID + "=?", new String[]{course.getExternalTeacherId() + ""}, null, null, null);
		Teacher teacher = Teacher.fromCursor2Teacher(c);

		c.close();

		selectedCourseContext.setCourseId(courseId);
		selectedCourseContext.setCourseExternalId(course.getExternalId());
		selectedCourseContext.setCourseName(course.getName());
		selectedCourseContext.setCourseType(course.getCourseType());
		selectedCourseContext.setTeacher(teacher.getFirstName() + " " + teacher.getLastName());
		selectedCourseContext.setRoomName(room.getName());
		selectedCourseContext.setBuildName(build.getName());
		selectedCourseContext.setLatitude(build.getLatitude());
		selectedCourseContext.setLongitude(build.getLongitude());

		return selectedCourseContext;
	}


}
