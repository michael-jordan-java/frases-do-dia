package br.com.jordan.frasesdodia.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 05/07/2017.
 */

public class CustomSQLiteOpenHelper extends SQLiteOpenHelper{
    // Nome da tabela
    public static final String TABLE_FRASE = "frase";
    //Coluna ID
    public static final String COLUMN_ID = "_id";
    //Coluna Titulo
    public static final String COLUMN_TITULO = "titulo";

    //Nome da base de dados
    private static final String DATABASE_NAME = "frase.db";

    //Versão do Banco de Dados
    private static final int DATABASE_VERSION = 4;

    //Criando a Tabela
    private static final String DATABASE_CREATE = "create table " + TABLE_FRASE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITULO + " text not null);";

    public CustomSQLiteOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        //Criando a tabela no SQLite
        database.execSQL(DATABASE_CREATE);

        Log.e("life", "onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int versaoVelha, int versaoNova) {
        //Atualizando o SQLite
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_FRASE);

        //Criando a tabela no SQLite
        database.execSQL(DATABASE_CREATE);


        Log.e("life", "onUpgrade");
    }
}
