package com.example.ethazi_mac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

public class Erregistratu extends AppCompatActivity {
    private EditText txtizenabizen, txterabiltzaile, txtpasahitza1, txtpasahitza2, txtmail, txttelefono;
    private Button btnReserva;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erregistratu);

        txterabiltzaile=(EditText)findViewById(R.id.editTextuser);
        txtpasahitza1=(EditText)findViewById(R.id.editPass1);
        txtpasahitza2=(EditText)findViewById(R.id.editPass2);
        txtizenabizen=(EditText)findViewById(R.id.editTextizenabizen);
        txtmail=(EditText)findViewById(R.id.editTextmail);
        txttelefono=(EditText)findViewById(R.id.editTexttelefonoa);
        btnReserva=(Button) findViewById(R.id.buttonErregis);
    }


    public void DatuakOndo(){

                    if(TextUtils.isEmpty((CharSequence) txtizenabizen) && TextUtils.isEmpty((CharSequence) txterabiltzaile) && TextUtils.isEmpty((CharSequence) txtpasahitza1) && TextUtils.isEmpty((CharSequence) txtpasahitza2)) {
                        txtizenabizen.setError("");
                        txterabiltzaile.setError("");
                        txtpasahitza1.setError("");
                        txtpasahitza2.setError("");
                        return;
                    }
                    else{
                        if (txtpasahitza1.getText().equals(txtpasahitza2.getText())){

                        }
                        else {
                            //CODIGO PARA AVANZAR
                        }
                    }
                }
}
