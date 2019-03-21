package com.mobile.nexus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.mobile.nexus.DAO.AlunosDAO;
import com.mobile.nexus.Model.Alunos;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lista;
    AlunosDAO DAO;
    ArrayList<Alunos> listView_Aluno;
    Alunos aluno;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.listView_Aluno);
        registerForContextMenu(lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                Alunos alunoEscolhido = (Alunos) adapter.getItemAtPosition(position);

                Intent i = new Intent(MainActivity.this, FormAluno.class);
                i.putExtra("aluno-escolhido", alunoEscolhido);
                startActivity(i);
            }
        });

        Button btnCadastrarAluno = (Button) findViewById(R.id.btn_CadastrarAluno);
        btnCadastrarAluno.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, FormAluno.class);
                startActivity(intent);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                aluno = (Alunos) adapter.getItemAtPosition(position);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Deletar Aluno");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                DAO = new AlunosDAO(MainActivity.this);
                DAO.deletarAluno(aluno);
                DAO.close();
                carregarAlunos();
                return true;
            }
        });
    }

    @Override
    protected  void onResume() {
        super.onResume();
        carregarAlunos();
    }

    public void carregarAlunos() {
        DAO = new AlunosDAO(MainActivity.this);
        listView_Aluno = DAO.getLista();
        DAO.close();

        if(listView_Aluno != null) {
            adapter = new ArrayAdapter<Alunos>(MainActivity.this, android.R.layout.simple_list_item_1, listView_Aluno);

            lista.setAdapter(adapter);
        }

    }
}
