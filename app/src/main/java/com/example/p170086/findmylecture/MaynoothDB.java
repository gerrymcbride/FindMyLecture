package com.example.p170086.findmylecture;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gerard
 */

public class MaynoothDB {

    /*
    This class declares the table columns,
    Database, and table, DBHelper and the Context Interface
     */

    public static final String KEY_ROWID = "_id";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGTITUDE = "longitute";
    public static final String KEY_NAME = "name";
    public static final String KEY_DEP = "department";
    public static final String KEY_ABOUT = "about";

    private static final String DATABASE_NAME = "Universities";
    private static final String DATABASE_TABLE = "Maynooth";
    private static final int DATABASE_VERSION = 1;

    private DbHelper ourHelper;
    private Context ourContext;
    private SQLiteDatabase ourDatabase;


private static class DbHelper extends SQLiteOpenHelper {
    // initialize DBHelper
    public DbHelper(Context context){
        // inherit from Super class
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){ // create function for table

        db.execSQL("CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " // PK auto increments
                + KEY_NAME + " VARCHAR(50) NOT NULL, " // Venue Name
                + KEY_DEP + " VARCHAR(50) NOT NULL, " // Department
                + KEY_LATITUDE + " TEXT(15) NOT NULL, " // latitude
                + KEY_LONGTITUDE + " TEXT(15) NOT NULL, " // longitude
                + KEY_ABOUT + " TEXT NOT NULL);" // about venue
        );

    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        }

    }

    public MaynoothDB(Context c) {ourContext = c;}

    public MaynoothDB open() throws SQLException {

        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        ourHelper.close();
    }

    public void createEntry(String name, String department, String longitude, String latitude, String about){
        ContentValues insertValues = new ContentValues();
        insertValues.put(KEY_NAME, name);
        insertValues.put(KEY_DEP, department);
        insertValues.put(KEY_LATITUDE, latitude);
        insertValues.put(KEY_LONGTITUDE, longitude);
        insertValues.put(KEY_ABOUT, about);
        ourDatabase.insert(DATABASE_TABLE, null, insertValues);

    }

    public String getData(String name){
        String[] columns = new String[]{ KEY_NAME, KEY_DEP, KEY_LATITUDE, KEY_LONGTITUDE, KEY_ABOUT, KEY_ROWID };
        Cursor cur = ourDatabase.query(DATABASE_TABLE, columns, KEY_NAME + "=?", new String[]{name}, null, null, null);

        String result = "";




        int Name = cur.getColumnIndex(KEY_NAME);
        int Department = cur.getColumnIndex(KEY_DEP);
        int Latitude = cur.getColumnIndex(KEY_LATITUDE);
        int Longitude = cur.getColumnIndex(KEY_LONGTITUDE);
        int about = cur.getColumnIndex(KEY_ABOUT);


            cur.moveToLast();
                result += cur.getString(Name) + "/" + cur.getString(Department) + "/" + cur.getDouble(Latitude) + "/"
                        + cur.getDouble(Longitude) + "/" + cur.getString(about) + "\n";


        return result;
    }

    public void populateMaynoothTable(){

        createEntry("Eolas Building", "Department of Computer Science", "53.384611", "6.601662", "Teaching Labs 1-4");
        createEntry("John Hume Building", "Department of Philosophy", "53.383891", "-6.599503", "JH 1-7");
        createEntry("Students Union", "Student Welfare", "53.382929", "-6.603665", "Bar and Student Services");
        createEntry("Iontas Building", "Department of English", "53.384716", "-6.600174", "Iontas Theatre, Seminar Rooms 1-5");
        createEntry("Arts Block", "Arts and Languages", "53.383821", "-6.601455", "Lecture Theatre 1 & 2");
        createEntry("John Paul II Library", "Library and Resources", "53.381178", "-6.599197", "Demonstration Rooms 1-4");
        createEntry("Science Building", "Department of Chemistry and Biology", "53.383008", "-6.600329", "Biology Labs 1-5, Chemical Labs 1-3");
        createEntry("Callan Building", "Department of Media", "53.383091", "-6.602486", "CB 1-6, Software Testing Labs");
        createEntry("Froebel College of Education", "Department of Masters of Education", "53.384970", "-6.598751", "Here is where you teach!");
        createEntry("Logic House", "Department of Maths and Physics", "53.377946", "-6.596101", "Maths Labs 1-6");
        createEntry("Loftus Hall", "Department of Theology", "53.378490", "-6.598480", "There's alot of great architecture here!");
        createEntry("Aula Maxima", "Department of Music", "53.380508", "-6.597862", "Exam and Function Venue");

    }
}
