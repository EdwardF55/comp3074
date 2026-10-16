package ca.gbc.comp3074.fuller_edward.lab4

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "customers.db"
        private const val DATABASE_VERSION = 1

        const val CUSTOMER_TABLE = "CUSTOMERS"
        const val COLUMN_ID = "ID"
        const val COLUMN_NAME = "CUSTOMER_NAME"
        const val COLUMN_AGE = "CUSTOMER_AGE"
        const val COLUMN_ACTIVE = "CUSTOMER_ACTIVE"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createStatement = """
            CREATE TABLE $CUSTOMER_TABLE (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_AGE INTEGER,
                $COLUMN_ACTIVE INTEGER
            )
        """.trimIndent()
        db?.execSQL(createStatement)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int){
        //WHYYYY
        val dropStatement = "DROP TABLE IF EXISTS $CUSTOMER_TABLE"
        db?.execSQL(dropStatement)
        onCreate(db)
    }

    fun addOne(customer: CustomerModel): Boolean{
        val db = writableDatabase
        val values = ContentValues().apply(){
            put(COLUMN_NAME, customer.name)
            put(COLUMN_AGE, customer.age)
            put(COLUMN_ACTIVE, if (customer.active) 1 else 0)
        }
        db.insert(CUSTOMER_TABLE, null, values)
        db.close()
        return newRowId != -1
    }

    fun getAll(): List<CustomerModel>{
        val customers = mutableListOf<CustomerModel>()
        val db = readableDatabase
        val query = "SELECT * FROM $CUSTOMER_TABLE"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()){
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE))
                val active = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ACTIVE)) == 1
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return customers
    }
    fun deleteOne(customer: CustomerModel): Boolean{
        val db = writableDatabase
        val n = db.delete(
            CUSTOMER_TABLE,
            "$COLUMN_ID = ?",
            arrayOf(customer.id.toString())
        )
        db.close()
        return n == 1
    }
}