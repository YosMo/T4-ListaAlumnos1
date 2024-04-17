package com.example.alumnlisttarea4.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.alumnlisttarea4.R
import com.example.alumnlisttarea4.components.AlumnCard
import com.example.alumnlisttarea4.viewmodels.AlumnViewModels

@Composable
fun ListView(navController: NavHostController) {

    //Variables
    var prompt: String by remember { mutableStateOf("") }
    var switchColor: Boolean = true
    var color: Color = Color(0xFFF9EBCF)
    val GamesViewModel = AlumnViewModels()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        Column () {
            OutlinedTextField(
                value = prompt,
                onValueChange = {prompt = it},
                label = { Text("Buscar por ID IEST o Nombre") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon (
                        painter = painterResource(id = R.drawable.person),
                        contentDescription = "Person",
                        tint = Color(0xFFD4A73C),
                    )
                },
            )

            Spacer(modifier = Modifier.height(5.dp))

            Button(
                onClick = {
                    val alumnList = GamesViewModel.getAlumnList()
                    val luckyAlumn = alumnList.random()
                    val message = "Alumno afortunado 🍀"

                    navController.navigate("AlumnView" +
                            "/$message" +
                            "/${luckyAlumn.id}" +
                            "/${luckyAlumn.name}" +
                            "/${luckyAlumn.mail}" +
                            "/${luckyAlumn.semester}" +
                            "/${luckyAlumn.career}" +
                            "/${luckyAlumn.hasScolarship}" +
                            "/${luckyAlumn.gpa.toFloat()}" +
                            "/${luckyAlumn.soldTickets}" +
                            "/${luckyAlumn.profilePic}"
                    )
                },
                colors = ButtonDefaults.buttonColors(Color(0xFF4CAF50)),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
            ) {
                Text(
                    text = "Alumno afortunado 🍀",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFFFFF),
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {
            items(GamesViewModel.getAlumnList()) {
                alumn ->
                    val alumnString = "${alumn.id} - ${alumn.name}"

                    if (alumnString.contains(prompt, ignoreCase = true)) {
                        AlumnCard(navController, alumn, color)
                        Spacer(modifier = Modifier.height(10.dp))

                        // Change bg color
                        if (switchColor) {
                            switchColor = false
                            color = Color(0xFFF2F2F2)
                        } else {
                            switchColor = true
                            color = Color(0xFFF9EBCF)
                        }
                    }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_List() {
    ListView(navController = rememberNavController())
}