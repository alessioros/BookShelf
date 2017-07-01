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
import butterknife.BindView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import it.polimi.bookshelf.R;
import it.polimi.bookshelf.activities.HomeActivity;
import it.polimi.bookshelf.data.DataHandler;
import it.polimi.bookshelf.objects.User;

public class SettingsFragment extends Fragment {

    @BindView(R.id.set_username_text) TextView userName;
    @BindView(R.id.set_usersurname_text) TextView userSurname;
    @BindView(R.id.set_useremail_text) TextView userEmail;
    @BindView(R.id.set_userpassword_text) TextView userPassword;
    @BindView(R.id.set_username) LinearLayout setUserName;
    @BindView(R.id.set_usersurname) LinearLayout setUserSurname;
    @BindView(R.id.set_useremail) LinearLayout setUserEmail;
    @BindView(R.id.set_userpassword) LinearLayout setUserPassword;

    private Unbinder unbinder;
    private User user;
    private DataHandler dataHandler;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {

        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public String shortVersion(String field){

        if(field.length() > 21){

            field = field.substring(0,19)+"..";
        }

        return field;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ButterKnife.bind(this, view);
        unbinder = ButterKnife.bind(this, view);

        dataHandler = new DataHandler(getActivity());
        user = dataHandler.getPreferencesHandler().getUser();

        userName.setText(shortVersion(user.getUser_name()));
        userSurname.setText(shortVersion(user.getUser_surname()));
        userEmail.setText(shortVersion(user.getAccess_email()));
        userPassword.setText(shortVersion(user.getPassword()));

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

                        if (!nameString.isEmpty() && isOnlyLetters(nameString)) {

                            try{
                                dataHandler.getPreferencesHandler().setUser_surname(nameString);
                                user.setUser_name(nameString);
                                dataHandler.getCloudHandler().updateUser(user);
                                userName.setText(shortVersion(nameString));
                                ((HomeActivity) getActivity()).refreshHeader();

                            }catch (Exception e){
                                e.printStackTrace();
                                Toast toast = Toast.makeText(getActivity(), getResources().getString(R.string.invalid_name), Toast.LENGTH_SHORT);
                                toast.show();
                            }

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

                        if (!surnameString.isEmpty() && isOnlyLetters(surnameString)) {

                            try{
                                dataHandler.getPreferencesHandler().setUser_surname(surnameString);
                                user.setUser_surname(surnameString);
                                dataHandler.getCloudHandler().updateUser(user);
                                userSurname.setText(shortVersion(surnameString));
                                ((HomeActivity) getActivity()).refreshHeader();

                            }catch (Exception e){
                                e.printStackTrace();
                                Toast toast = Toast.makeText(getActivity(), getResources().getString(R.string.invalid_surname), Toast.LENGTH_SHORT);
                                toast.show();
                            }

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

        setUserEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(R.string.change_email));

                View promptsView = LayoutInflater.from(getActivity()).inflate(R.layout.custom_settings_dialog, null);
                builder.setView(promptsView);

                final EditText dialogEdit = (EditText) promptsView.findViewById(R.id.dialog_edittxt);

                dialogEdit.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                builder.setPositiveButton(getResources().getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String emailString = dialogEdit.getText().toString();

                        if (!emailString.isEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {

                            try{
                                dataHandler.getPreferencesHandler().setUser_access_email(emailString);
                                user.setAccess_email(emailString);
                                dataHandler.getCloudHandler().updateUser(user);
                                userEmail.setText(shortVersion(emailString));
                                ((HomeActivity) getActivity()).refreshHeader();

                            }catch (Exception e){
                                e.printStackTrace();
                                Toast toast = Toast.makeText(getActivity(), getResources().getString(R.string.invalid_email), Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        } else {

                            Toast toast = Toast.makeText(getActivity(), getResources().getString(R.string.invalid_email), Toast.LENGTH_SHORT);
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

        setUserPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(R.string.change_password));

                View promptsView = LayoutInflater.from(getActivity()).inflate(R.layout.custom_settings_dialog, null);
                builder.setView(promptsView);

                final EditText dialogEdit = (EditText) promptsView.findViewById(R.id.dialog_edittxt);

                dialogEdit.setInputType(InputType.TYPE_CLASS_TEXT);

                builder.setPositiveButton(getResources().getString(R.string.alert_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String passwordString = dialogEdit.getText().toString();

                        if (!passwordString.isEmpty() && passwordString.length() > 4 || passwordString.length() < 15) {

                            try{
                                dataHandler.getPreferencesHandler().setUser_password(passwordString);
                                user.setPassword(passwordString);
                                dataHandler.getCloudHandler().updateUser(user);
                                userPassword.setText(shortVersion(passwordString));
                                ((HomeActivity) getActivity()).refreshHeader();

                            }catch (Exception e){
                                e.printStackTrace();
                                Toast toast = Toast.makeText(getActivity(), getResources().getString(R.string.invalid_password), Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        } else {

                            Toast toast = Toast.makeText(getActivity(), getResources().getString(R.string.invalid_password), Toast.LENGTH_SHORT);
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

    @Override public void onDestroyView() {
        super.onDestroyView();
        //unbinder.unbind();
    }

    public boolean isOnlyLetters(String name) {
        return name.matches("[\\p{L}]+");
    }
}
