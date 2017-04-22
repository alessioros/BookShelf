package it.polimi.bookshelf.data;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import it.polimi.bookshelf.objects.Book;
import it.polimi.bookshelf.objects.Shelf;
import it.polimi.bookshelf.objects.User;

/* this class contains generic functions in order to interface with Firebase realtime database
* for configuring and managing your cloud database visit: https://console.firebase.google.com/
* for more complete examples visit: https://firebase.google.com/docs/database
*/
public class CloudHandler {

    private FirebaseDatabase fDB;
    private final String TAG = "CloudHandler";
    private User user;

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
    * allows to manipulate data in realtime
    * @param reference the DatabaseReference
    */
    public void addListener(DatabaseReference reference) {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // for a callback called just once -> addListenerForSingleValueEvent()
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    /*
    * allows to write a new object to the database
    * @param object fields
    */
    public void writeNewUser(String access_email, String password, String user_name, String user_surname) {

        User user = new User(access_email, password, user_name, user_surname);
        this.fDB.getReference().child("users").child(access_email).setValue(user);
    }

    public DatabaseReference getUser(String access_email) {

        Log.v("FIREBASE","GETTING USER... "+access_email);
        //testcodegen-e6dee/
        DatabaseReference ref = getReference("users/"+access_email.replace('.','*'));

        return ref;
    }

    /*
    * allows to delete an object from the database
    * @param objectId
    */
    public void deleteUser(String objectId) {

        // modify the reference and the ID with the values in your Firebase console
        //this.FBD.child("users").child(objectId).removeValue();
    }

    /*
    * allows to write a new object to the database
    * @param object fields
    */
    public void writeNewBook(String book_isbn, String book_title, String book_author, String user_id, String shelf_id) {

        Book book = new Book(book_isbn, book_title, book_author, user_id, shelf_id);
        // modify the reference and the ID with the values in your Firebase console
        //this.FBD.child("books").child("book_ID").setValue(book);
    }

    /*
    * allows to delete an object from the database
    * @param objectId
    */
    public void deleteBook(String objectId) {

        // modify the reference and the ID with the values in your Firebase console
        //this.FBD.child("books").child(objectId).removeValue();
    }

    /*
    * allows to write a new object to the database
    * @param object fields
    */
    public void writeNewShelf(String shelf_id, String shelf_name, String user_id) {

        Shelf shelf = new Shelf(shelf_id, shelf_name, user_id);
        // modify the reference and the ID with the values in your Firebase console
        //this.FBD.child("shelfs").child("shelf_ID").setValue(shelf);
    }

    /*
    * allows to delete an object from the database
    * @param objectId
    */
    public void deleteShelf(String objectId) {

        // modify the reference and the ID with the values in your Firebase console
        //this.FBD.child("shelfs").child(objectId).removeValue();
    }

}
