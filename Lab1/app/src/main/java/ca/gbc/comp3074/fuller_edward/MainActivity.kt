package ca.gbc.comp3074.fuller_edward

import	android.os.Bundle
import	android.widget.Button
import	android.widget.TextView
import	androidx.appcompat.app.AppCompatActivity
class	MainActivity	:	AppCompatActivity()	{
    override	fun	onCreate(savedInstanceState:	Bundle?)	{
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val	message	=	findViewById<TextView>(R.id.textViewMessage)
        val	button	=	findViewById<Button>(R.id.buttonChangeText)
        button.setOnClickListener	{
            message.text	=	"Button	clicked!"
        }
    }
}