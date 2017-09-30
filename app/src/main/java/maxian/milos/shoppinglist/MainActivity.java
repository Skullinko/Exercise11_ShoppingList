package maxian.milos.shoppinglist;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        NewItemDialogFragment.NewItemDialogListener {
    private final String TABLE_PRODUCTS = "products";
    private SQLiteDatabase db;
    private Cursor cursor;
    private ListView listView;
    private ArrayList<Item> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NewItemDialogFragment eDialog = new NewItemDialogFragment();
                eDialog.show(getFragmentManager(), getString(R.string.dialogTitle));

            }
        });

        this.listView = (ListView) findViewById(R.id.myList);

        this.db = (new DatabaseOpenHelper(this)).getWritableDatabase();
        queryData();

        this.products = new ArrayList<>();

        listView.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view, final int position, long row_id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Remove product");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Item product = products.remove(position);
                        String[] args = {String.valueOf(product.getId())};
                        db.delete(TABLE_PRODUCTS, "_id=?", args);
                        queryData();
                        totalPrice();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });
    }

    public void queryData() {
        String[] resultColumns = new String[]{"_id", "name", "count", "price"};
        this.cursor = db.query(TABLE_PRODUCTS, resultColumns, null, null, null, null, "price", null);

        ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.row_layout, cursor,
                new String[]{"name", "count", "price"},
                new int[]{R.id.name, R.id.count, R.id.price}
                , 0);
        listView.setAdapter(adapter);
    }

    public void totalPrice() {
        double total = 0.0;
        if (this.cursor.moveToFirst()) {
            do {
                double price = this.cursor.getDouble(3);
                total += price;
            } while (this.cursor.moveToNext());
        }
        Toast.makeText(this, getString(R.string.totalPriceText) + " " + total, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String name, int count, double price) {
        Item newItem = new Item(name, count, price);

        ContentValues values = new ContentValues(3);
        values.put("name", name);
        values.put("count", count);
        values.put("price", price);
        Long id = this.db.insert(TABLE_PRODUCTS, null, values);
        newItem.setId(id.intValue());
        this.products.add(newItem);
        queryData();

        totalPrice();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(this, R.string.cancel, Toast.LENGTH_LONG).show();
    }
}
