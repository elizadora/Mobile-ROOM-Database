package br.ufc.quixada.usoroomdatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import br.ufc.quixada.usoroomdatabase.dao.PessoaDao;
import br.ufc.quixada.usoroomdatabase.database.AppDatabase;
import br.ufc.quixada.usoroomdatabase.models.Pessoa;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvPessoas;

    // btn add new person
    FloatingActionButton btnAdd;

    // form
    EditText name;
    EditText course;
    EditText age;

    List<Pessoa> pessoasDoBd;
    PessoaDao pessoaDao;
    ArrayList<Item> itemList = new ArrayList<Item>();
    ItemArrayAdapter itemArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btn_add);

        name = findViewById(R.id.name_edit);
        course = findViewById(R.id.course_edit);
        age = findViewById(R.id.age_edit);




        // construct recyclerview layout and link to item.xml
        rvPessoas = findViewById(R.id.pessoas);
        itemArrayAdapter = new ItemArrayAdapter(R.layout.item, itemList);

        rvPessoas = (RecyclerView) findViewById(R.id.pessoas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvPessoas.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());

        rvPessoas.addItemDecoration(dividerItemDecoration);

        rvPessoas.setAdapter(itemArrayAdapter);


        // create info about database use
        AppDatabase appDatabase = Room.databaseBuilder(this,
                AppDatabase.class,
                "db_pessoas")
                .enableMultiInstanceInvalidation()
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();


        pessoaDao = appDatabase.pessoaDao();
        pessoasDoBd = pessoaDao.getAllPessoas();
        for(Pessoa p : pessoasDoBd){
            itemList.add(new Item(p.nome, p.curso, p.idade));
        }

        swipeToDelete();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameS = name.getText().toString().trim();
                String courseS = course.getText().toString().trim();
                String ageS = age.getText().toString().trim();

                if(nameS.isEmpty() || courseS.isEmpty() || ageS.isEmpty()){
                    Toast.makeText(v.getContext(), "Preencha todos os campos para inserir uma pessoa", Toast.LENGTH_SHORT).show();

                }else{
                    int ageI = Integer.parseInt(ageS);

                    // create new object person
                    Pessoa pessoa = new Pessoa(nameS, courseS, ageI);

                    // add person to database
                    pessoaDao.insertAll(pessoa);

                    // add person pessoasDoBd
                    pessoasDoBd.add(pessoa);

                    // update itemList
                    itemList.add(new Item(nameS, courseS, ageI));

                    // notify adapter about changed
                    itemArrayAdapter.notifyItemInserted(itemList.size() - 1);

                    //clear form
                    name.setText("");
                    course.setText("");
                    age.setText("");
                }
            }
        });

    }

    private void swipeToDelete(){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // get position item in recyclerview
                int position = viewHolder.getAdapterPosition();

                Log.d("SwipeToDelete", "Position: " + position + ", Size of pessoasDoBd: " + pessoasDoBd.size() + ", Size of itemList: " + itemList.size());

                // get person position of pessoasDoBd
                Pessoa pessoa = pessoasDoBd.get(position);

                // delete person from database
                pessoaDao.delete(pessoa);

                // remove person from pessoasDoBd
                pessoasDoBd.remove(position);

                // remove person from recyclerview
                itemList.remove(position);


                // update recyclerview
                itemArrayAdapter.notifyItemRemoved(position);
                itemArrayAdapter.notifyItemRangeChanged(position, itemList.size());
            }
        }).attachToRecyclerView(rvPessoas);
    }
}