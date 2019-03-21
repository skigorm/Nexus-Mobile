package com.mobile.nexus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mobile.nexus.DAO.AlunosDAO;
import com.mobile.nexus.Model.Alunos;

public class FormAluno extends AppCompatActivity {

    EditText editText_NomeAluno, editText_Email, editText_Telefone;
    Button btn_InserirAluno;
    Alunos editarAluno, aluno;
    AlunosDAO DAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_aluno);

        aluno = new Alunos();
        DAO = new AlunosDAO(FormAluno.this);

        Intent intent = getIntent();
        editarAluno = (Alunos) intent.getSerializableExtra("aluno-selecionado");

        editText_NomeAluno = (EditText) findViewById(R.id.editText_NomeAluno);
        editText_Email = (EditText) findViewById(R.id.editText_Email);
        editText_Telefone = (EditText) findViewById(R.id.editText_Telefone);

        btn_InserirAluno = (Button) findViewById(R.id.btn_InserirAluno);

        if (editarAluno !=null){
            btn_InserirAluno.setText("Update");

            editText_NomeAluno.setText(editarAluno.getNomeAluno());
            editText_Email.setText(editarAluno.getEmail());
            editText_Telefone.setText(editarAluno.getTelefone());

            aluno.setId(editarAluno.getId());

        } else {
            btn_InserirAluno.setText("Cadastrar");
        }

        btn_InserirAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aluno.setNomeAluno(editText_NomeAluno.getText().toString());
                aluno.setEmail(editText_Email.getText().toString());
                aluno.setTelefone(editText_Telefone.getText().toString());

                if(btn_InserirAluno.getText().toString().equals("Cadastrar")){
                    DAO.salvarAluno(aluno);
                    DAO.close();
                } else {
                    DAO.alterarAluno(aluno);
                    DAO.close();
                }
            }
        });


    }
}
