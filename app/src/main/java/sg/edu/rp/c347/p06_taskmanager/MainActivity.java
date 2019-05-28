package sg.edu.rp.c347.p06_taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvTask;
    Button btnAdd;
    ArrayList<Task> tasks;
    ArrayAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTask = findViewById(R.id.lvTask);
        btnAdd = findViewById(R.id.btnAdd);

        updateTable();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivityForResult(i, 9);
            }
        });

        lvTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
                Task task = tasks.get(i);
                Log.d("The task is:", task.toString());

                String id = String.valueOf(task.getId());
                String name = task.getName();
                String desc = task.getDescription();
                Task target = new Task(Integer.parseInt(id), name, desc);
                intent.putExtra("data", target);
                startActivityForResult(intent, 9);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            updateTable();
        }
    }

    private void updateTable(){
        DBHelper db = new DBHelper(MainActivity.this);
        tasks = db.getAllTasks();
        aa = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1 ,tasks);
        lvTask.setAdapter(aa);
        aa.notifyDataSetChanged();
        db.close();
    }
}
