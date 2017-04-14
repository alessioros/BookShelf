package it.polimi.bookshelf.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import it.polimi.bookshelf.R;
import it.polimi.bookshelf.activities.HomeActivity;
import it.polimi.bookshelf.data.PreferenceHandler;

public class SettingsFragment extends Fragment {

    private TextView userName, userSurname;
    private LinearLayout setUserName,setUserSurname;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        final PreferenceHandler pH = new PreferenceHandler(getActivity());

        userName = (TextView) view.findViewById(R.id.set_username_text);
        userSurname = (TextView) view.findViewById(R.id.set_usersurname_text);

        setUserName = (LinearLayout) view.findViewById(R.id.set_username);
        setUserSurname = (LinearLayout) view.findViewById(R.id.set_usersurname);

        userName.setText(pH.getUser_name());
        userSurname.setText(pH.getUser_surname());

        setUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(R.string.change_username));

                View promptsView = LayoutInflater.from(getActivity()).inflate(R.layout.custom_settings_dialog, null);
                builder.setView(promptsView);

                final EditText dialogEdit = (EditText) promptsView.findViewById(R.id.dialog_edittxt);
                dialogEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

                builder.setPositiveButton(getResources().getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String nameString = dialogEdit.getText().toString();

                        if (!nameString.equals("") && isOnlyLetters(nameString)) {

                            pH.setUser_name(nameString);
                            userName.setText(pH.getUser_name());
                            ((HomeActivity) getActivity()).refreshHeader();

                        } else {

                            Toast toast = Toast.makeText(getActivity(), getResources().getString(R.string.invalid_name), Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.alert_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        setUserSurname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(R.string.change_usersurname));

                View promptsView = LayoutInflater.from(getActivity()).inflate(R.layout.custom_settings_dialog, null);
                builder.setView(promptsView);

                final EditText dialogEdit = (EditText) promptsView.findViewById(R.id.dialog_edittxt);

                dialogEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

                builder.setPositiveButton(getResources().getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String surnameString = dialogEdit.getText().toString();

                        if (!surnameString.equals("") && isOnlyLetters(surnameString)) {

                            pH.setUser_surname(surnameString);
                            userSurname.setText(pH.getUser_surname());
                            ((HomeActivity) getActivity()).refreshHeader();

                        } else {

                            Toast toast = Toast.makeText(getActivity(), getResources().getString(R.string.invalid_surname), Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.alert_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public boolean isOnlyLetters(String name) {
        return name.matches("[\\p{L}]+");
    }
}
