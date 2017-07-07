package br.com.jordan.frasesdodia.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import br.com.jordan.frasesdodia.Model.Frase;
import br.com.jordan.frasesdodia.SQLite.CustomSQLiteOpenHelper;

/**
 * Created by User on 05/07/2017.
 */

public class FraseDao {
    private SQLiteDatabase database;
    //Criando as colunas
    private String[] colums = {CustomSQLiteOpenHelper.COLUMN_ID, CustomSQLiteOpenHelper.COLUMN_TITULO};
    private CustomSQLiteOpenHelper customSQLiteOpenHelper;


    public FraseDao(Context context) {
        customSQLiteOpenHelper = new CustomSQLiteOpenHelper(context);
    }

    public void open() throws SQLException {
        //Abrindo a conexão com o banco
        database = customSQLiteOpenHelper.getWritableDatabase();
    }

    public void close() {
        //Fechando a conexão com o banco
        customSQLiteOpenHelper.close();
    }

    public void create(String titulo) {
        //Objeto para guardar os valores a serem salvos no banco
        ContentValues values = new ContentValues();
        //Adicionando o titulo para ser adicionado no banco
        values.put(CustomSQLiteOpenHelper.COLUMN_TITULO, titulo);
        //Inserindo o titulo no banco
        long id = database.insert(CustomSQLiteOpenHelper.TABLE_FRASE, null, values);
        Cursor cursor = database.query(CustomSQLiteOpenHelper.TABLE_FRASE, colums, CustomSQLiteOpenHelper.COLUMN_ID + " = " + id, null, null, null, null);
        //Movendo o cursor para o proximo campo
        cursor.moveToFirst();
        cursor.close();
    }

    public void delete(Frase frase) {
        //Obtendo o id do objeto a ser deletado
        long id = frase.getId();
        //Deletando o Objeto do banco
        database.delete(customSQLiteOpenHelper.TABLE_FRASE, CustomSQLiteOpenHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Frase> getAll() {
        List<Frase> frases = new ArrayList<>();


        //Pegando a lista do banco
        Cursor cursor = database.query(CustomSQLiteOpenHelper.TABLE_FRASE, colums, null, null, null, null, null);

        try {
            cursor.moveToFirst();

            //Verificando se ainda tem campo na tabela para percorrer
            while (!cursor.isAfterLast()) {
                Frase frase = new Frase();
                frase.setId(cursor.getLong(0));
                frase.setTitulo(cursor.getString(1));
                frases.add(frase);
                cursor.moveToNext();
            }

            //Fechando o cursor
            cursor.close();
            return frases;

        }catch (android.database.SQLException e){
            e.printStackTrace();
            return  null;
        }
    }
}
