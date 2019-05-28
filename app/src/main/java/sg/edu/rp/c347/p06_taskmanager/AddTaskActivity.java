package sg.edu.rp.c347.p06_taskmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity {

    EditText etName, etDesc;
    Button btnAdd, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        etDesc = findViewById(R.id.etDesc);
        etName = findViewById(R.id.etName);

        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String desc = etDesc.getText().toString();
                DBHelper dbh = new DBHelper(AddTaskActivity.this);
                long row_affected = dbh.insertTask(name, desc);
                dbh.close();
                if (row_affected != -1){
                    Toast.makeText(AddTaskActivity.this, "Added successfully",
                            Toast.LENGTH_SHORT).show();
                    etName.setText("");
                    etDesc.setText("");

                }
            }
        });


    }
}
