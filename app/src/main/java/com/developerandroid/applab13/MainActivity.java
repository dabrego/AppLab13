package com.developerandroid.applab13;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.database.Cursor;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        UsuariosSQLiteHelper usdbh =
                new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        //Si hemos abierto correctamente la base de datos
        if(db != null){
            //Insertamos 5 usuarios de ejemplo

            //Cerramos la base de datos
            db.close();
        }

        //Toast.makeText(getApplicationContext(), "Usuarios agregados", Toast.LENGTH_LONG).show();
    }

    public void makeInsert(View v){

        EditText texto1 = (EditText) findViewById(R.id.editText);
        EditText texto2 = (EditText) findViewById(R.id.editText2);
        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        UsuariosSQLiteHelper usdbh =  new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        //Creamos el registro a insertar como objeto ContentValues
        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("codigo", texto1.getText().toString());
        nuevoRegistro.put("nombre", texto2.getText().toString());
        //Insertamos el registro en la base de datos (1er parámetro es tabla)
        db.insert("Usuarios", null, nuevoRegistro);
        //Toast.makeText(getApplicationContext(), "Insert Exitoso", Toast.LENGTH_LONG).show();

/*

        try{
            //Abrimos la base de datos 'DBUsuarios' en modo escritura
            UsuariosSQLiteHelper usdbh =  new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);
            SQLiteDatabase db = usdbh.getWritableDatabase();
            EditText edT1 = (EditText) findViewById(R.id.editText);
            EditText edT2 = (EditText) findViewById(R.id.editText2);
            //Creamos el registro a insertar como objeto ContentValues
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("codigo", edT1.getText().toString());
            nuevoRegistro.put("nombre", edT2.getText().toString());

            //Insertamos el registro en la base de datos (1er parámetro es tabla)
            db.insert("DBUsuarios", null, nuevoRegistro);
            Toast.makeText(getApplicationContext(), "Insert Exitoso", Toast.LENGTH_LONG).show();

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Insert No Exitoso" +e, Toast.LENGTH_LONG).show();
        }*/





    }

    public void makeUpdate(View v){
        EditText texto1 = (EditText) findViewById(R.id.editText);
        EditText texto2 = (EditText) findViewById(R.id.editText2);
        try {
            //Abrimos la base de datos 'DBUsuarios' en modo escritura
            UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);
            SQLiteDatabase db = usdbh.getWritableDatabase();
            //Establecemos los campos-valores a actualizar
            ContentValues valores = new ContentValues();
            valores.put("nombre", texto2.getText().toString());
            //Actualizamos el registro en la base de datos
            db.update("Usuarios", valores, "codigo=" + texto1.getText().toString(), null);
            Toast.makeText(getApplicationContext(), "Update Exitoso",
                    Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error al actualizar",
                    Toast.LENGTH_LONG).show();
        }

    }

    public void makeDelete(View v){
        EditText texto1 = (EditText) findViewById(R.id.editText);
        try {
            //Abrimos la base de datos 'DBUsuarios' en modo escritura
            UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);
            SQLiteDatabase db = usdbh.getWritableDatabase();
            db.delete("Usuarios", "codigo=" + texto1.getText().toString(), null);
            Toast.makeText(getApplicationContext(), "Delete Exitoso",
                    Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error al eliminar",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void makeConsult(View v){
        TextView txtResultado = (TextView)findViewById(R.id.vista);
        try {
            //Abrimos la base de datos 'DBUsuarios' en modo escritura
            UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);
            SQLiteDatabase db = usdbh.getWritableDatabase();

            Cursor c = db.rawQuery(" SELECT codigo,nombre FROM Usuarios", null);
            txtResultado.setText("");
            Toast.makeText(getApplicationContext(), "Registros " +c.getCount(), Toast.LENGTH_LONG).show();

            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    String cod= c.getString(0);           // Get the info from column '0'
                    String nom = c.getString(1);          // Get the info from column '1'
                    txtResultado.append(" " + cod + " - " + nom + "\n");
                } while(c.moveToNext());
            }
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error al consultar",
                    Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
