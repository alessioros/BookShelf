package it.polimi.bookshelf.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.polimi.bookshelf.objects.User;

/* this class contains generic functions in order to interface with Firebase realtime database
* for configuring and managing your cloud database visit: https://console.firebase.google.com/
* for more complete examples visit: https://firebase.google.com/docs/database
*/
public class CloudHandler {

    private FirebaseDatabase fDB;

    public CloudHandler() {
        fDB = FirebaseDatabase.getInstance();
    }

    /*
    * returns the reference to the database at the specified location
    * @param reference the string of the location (example reference = "users")
    */
    public DatabaseReference getReference(String reference) {

        return this.fDB.getReference(reference);
    }

    /*
    * allows to add a new object to the database
    * @param object fields
    */
    public boolean addUser(String access_email, String password, String user_name, String user_surname) {

        User user = new User(access_email, password, user_name, user_surname);
        try {
            String returnValue = getReference("users").child(access_email.replace(".", "*")).setValue(user).toString();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /*
    * allows to update an object in the database
    * @param object fields
    */
    public boolean updateUser(User user) {

        try {
            getReference("users").child(user.getAccess_email().replace(".", "*")).setValue(user);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public DatabaseReference getUser(String access_email) {

        return getReference("users/" + access_email.replace('.', '*'));
    }

    /*
    * allows to delete an object from the database
    * @param objectId
    */
    public void deleteUser(String objectId) {

        getReference("users").child(objectId).removeValue();
    }
}
