package it.polimi.bookshelf.data;

import android.content.Context;
import android.content.SharedPreferences;
import it.polimi.bookshelf.R;
import it.polimi.bookshelf.objects.User;

public class PreferenceHandler {

	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	private String PREFERENCE_NAME = "default";
	private Context context;

	public PreferenceHandler(Context context) {
		SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		this.context = context;
		this.sp = sharedPref;
	}

	public User getUser(){

		return new User(getUser_access_email(),getUser_password(),getUser_name(),getUser_surname());
	}

	public void setUser(User user){

		editor = sp.edit();
		editor.putString(context.getResources().getString(R.string.user_access_email), user.getAccess_email());
		editor.putString(context.getResources().getString(R.string.user_password), user.getPassword());
		editor.putString(context.getResources().getString(R.string.user_name), user.getUser_name());
		editor.putString(context.getResources().getString(R.string.user_surname), user.getUser_surname());
		editor.apply();
	}

	public String getUser_access_email() {

		String defaultValue = context.getResources().getString(R.string.user_access_email_default);
		return sp.getString(context.getResources().getString(R.string.user_access_email), defaultValue);
	}

	public void setUser_access_email(String value) {

		editor = sp.edit();
		editor.putString(context.getResources().getString(R.string.user_access_email), value);
		editor.apply();
	}

	public void resetUser_access_email() {

		editor = sp.edit();
		String defaultValue = context.getResources().getString(R.string.user_access_email_default);
		editor.putString(context.getResources().getString(R.string.user_access_email), defaultValue);
		editor.apply();
	}
	public String getUser_password() {

		String defaultValue = context.getResources().getString(R.string.user_password_default);
		return sp.getString(context.getResources().getString(R.string.user_password), defaultValue);
	}

	public void setUser_password(String value) {

		editor = sp.edit();
		editor.putString(context.getResources().getString(R.string.user_password), value);
		editor.apply();
	}

	public void resetUser_password() {

		editor = sp.edit();
		String defaultValue = context.getResources().getString(R.string.user_password_default);
		editor.putString(context.getResources().getString(R.string.user_password), defaultValue);
		editor.apply();
	}
	public String getUser_name() {

		String defaultValue = context.getResources().getString(R.string.user_name_default);
		return sp.getString(context.getResources().getString(R.string.user_name), defaultValue);
	}

	public void setUser_name(String value) {

		editor = sp.edit();
		editor.putString(context.getResources().getString(R.string.user_name), value);
		editor.apply();
	}

	public void resetUser_name() {

		editor = sp.edit();
		String defaultValue = context.getResources().getString(R.string.user_name_default);
		editor.putString(context.getResources().getString(R.string.user_name), defaultValue);
		editor.apply();
	}
	public String getUser_surname() {

		String defaultValue = context.getResources().getString(R.string.user_surname_default);
		return sp.getString(context.getResources().getString(R.string.user_surname), defaultValue);
	}

	public void setUser_surname(String value) {

		editor = sp.edit();
		editor.putString(context.getResources().getString(R.string.user_surname), value);
		editor.apply();
	}

	public void resetUser_surname() {

		editor = sp.edit();
		String defaultValue = context.getResources().getString(R.string.user_surname_default);
		editor.putString(context.getResources().getString(R.string.user_surname), defaultValue);
		editor.apply();
	}
	public String getUser_book_count() {

		String defaultValue = context.getResources().getString(R.string.user_book_count_default);
		return sp.getString(context.getResources().getString(R.string.user_book_count), defaultValue);
	}

	public void setUser_book_count(String value) {

		editor = sp.edit();
		editor.putString(context.getResources().getString(R.string.user_book_count), value);
		editor.apply();
	}

	public void resetUser_book_count() {

		editor = sp.edit();
		String defaultValue = context.getResources().getString(R.string.user_book_count_default);
		editor.putString(context.getResources().getString(R.string.user_book_count), defaultValue);
		editor.apply();
	}
}