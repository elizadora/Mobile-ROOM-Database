package br.ufc.quixada.usoroomdatabase;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.List;

import br.ufc.quixada.usoroomdatabase.dao.PessoaDao;
import br.ufc.quixada.usoroomdatabase.database.AppDatabase;
import br.ufc.quixada.usoroomdatabase.models.Pessoa;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Pessoa sidartha = new Pessoa("Sidartha Carvalho", "Sistemas de Informação", 23);
        Pessoa melba = new Pessoa("Melba Girafa", "Zoologia", 12);


        AppDatabase appDatabase = Room.databaseBuilder(this,
                AppDatabase.class,
                "db_pessoas")
                .enableMultiInstanceInvalidation()
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        PessoaDao pessoaDao = appDatabase.pessoaDao();
        pessoaDao.insertAll(sidartha, melba);

        List<Pessoa> pessoasDoBd = pessoaDao.getAllPessoas();
        for(Pessoa p : pessoasDoBd){
            Log.d("sid-tag", p.toString());
        }


    }
}