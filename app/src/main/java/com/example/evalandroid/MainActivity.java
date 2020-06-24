package com.example.evalandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText name, date, noteScenario, noteReal, noteMusic, mail;
    TextView message;
    Button submit_button, allFilms, delete, update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        name = (EditText) findViewById(R.id.name);
        date = (EditText) findViewById(R.id.date);
        noteScenario = (EditText) findViewById(R.id.noteScenario);
        noteReal = (EditText) findViewById(R.id.noteReal);
        noteMusic = (EditText) findViewById(R.id.noteMusic);
        message = (TextView) findViewById(R.id.message);
        mail = (EditText) findViewById(R.id.mail);
        submit_button = (Button) findViewById(R.id.submit_button);
        allFilms = (Button) findViewById(R.id.allFilms);
        delete = (Button) findViewById(R.id.delete);
        update = (Button) findViewById(R.id.update);

        insertData();
        displayAll();
        deleteData();
        UpdateData();
    }

    public void insertData() {
        submit_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isGood = myDb.insertData(name.getText().toString(), date.getText().toString(), noteScenario.getText().toString(), noteReal.getText().toString(), noteMusic.getText().toString(), message.getText().toString());
                        if (isGood) {
                            Toast.makeText(MainActivity.this, "Le film a bien été ajouté", Toast.LENGTH_LONG).show();
                            if(!mail.getText().toString().equals("") || mail.getText().toString().equals("Email")){
                                sendEmail();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Une erreur est survenue", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void displayAll() {
        allFilms.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            showMessage("Erreur", "Aucun film trouvé");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ID : " + res.getString(0) + "\n");
                            buffer.append("Title : " + res.getString(1) + "\n");
                            buffer.append("Date : " + res.getString(2) + "\n");
                            buffer.append("Note scénario : " + res.getString(3) + "\n");
                            buffer.append("Note réalisation : " + res.getString(4) + "\n");
                            buffer.append("Note musique : " + res.getString(5) + "\n");
                            buffer.append("Description : " + res.getString(6) + "\n\n");
                        }
                        showMessage("Critiques", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"" + mail.getText().toString()};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Critique du film : " + name.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Titre : " + name.getText().toString() + "\n" + "Date : "  + date.getText().toString() + "\n" + "Note scénario : " + noteScenario.getText().toString() + "\n" + "Note réalisation : " + noteReal.getText().toString() + "\n" + "Note musique : " + noteMusic.getText().toString() + "\n" + "Critique : " + message.getText().toString());

        try {
            startActivity(Intent.createChooser(emailIntent, "Envoie de l'email"));
            finish();
            Log.i("Email envoyé", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this,
                    "Erreur", Toast.LENGTH_SHORT).show();
        }
    }

    public void UpdateData(){
        update.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = myDb.updateData(name.getText().toString(), date.getText().toString(), noteScenario.getText().toString(), noteReal.getText().toString(), noteMusic.getText().toString(), message.getText().toString());
                        if(isUpdated == true){
                            Toast.makeText(MainActivity.this, "Le film a bien été modifié", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(MainActivity.this, "Une erreur est survenue", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void deleteData() {
        delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(name.getText().toString());
                        if(deletedRows > 0){
                            Toast.makeText(MainActivity.this, "Le film a bien été supprimé", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(MainActivity.this, "Une erreur est survenue", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}