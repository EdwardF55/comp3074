package ca.gbc.comp3074.fuller_edward.lab4

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CourseItem(course: Course){
    Column(modifier = Modifier.padding(16.dp).padding(16.dp)){
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = course.code,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = course.name,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun SimpleColumnList(courses: List<Course>){
    Column(
        modifier = Modifier.padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()
            )
    ){
        courses.forEach{
            course -> CourseItem(course)
            HorizontalDivider()
        }
    }
}

@Composable
fun LazyColumn (modifier: Modifier, courses: List<Course>){
    LazyColumn(modifier = Modifier.padding(16.dp)
        .fillMaxSize()
    ){
        item {
            Text(
                text = "Courses",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        items(courses){
            course ->
            CourseItem(course)
            HorizontalDivider()
        }
    }
}

@Composable
fun CourseScreen(modifier: Modifier) {
    val context = LocalContext.current
    val courses = remember{mutableStateListOf<Course>().apply {
        addAll(loadCourses(context))
    }}
    Column(modifier=modifier.fillMaxWidth()){
        Button(onClick= {
            val numCourses = courses.size
            val newCourse = Course(
                code = "COMP${numCourses + 1000}",
                name = "Course ${numCourses+1}"
            )
            courses.add(0,newCourse)
        },
            modifier=modifier.fillMaxWidth().padding(16.dp)){
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = "Add Course")
        }
    }

    LazyColumn(modifier,courses)
}