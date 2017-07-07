package br.com.jordan.frasesdodia;

import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;

import java.sql.SQLException;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import br.com.jordan.frasesdodia.Dao.FraseDao;
import br.com.jordan.frasesdodia.Model.Frase;
import me.grantland.widget.AutofitTextView;


public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabAdd;
    private FraseDao bdFrase;
    private Button btFrase, btCopiar;
    private AutofitTextView etFrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciando o bdFrase
        bdFrase = new FraseDao(this);

        try {
            //Abrindo o Banco de Dados
            bdFrase.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btFrase = (Button) findViewById(R.id.btFrase);
        btCopiar = (Button) findViewById(R.id.btCopiar);
        etFrase = (AutofitTextView) findViewById(R.id.etFrase);
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(listenerAddFrase);

        btFrase.setOnClickListener(listenerGerarFrase);
        btCopiar.setOnClickListener(listenerCopiarFrase);

    }

    private View.OnClickListener listenerGerarFrase = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Random random = new Random();
            if(!bdFrase.getAll().isEmpty()){
               Frase frase = bdFrase.getAll().get(random.nextInt(bdFrase.getAll().size()));
                etFrase.setText(frase.getTitulo());
            }else{
                Toast.makeText(MainActivity.this, "Sem Frases no banco!", Toast.LENGTH_SHORT).show();
            }

        }
    };

    private View.OnClickListener listenerAddFrase = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, Add_Frase.class));
        }
    };

    private View.OnClickListener listenerCopiarFrase = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //place your TextView's text in clipboard
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            if (!etFrase.getText().toString().isEmpty()) {
                clipboard.setText(etFrase.getText().toString());
                Toast.makeText(MainActivity.this, "Texto Copiado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Escolha uma frase!", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onResume() {
        try {
            bdFrase.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        bdFrase.close();
        super.onPause();

    }
}
