package sg.edu.rp.c347.p06_taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditTaskActivity extends AppCompatActivity {

    TextView tvID;
    EditText etName, etDesc;
    Button btnUpdate, btnCancel, btnDelete;
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Intent i = getIntent();
        task = (Task) i.getSerializableExtra("data");

        tvID = findViewById(R.id.tvId);
        etDesc = findViewById(R.id.etDesc);
        etName = findViewById(R.id.etName);
        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        tvID.setText(task.getId());
        etName.setText(task.getName());
        etDesc.setText(task.getDescription());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbh = new DBHelper(EditTaskActivity.this);
                task.setName(etName.getText().toString());
                task.setDescription(etDesc.getText().toString());
                dbh.updateTask(task);
                dbh.close();

                Intent i = new Intent();
                i.putExtra("name", etName.getText().toString());
                i.putExtra("desc", etDesc.getText().toString());
                setResult(RESULT_OK, i);

                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbh = new DBHelper(EditTaskActivity.this);
                dbh.deleteTask(task.getId());
                dbh.close();

                Intent i = new Intent();
                i.putExtra("data", task.getId());
                setResult(RESULT_OK, i);

                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
