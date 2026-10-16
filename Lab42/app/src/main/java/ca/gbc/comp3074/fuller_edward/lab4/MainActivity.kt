package ca.gbc.comp3074.fuller_edward.lab4

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Switch
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var swActive: Switch
    private lateinit var btnViewAll: Button
    private lateinit var btnAdd: Button
    private lateinit var lvCustomerList: ListView
    private lateinit var dbHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DatabaseHelper(this)
        etName = findViewById(R.id.etName)
        etAge = findViewById(R.id.etAge)
        swActive = findViewById(R.id.swActive)
        btnAdd = findViewById(R.id.btnAdd)
        lvCustomerList = findViewById(R.id.lvCustomerList)

        showCustomers()

        btnAdd.setOnClickListener {
            val name = etName.text.toString()
            val age = etAge.text.toString().toInt()
            val active = swActive.isChecked
            val customer = CustomerModel(name=name,age=age,active=active)
            val success = dbHelper.addOne(customer)
            if (success){
                Toast.makeText(this,"Customer added: $customer", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"Error while adding customer.",Toast.LENGTH_LONG).show()
            }
        }

        btnViewAll.setOnClickListener{
            showCustomers()
        }

        lvCustomerList.setOnItemLongClickListener{
            parent, _, position, _ ->
            val customer = parent.getItemAtPosition(position) as CustomerModel
            val success = dbHelper.deleteOne(customer)
            if (success){
                Toast.makeText(this,"Customer deleted: $customer", Toast.LENGTH_LONG).show()
                showCustomers()
                true
            }else{
                Toast.makeText(this,"Error while adding customer.",Toast.LENGTH_LONG).show()
                false
            }
        }

        lvCustomerList.setOnItemClickListener{
            parent, _, position, _ ->
            val customer = parent.getItemAtPosition(position) as CustomerModel
            Toast.makeText(this,"Customer selected: $customer", Toast.LENGTH_LONG).show()
            true
        }
    }

    fun showCustomers(){
        val customers = dbHelper.getAll()
        val adapter = ArrayAdapter<CustomerModel>(
            this,
            android.R.layout.simple_list_item_1,
            customers
        )
        lvCustomerList.adapter = adapter
    }
}