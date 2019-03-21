package com.mobile.nexus.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobile.nexus.Model.Alunos;

import java.util.ArrayList;

public class AlunosDAO extends SQLiteOpenHelper {

    private static final String DATABASE = "nexus_db";
    private static  final int VERSION = 1;

    public AlunosDAO (Context context) {
        super(context, DATABASE,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String aluno = "CREATE TABLE alunos(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nomealuno TEXT NOT NULL, telefone TEXT NOT NULL, email TEXT );";
        db.execSQL(aluno);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String aluno = "DROP TABLE IF EXISTIS alunos";
        db.execSQL(aluno);

    }

    //Salva Aluno

    public void salvarAluno(Alunos aluno){
        ContentValues values = new ContentValues();

        values.put("nomealuno",aluno.getNomeAluno());
        values.put("telefone",aluno.getTelefone());
        values.put("email",aluno.getEmail());

        getWritableDatabase().insert("alunos", null,values);
    }


    //Alterar PRoduto


    public void alterarAluno(Alunos aluno){
        ContentValues values = new ContentValues();

        values.put("nomealuno",aluno.getNomeAluno());
        values.put("telefone",aluno.getTelefone());
        values.put("email",aluno.getEmail());

        String [] args = {aluno.getId().toString()};

        getWritableDatabase().update("alunos",values,"id=?", args);

    }

    public void deletarAluno(Alunos aluno){
        String [] args = {aluno.getId().toString()};

        getWritableDatabase().delete("alunos","id=?", args);
    }

    //Listar Aluno

    public ArrayList<Alunos> getLista() {
        String [] columns = {"id","nomealuno","telefone","email"};
        Cursor cursor = getWritableDatabase().query("alunos",columns,null,null,null,null,null,null);
        ArrayList<Alunos> alunos = new ArrayList<Alunos>();

        while (cursor.moveToNext()){
            Alunos aluno = new Alunos();
            aluno.setId(cursor.getLong(0));
            aluno.setNomeAluno(cursor.getString(1));
            aluno.setTelefone(cursor.getString(2));
            aluno.setEmail(cursor.getString(3));

            alunos.add(aluno);

        }

        return alunos;

    }
}
