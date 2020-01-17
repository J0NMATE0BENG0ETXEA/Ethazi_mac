package com.example.ethazi_mac;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ostatuak extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> codigos = new ArrayList<String>();
    private ArrayList<String> nombres = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ostatuak);
        this.setTitle(R.string.txtostatuakpantalla);
        //BD CARGAR

        //LISTVIEW
        listView=(ListView)findViewById(R.id.listview);
        EnviarRecibirDatos("http://192.168.13.26/ethazi_mac/selectostatuak.php");
       // ArrayAdapter adapter = new ArrayAdapter (this,android.R.layout.simple_list_item_1, nombres);
       /* lista.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView adapter, View view, int a, long l) {
                //verTarea(adapter,view,a,l);
                //System.out.println(lista.getItemAtPosition(i)+" : "+ codigos.get(i).toString());
            }
        });*/
    }


    public void CargarListView(JSONArray ja){

        ArrayList<Ostatu> lista = new ArrayList<>();

        //KOLTSULTAREN EMAITZAK OBJEKTUETAN SARTU
        for(int i=0;i<ja.length();i+=10){

            try {
                Ostatu ost = new Ostatu(ja.getString(i),ja.getString(i+1),ja.getString(i+2),ja.getString(i+3),ja.getString(i+4),ja.getString(i+5),
                        ja.getString(i+6),ja.getString(i+7),ja.getString(i+8),ja.getString(i+9));
                //lista.add(ja.getString(i+1));
                lista.add(ost);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        ArrayAdapter<Ostatu> adaptador = new ArrayAdapter<Ostatu>(this, android.R.layout.simple_list_item_1, lista);
        listView.setAdapter(adaptador);

    }

    public void EnviarRecibirDatos(String URL){

        Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).show();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarListView(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }

}
