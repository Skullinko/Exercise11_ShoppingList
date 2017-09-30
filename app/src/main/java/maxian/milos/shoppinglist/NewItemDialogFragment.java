package maxian.milos.shoppinglist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by milos on 30.09.2017.
 */

public class NewItemDialogFragment extends DialogFragment {

    public interface NewItemDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String name, int count, double price);

        public void onDialogNegativeClick(DialogFragment dialog);
    }

    NewItemDialogListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (NewItemDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " " + R.string.exceptionText);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.new_item_dialog, null);

        builder.setView(dialogView)
                .setTitle(R.string.dialogTitle)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et1 = (EditText) dialogView.findViewById(R.id.name);
                        String name = et1.getText().toString();
                        EditText et2 = (EditText) dialogView.findViewById(R.id.count);
                        int count = Integer.parseInt(et2.getText().toString());
                        EditText et3 = (EditText) dialogView.findViewById(R.id.price);
                        double price = Double.parseDouble(et3.getText().toString());
                        mListener.onDialogPositiveClick(NewItemDialogFragment.this, name, count, price);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogNegativeClick(NewItemDialogFragment.this);
                    }
                });

        return builder.create();
    }
}