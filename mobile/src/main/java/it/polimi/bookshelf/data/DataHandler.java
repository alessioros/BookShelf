package it.polimi.bookshelf.data;

import android.content.Context;

public class DataHandler {

    private Context context;
    private PreferenceHandler ph;
    private DatabaseHandler dbh;
    private CloudHandler cdh;

    public DataHandler(Context context) {
        this.context = context;
        this.ph = new PreferenceHandler(this.context);
        this.dbh = new DatabaseHandler(this.context);
        this.cdh = new CloudHandler();
    }

    public PreferenceHandler getPreferencesHandler() {
        return this.ph;
    }

    public DatabaseHandler getDatabaseHandler() {
        return this.dbh;
    }

    public CloudHandler getCloudHandler() {
        return this.cdh;
    }
}
