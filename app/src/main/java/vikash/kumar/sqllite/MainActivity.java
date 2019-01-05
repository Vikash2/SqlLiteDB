package vikash.kumar.sqllite;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    EditText editTextId,editName,editEmail,editCC;
    Button buttonAdd,buttonGetData,buttonUpdate,buttonDelete,buttonViewAll;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);

        editTextId = findViewById(R.id.editText_id);
        editName = findViewById(R.id.editText_name);
        editEmail = findViewById(R.id.editText_email);
        editCC = findViewById(R.id.editText_CC);


        buttonAdd = findViewById(R.id.button_add);
        buttonGetData = findViewById(R.id.button_view);
        buttonUpdate = findViewById(R.id.button_update);
        buttonDelete = findViewById(R.id.button_delete);
        buttonViewAll = findViewById(R.id.button_viewAll);

//        showMessage("TEST","Testing Done");
        addData();
        getData();
        viewAll();
        updateData();
        deleteData();

    }

    public void addData(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editTextId.getText().toString();
                String name = editName.getText().toString();
                String email = editEmail.getText().toString();
                String cc = editCC.getText().toString();



                if(id.equals(String.valueOf("")) && name.equals(String.valueOf("")) && email.equals(String.valueOf("")) && cc.equals(String.valueOf(""))){
                    editTextId.setError("Enter ID");
                    editName.setError("Enter Name");
                    editEmail.setError("Enter Email");
                    editCC.setError("Enter Course Count");
                    Toast.makeText(MainActivity.this,"Enter all the values",Toast.LENGTH_SHORT).show();
                    return;
                }

                if((id.equals(String.valueOf("")) || name.equals(String.valueOf("")) || email.equals(String.valueOf("")) || cc.equals(String.valueOf(""))))
                {
                    if(id.equals(String.valueOf("")))
                        editTextId.setError("Enter ID");
                    if(name.equals(String.valueOf("")))
                        editName.setError("Enter Name");
                    if(email.equals(String.valueOf("")))
                        editEmail.setError("Enter Email");
                    if(cc.equals(String.valueOf("")))
                        editCC.setError("Enter Course Count");

                    Toast.makeText(MainActivity.this,"OPPS! Enter the blank fields",Toast.LENGTH_SHORT).show();
                }
                else {

                    myDB.insertData(editName.getText().toString(), editEmail.getText().toString(), editCC.getText().toString());
                    Toast.makeText(MainActivity.this,"Data Inserted.",Toast.LENGTH_SHORT).show();
                }



            }
        });
    }
    public void deleteData(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = editTextId.getText().toString();

                if(id.equals(String.valueOf(""))){
                    editTextId.setError("Enter ID");
                    Toast.makeText(MainActivity.this,"OOPSsss!!!",Toast.LENGTH_SHORT).show();
                    return;
                }

                Integer deletedRow = myDB.deleteData(editTextId.getText().toString());

                if(deletedRow >0){
                    Toast.makeText(MainActivity.this,"Deleted Successfully!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"OOPSSS!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void getData(){
        buttonGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editTextId.getText().toString();

                if(id.equals(String.valueOf(""))){
                    editTextId.setError("Enter ID");
                    Toast.makeText(MainActivity.this,"OOPPSSS!!!",Toast.LENGTH_SHORT).show();

                    return;
                }

                Cursor cursor = myDB.getData(id);
                String data=null;

                if(cursor.moveToNext()){
                    data = "ID: "+ cursor.getString(0) +"\n"+
                            "NAME: "+ cursor.getString(1) +"\n"+
                            "EMAIL: "+ cursor.getString(2) +"\n"+
                            "COURSE COUNT: "+ cursor.getString(3) +"\n";
                }
                showMessage("DATA: ",data);
            }
        });

    }
    public void viewAll(){
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = myDB.getAllData();

                if(cursor.getCount()==0){
                    showMessage("ERROR","Nothing Found in DB");
                }

                StringBuffer buffer =new StringBuffer();

                while(cursor.moveToNext()){
                    buffer.append("ID: "+cursor.getString(0)+"\n");
                    buffer.append("NAME: "+cursor.getString(1)+"\n");
                    buffer.append("EMAIL: "+cursor.getString(2)+"\n");
                    buffer.append("COURSE COUNT: "+cursor.getString(3)+"\n\n");
                }
                showMessage("ALL DATA: ",buffer.toString());


            }
        });
    }
    public void updateData(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = editTextId.getText().toString();
                String name = editName.getText().toString();
                String email = editEmail.getText().toString();
                String cc = editCC.getText().toString();

                //Handling all the null values
                if(id.equals(String.valueOf("")) && name.equals(String.valueOf("")) && email.equals(String.valueOf("")) && cc.equals(String.valueOf("")))
                {
                    editTextId.setError("Enter ID");
                    editName.setError("Enter Name");
                    editEmail.setError("Enter Email");
                    editCC.setError("Enter Course Count");
                    Toast.makeText(MainActivity.this,"Enter all the values",Toast.LENGTH_SHORT).show();
                    return;
                }
                // if one of any field is null
                if((id.equals(String.valueOf("")) || name.equals(String.valueOf("")) || email.equals(String.valueOf("")) || cc.equals(String.valueOf(""))))
                {
                    if(id.equals(String.valueOf("")))
                        editTextId.setError("Enter ID");
                    if(name.equals(String.valueOf("")))
                        editName.setError("Enter Name");
                    if(email.equals(String.valueOf("")))
                        editEmail.setError("Enter Email");
                    if(cc.equals(String.valueOf("")))
                        editCC.setError("Enter Course Count");


                    Toast.makeText(MainActivity.this,"OPPS! Enter the blank fields",Toast.LENGTH_SHORT).show();
                }
                else {
                        myDB.updateData(editTextId.getText().toString(),
                        editName.getText().toString(),
                        editEmail.getText().toString(),
                        editCC.getText().toString());
                    Toast.makeText(MainActivity.this,"Updated Successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }




    private void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
}
