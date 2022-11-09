package com.example.mealerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class traiter_demande_achat_activity extends AppCompatActivity {
    /*
    EditText editTextName;
    EditText editTextPrice;
    Button buttonAddProduct;
    ListView listViewProducts;

    //List<Demandes> demande;

    DatabaseReference databaseProducts ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traiter_demande_achat);


        listViewDemandes = (ListView) findViewById(R.id.listViewDemandes);


        databaseProducts = FirebaseDatabase.getInstance().getReference("Demandes") ;

        products = new ArrayList<>();



        listViewProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product product = products.get(i);
                showAccepterRejeter(product.getId(), product.getProductName());
                return true;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                products.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    Product product = data.getValue(Demande.class) ;
                    products.add(product) ; }

                Demande demande = new (traiter_demande_achat_activity.this, products) ;
                listViewProducts.setAdapter(productsAdapter) ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private void showAccepterRejeter(final String productId, String productName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextPrice  = (EditText) dialogView.findViewById(R.id.editTextPrice);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduct);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);

        dialogBuilder.setTitle(productName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                double price = Double.parseDouble(String.valueOf(editTextPrice.getText().toString()));
                if (!TextUtils.isEmpty(name)) {
                    updateProduct(productId, name, price);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(productId);
                b.dismiss();
            }
        });
    }

    private void updateProduct(String id, String name, double price) {

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("products").child(id) ;

        Product product = new Product(id, name, price) ;
        db.setValue(product) ;
        Toast.makeText(getApplicationContext(), "Product updated", Toast.LENGTH_LONG).show();
    }

    private boolean deleteProduct(String id) {

        DatabaseReference dbase = FirebaseDatabase.getInstance().getReference("products").child(id) ;
        dbase.removeValue();
        Toast.makeText(getApplicationContext(), "Product deleted", Toast.LENGTH_LONG).show();

        return true ;
    }

    private void addProduct() {

        String name = editTextName.getText().toString().trim() ;
        double price = Double.parseDouble(String.valueOf(editTextPrice.getText().toString())) ;

        if (!TextUtils.isEmpty(name)) {

            String id = databaseProducts.push().getKey() ;
            Product product = new Product(id, name, price) ;

            databaseProducts.child(id).setValue(product) ;

            editTextName.setText("");
            editTextPrice.setText("");

            Toast.makeText(this, "Product added", Toast.LENGTH_LONG).show() ;
        }

        else {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show() ; }
    }
    */

}