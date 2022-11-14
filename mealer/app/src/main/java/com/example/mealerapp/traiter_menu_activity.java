package com.example.mealerapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class traiter_menu_activity extends AppCompatActivity {

    ListView listViewRepas;

    List<Repas> repasArrayList;

    DatabaseReference databaseRepas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traiter_menu);

        /*
        Repas repas = new Repas("Toast à l'avocat et au saumon fumé","Tartinade de cajou à l'aneth, œuf poché, oignons rouge et graines de sésame","Repas délicieux et nutritionnel", "Européenne", 25);
        repas.addRepasDatabase();

        Repas repas2 = new Repas("Tajine au poulet","citron,coriandre,cumin,gingembre frais,oignon,navet,carottes,huile d'olive, poulet avec os,safran,eau bouillante","Ce plat typique du maroc fait un excellent repas à manger en famille ou avec des invités.","Marocaine",100);
        repas2.addRepasDatabase();

        Repas repas3 = new Repas("Tajine au boeuf ","citron,coriandre,cumin,gingembre frais,oignon,navet,carottes,huile d'olive, viande de boeuf avec os,safran,eau bouillante","Ce plat typique du maroc fait un excellent repas à manger en famille ou avec des invités.","Marocaine",100);
        repas3.addRepasDatabase();

        Repas repas4 = new Repas("Gyozas","Vinaigre de riz,Sauce soja,Porc,Huile de sésame,Sauce soja,Ail,Choux chinois,huile d'olive,Gingembre frais,Huile de tournesol,Ciboulette","Raviolis japonais.","Japonaise",50);
        repas4.addRepasDatabase();

        Repas repas5 = new Repas("Pizza Margherita","salt,passata,fresh basil,huile d'olive,flour,instant yeast,mozzarella,parmesan,cherry tomatoes,basil leaves,Ciboulette","Raviolis japonais.","Italienne",50);
        repas5.addRepasDatabase();

        Repas repas6 = new Repas("Kopto","feuilles de moringa,oignons,piment,tomates,pâte d’arachide","Sauce au feuilles de moringa et pate d’arachide.","Nigérienne",50);
        repas6.addRepasDatabase();

        Repas repas7 = new Repas("Awara","soybeans,water,salt,pepper,onions,vegetables,oil,lemon","Awara is a low-fat,high-protein soy food that is often offeredin blocks .","Nigérienne",55);
        repas7.addRepasDatabase();

        Repas repas8 = new Repas("Pastilla","poulet,oignons,huile d'olive,sel,poivre noivre,canelle moulue,safran,Gingembre moulu,Eau,Coriandre fraîche hachée,oeufs,sucre,amandes,Eau de fleur d'oranger","Recette Pastilla marocaine au poulet et amandes.","Marocaine",110);
        repas8.addRepasDatabase();

        Repas repas9 = new Repas("Bibimbap","vinaigre de riz,sucre,eau,carotte,sel,riz,viande de boeuf,sauce soya,sirop d’érable,huile de sésame,sauce sriracha,ail,poivre","bol coréen de riz,de légumes, d’oeuf et de boeuf.","Coréenne",70);
        repas9.addRepasDatabase();

        Repas repas10 = new Repas("Paella","Riz,Chorizo,Petits pois,Tomates,Cuisses de poulet,Poivrons rouge,Cubes de bouillon de volaille,Oignons,Ail,Huile d'olive,Persil,Sel,poivre","Paella au poulet et au chorizo.","Espagnole",40);
        repas10.addRepasDatabase();
        */





        listViewRepas = (ListView) findViewById(R.id.listViewRepas);
        databaseRepas = FirebaseDatabase.getInstance().getReference("Repas");
        repasArrayList = new ArrayList<Repas>();

        listViewRepas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Repas repas = repasArrayList.get(i);
                showOffrirRetirer(repas);
                return true;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();




        databaseRepas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                repasArrayList.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    Repas repas = data.getValue(Repas.class) ;
                    Log.i("Repas non offert",  repas.getRepasStatus() + " id : " + repas.getId());
                    if ( repas.getRepasStatus().equals("false")) {
                        repasArrayList.add(repas);
                    }
                    //Si idCuisnier de repas == id du cuisinier connecte


                }

                RepasList repasAdapter = new RepasList(traiter_menu_activity.this, repasArrayList) ;
                listViewRepas.setAdapter(repasAdapter) ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    private void showOffrirRetirer(Repas repas) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.traiter_menu_dialogue, null);
        dialogBuilder.setView(dialogView);


        final Button buttonOffrirRepas = (Button) dialogView.findViewById(R.id.buttonOffrirRepas);
        final Button buttonDesoffirRepas = (Button) dialogView.findViewById(R.id.buttonDesoffrirRepas);

        dialogBuilder.setTitle("");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonOffrirRepas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offrirRepas(repas);
                b.dismiss();
            }
        });

        buttonDesoffirRepas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offrirRepas(repas);
                b.dismiss();
            }
        });

    }

    private void offrirRepas(Repas repas) {

        repas.traiterRepas();
        //FirebaseDatabase.getInstance().getReference("Repas").child(repas.getId()).setValue(repas);
        FirebaseDatabase.getInstance().getReference("Repas").child(repas.getId()).setValue(repas);
        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
    }

    public void retirerRepas(Repas repas) {
        /*
         * supprimer un repas de la base des données
         * */

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Repas").child(repas.getId());
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                database.removeValue();
                Toast.makeText(getApplicationContext(), "Repas retiré de la base de donnée", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}