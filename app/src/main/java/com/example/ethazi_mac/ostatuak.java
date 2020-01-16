package com.example.ethazi_mac;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ostatuak extends AppCompatActivity {

    private ListView lista;
    private ArrayList<Integer> codigos = new ArrayList<Integer>();
    private ArrayList<String> nombres = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ostatuak);
        this.setTitle(R.string.txtostatuakpantalla);
        //BD CARGAR

        //LISTVIEW
        lista=(ListView)findViewById(R.id.listview);
        ArrayAdapter adapter = new ArrayAdapter (this,android.R.layout.simple_list_item_1, nombres);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView adapter, View view, int a, long l) {
                //verTarea(adapter,view,a,l);
                //System.out.println(lista.getItemAtPosition(i)+" : "+ codigos.get(i).toString());
            }
        });
    }
}
