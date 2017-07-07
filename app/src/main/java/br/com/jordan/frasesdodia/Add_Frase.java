package br.com.jordan.frasesdodia;

import java.sql.SQLException;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.jordan.frasesdodia.Dao.FraseDao;

/**
 * Created by User on 05/07/2017.
 */

public class Add_Frase extends AppCompatActivity {
    private EditText etFrase;
    private FraseDao bdFrase;
    private Button btAdd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_frase);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Intanciando o Banco de Dados
        bdFrase = new FraseDao(this);

        try {
            //Abrindo o banco de dados
            bdFrase.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        etFrase = (EditText) findViewById(R.id.et_frase);
        btAdd = (Button) findViewById(R.id.bt_add);
        btAdd.setOnClickListener(listenerAdicionar);
    }

    private View.OnClickListener listenerAdicionar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Se o campo não estiver vazio adiciona no banco
            if (!etFrase.getText().toString().isEmpty()) {
                bdFrase.create(etFrase.getText().toString());
                Toast.makeText(Add_Frase.this, "Frase Criada!", Toast.LENGTH_SHORT).show();
                etFrase.setText("");
            } else {
                Toast.makeText(Add_Frase.this, "Campo Vazio !!!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();

        //Fechando o banco
        bdFrase.close();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
