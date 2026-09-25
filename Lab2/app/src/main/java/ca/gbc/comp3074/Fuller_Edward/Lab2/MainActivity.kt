package ca.gbc.comp3074.Fuller_Edward.Lab2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import ca.gbc.comp3074.Fuller_Edward.Lab2.ui.theme.Lab2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ButtonStuff(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun ButtonStuff(
    modifier: Modifier = Modifier,
    url: Uri = "https://www.georgebrown.ca".toUri(),
    phone: Uri = "tel:41641550000".toUri(),
    location: Uri = ("geo:0,0?q=" + Uri.encode("160 Kendal Ave, Toronto")).toUri(),
    name: String
)
{
    val context = LocalContext.current
    var counter = remember { mutableIntStateOf(0) }
    var stepCounter = remember { mutableIntStateOf(1) }

    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)){
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Here's a thingy.",
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
            )
            Text("${counter.intValue}") //It's permanently attached to the eye. Same with the buttons - they never sat in 2x2, they always sat in 1x4, no matter what I did. It is likely you had settings that made yours display the way they did - I checked EVERYWHERE. Recording included...
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically){
            Button(onClick = {
                /*
                val intent = Intent(Intent.ACTION_VIEW,
                    url)
                context.startActivity(intent)
                */
                counter.intValue += stepCounter.intValue
            },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)) {
                Text("Increment")
            }
            Button(onClick = {
                /*
                val intent = Intent(Intent.ACTION_DIAL,
                    phone)
                context.startActivity(intent)
                */
                counter.intValue -= stepCounter.intValue
            },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                Text("Decrement")
            }
            Button(onClick = {
                /*
                val intent = Intent(Intent.ACTION_VIEW,
                    location)
                context.startActivity(intent)
                */
                counter.intValue=0
                stepCounter.intValue=1
            },
                modifier = Modifier.weight(1f)) {
                Text("Reset")
            }
            Button(onClick = {
                /*
                val intent = Intent(
                    context,
                    AboutActivity2::class.java
                ) //uhh
                context.startActivity(intent)
                */
                stepCounter.intValue++
            },
                modifier = Modifier.weight(1f)) {
                Text("+1 Step")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab2Theme {
        ButtonStuff(name = "Android")
    }
}