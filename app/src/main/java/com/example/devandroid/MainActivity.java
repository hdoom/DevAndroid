package com.example.devandroid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText nomEditText, dateHeureEditText, decorationEditText, nourritureEditText, serviceEditText, descriptionEditText;
    private Button addButton;
    private Button shareButton;

    private RestaurantDatabaseHelper dbHelper;
    private ListView restaurantListView;

    public class RestaurantDetailActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.restaurant_list_item);

            Bundle extras = getIntent().getExtras();
            if (extras != null){
                String restaurantId = extras.getString("restaurant_id");
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomEditText = findViewById(R.id.nomEditText);
        dateHeureEditText = findViewById(R.id.dateHeureEditText);
        decorationEditText = findViewById(R.id.decorationEditText);
        nourritureEditText = findViewById(R.id.nourritureEditText);
        serviceEditText = findViewById(R.id.serviceEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        addButton = findViewById(R.id.addButton);

        /*restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, RestaurantDetailActivity.class);
                startActivity(intent);
            }
        */


        dbHelper = new RestaurantDatabaseHelper(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterRestaurant();
            }
        });
        shareButton = findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                partagerRestaurantParEmail();
            }
        });
        lireTousLesRestaurants();
    }

    private void ajouterRestaurant() {
        String nom = nomEditText.getText().toString();
        String dateHeure = dateHeureEditText.getText().toString();
        float noteDecoration = Float.parseFloat(decorationEditText.getText().toString());
        float noteNourriture = Float.parseFloat(nourritureEditText.getText().toString());
        float noteService = Float.parseFloat(serviceEditText.getText().toString());
        String description = descriptionEditText.getText().toString();

        // Insertion dans la base de données
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RestaurantDatabaseHelper.COLUMN_NOM, nom);
        values.put(RestaurantDatabaseHelper.COLUMN_DATE_HEURE, dateHeure);
        values.put(RestaurantDatabaseHelper.COLUMN_NOTE_DECORATION, noteDecoration);
        values.put(RestaurantDatabaseHelper.COLUMN_NOTE_NOURRITURE, noteNourriture);
        values.put(RestaurantDatabaseHelper.COLUMN_NOTE_SERVICE, noteService);
        values.put(RestaurantDatabaseHelper.COLUMN_DESCRIPTION, description);
        long newRowId = db.insert(RestaurantDatabaseHelper.TABLE_RESTAURANT, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Restaurant ajouté à la base de données", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Erreur lors de l'ajout du restaurant", Toast.LENGTH_SHORT).show();
        }
    }
    private void partagerRestaurantParEmail() {
        Restaurant dernierRestaurant = getDernierRestaurant();

        if (dernierRestaurant != null) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Critique de restaurant");
            emailIntent.putExtra(Intent.EXTRA_TEXT, dernierRestaurant.toString() + "\n" +
                    "Décoration: " + dernierRestaurant.getNoteDecoration() + "\n" +
                    "Nourriture: " + dernierRestaurant.getNoteNourriture() + "\n" +
                    "Service: " + dernierRestaurant.getNoteService() + "\n" +
                    "Description: " + dernierRestaurant.getDescriptionCritique());

            startActivity(Intent.createChooser(emailIntent, "Partager par e-mail"));
        } else {
            Toast.makeText(this, "Aucun restaurant à partager.", Toast.LENGTH_SHORT).show();
        }
    }

    private Restaurant getDernierRestaurant() {
        // Récupérer le dernier restaurant ajouté depuis la base de données
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                RestaurantDatabaseHelper.TABLE_RESTAURANT,
                null,
                null,
                null,
                null,
                null,
                "1"
        );

        if (cursor != null && cursor.moveToFirst()) {

            int callName = cursor.getColumnIndex(RestaurantDatabaseHelper.COLUMN_NOM);
            String nom = cursor.getString(callName);
            int callDate = cursor.getColumnIndex(RestaurantDatabaseHelper.COLUMN_DATE_HEURE);
            String dateHeure = cursor.getString(callDate);
            int callDeco = cursor.getColumnIndex(RestaurantDatabaseHelper.COLUMN_NOTE_DECORATION);
            float noteDecoration = cursor.getFloat(callDeco);
            int callFood = cursor.getColumnIndex(RestaurantDatabaseHelper.COLUMN_NOTE_NOURRITURE);
            float noteNourriture = cursor.getFloat(callFood);
            int callServ = cursor.getColumnIndex(RestaurantDatabaseHelper.COLUMN_NOTE_SERVICE);
            float noteService = cursor.getFloat(callServ);
            int callDesc = cursor.getColumnIndex(RestaurantDatabaseHelper.COLUMN_DESCRIPTION);
            String description = cursor.getString(callDesc);

            return new Restaurant(nom, dateHeure, noteDecoration, noteNourriture, noteService, description);
        }

        return null;
    }

    private void clearFields() {
        nomEditText.setText("");
        dateHeureEditText.setText("");
        decorationEditText.setText("");
        nourritureEditText.setText("");
        serviceEditText.setText("");
        descriptionEditText.setText("");
        }

    private void lireTousLesRestaurants() {
        // Récupérer tous les restaurants depuis la base de données
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                RestaurantDatabaseHelper.COLUMN_NOM,
                RestaurantDatabaseHelper.COLUMN_DATE_HEURE,
                RestaurantDatabaseHelper.COLUMN_NOTE_DECORATION,
                RestaurantDatabaseHelper.COLUMN_NOTE_NOURRITURE,
                RestaurantDatabaseHelper.COLUMN_NOTE_SERVICE,
                RestaurantDatabaseHelper.COLUMN_DESCRIPTION
        };

        Cursor cursor = db.query(
                RestaurantDatabaseHelper.TABLE_RESTAURANT,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int callName = cursor.getColumnIndex(RestaurantDatabaseHelper.COLUMN_NOM);
                String nom = cursor.getString(callName);
                int callDate = cursor.getColumnIndex(RestaurantDatabaseHelper.COLUMN_DATE_HEURE);
                String dateHeure = cursor.getString(callDate);
                int callDeco = cursor.getColumnIndex(RestaurantDatabaseHelper.COLUMN_NOTE_DECORATION);
                float noteDecoration = cursor.getFloat(callDeco);
                int callFood = cursor.getColumnIndex(RestaurantDatabaseHelper.COLUMN_NOTE_NOURRITURE);
                float noteNourriture = cursor.getFloat(callFood);
                int callServ = cursor.getColumnIndex(RestaurantDatabaseHelper.COLUMN_NOTE_SERVICE);
                float noteService = cursor.getFloat(callServ);
                int callDesc = cursor.getColumnIndex(RestaurantDatabaseHelper.COLUMN_DESCRIPTION);
                String description = cursor.getString(callDesc);
                }
            cursor.close();
        }


    }

}

