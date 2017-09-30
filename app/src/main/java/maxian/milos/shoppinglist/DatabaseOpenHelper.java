package maxian.milos.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by milos on 30.09.2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myDatabase";
    private final String TABLE_PRODUCTS = "products";
    private final String NAME = "name";
    private final String COUNT = "count";
    private final String PRICE = "price";

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PRODUCTS + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT, " + COUNT + " INTEGER, " + PRICE + " REAL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }
}
