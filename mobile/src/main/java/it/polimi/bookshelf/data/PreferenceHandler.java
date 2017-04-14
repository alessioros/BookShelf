package it.polimi.bookshelf.data;

import android.content.Context;
import android.content.SharedPreferences;
import it.polimi.bookshelf.R;

public class PreferenceHandler {

	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	private String PREFERENCE_NAME = "default";
	private Context context;

	public PreferenceHandler(Context context) {

		this.context = context;
		this.sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
	}

	public String getUser_name() {

		String defaultValue = context.getResources().getString(R.string.user_name_default);
		return sp.getString(context.getResources().getString(R.string.user_name), defaultValue);
	}

	public void setUser_name(String value) {

        editor = sp.edit();
		editor.putString(context.getResources().getString(R.string.user_name), value);
		editor.commit();
	}

	public void resetUser_name() {

		String defaultValue = context.getResources().getString(R.string.user_name_default);
        editor = sp.edit();
		editor.putString(context.getResources().getString(R.string.user_name), defaultValue);
		editor.commit();
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

		String defaultValue = context.getResources().getString(R.string.user_surname_default);
        editor = sp.edit();
		editor.putString(context.getResources().getString(R.string.user_surname), defaultValue);
		editor.commit();
	}
	public int getNum_books() {

		int defaultValue = Integer.parseInt(context.getResources().getString(R.string.num_books_default));
		return sp.getInt(context.getResources().getString(R.string.num_books), defaultValue);
	}

	public void setNum_books(int value) {

        editor = sp.edit();
		editor.putInt(context.getResources().getString(R.string.num_books), value);
		editor.commit();
	}

	public void resetNum_books() {

		int defaultValue = Integer.parseInt(context.getResources().getString(R.string.num_books_default));
        editor = sp.edit();
		editor.putInt(context.getResources().getString(R.string.num_books), defaultValue);
		editor.commit();
	}
}
