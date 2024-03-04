package com.example.littlelemonfinalproject

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.Locale.Category


@Composable
fun Home(navController: NavController? = null){

    val viewModel: MyViewModel = viewModel()
    val databaseMenuItems = viewModel.getAllDatabaseMenuItems().observeAsState(emptyList()).value
    val searchPhrase = remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit){
        viewModel.fetchMenuDataIfNeeded()
    }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
        {
            Header()
            UpperPanel(){
                searchPhrase.value = it
            }
            LowerPanel(databaseMenuItems, searchPhrase)
        }


        }

    }

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemsList(item: MenuItemRoom) {
    val itemDescription = if (item.description.length>100){
        item.description.substring(0,100) + ". . ."
    }
    else{
        item.description
    }

    Card(
        modifier = Modifier
            .clickable {

            }) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.fillMaxWidth(0.7f),
                verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = item.title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 10.dp))
                Text(text = itemDescription, modifier = Modifier.padding(bottom = 10.dp))
                Text(text = "$ ${item.price}", fontWeight = FontWeight.Bold)

            }

            GlideImage(model = item.image,
                contentDescription = "",
                Modifier.size(100.dp, 100.dp),
                contentScale = ContentScale.Crop)
        }
    }
}

@Composable
fun UpperPanel(search : (parameter: String) -> Unit) {
    val searchPhrase = remember {
        mutableStateOf("")
    }

    Log.d("AAAA", "UpperPanel: ${searchPhrase.value}")
    Column(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp)
    ) {
        Text(text = "Little Lemon",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFF4CE14)
        )
        Text(text = "Chicago",
            fontSize = 40.sp,
            color = Color(0xFFF4CE14)
        )
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(top = 20.dp)
        ){
            Text(text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                color = Color(0xFFEDEFEE),
                modifier = Modifier
                    .padding(bottom = 28.dp, end = 20.dp)
                    .fillMaxWidth(0.6f))
            Image(painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Hero Image",
                modifier = Modifier.clip(RoundedCornerShape(10.dp)))
        }
        Spacer(modifier = Modifier.size(10.dp))
        OutlinedTextField(value = searchPhrase.value,
            onValueChange = {
                searchPhrase.value = it
                search(searchPhrase.value)
            },
            placeholder = {
                Text(text = "Enter Search Phrase")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon")
            })

    }
}

@Composable
fun LowerPanel(databaseMenuItems: List<MenuItemRoom>, search: MutableState<String>) {
    val categories = databaseMenuItems.map {
        it.category.replaceFirstChar { character ->
            character.uppercase()
        }
    }.toSet()
    val selectedCategory = remember {
        mutableStateOf("")
    }

    val items = if (search.value == "") {
        databaseMenuItems
    } else {
        databaseMenuItems.filter {
            it.title.contains(search.value, ignoreCase = true)
        }
    }

    val filteredItems = if (selectedCategory.value == "" || selectedCategory.value == "all") {
        items
    } else {
        items.filter {
            it.category.contains(selectedCategory.value, ignoreCase = true)
        }
    }
    Column {
        MenuCategories(categories){
            selectedCategory.value = it
        }
        Spacer(modifier = Modifier.size(2.dp))
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            for (item in filteredItems){
                MenuItemsList(item = item)
            }
        }
    }

}

@Composable
fun  MenuCategories(categories: Set<String>, categoryLambda: (sel: String) -> Unit){
    val cat = remember {
        mutableStateOf("")
    }
    Card(Modifier.fillMaxWidth() ) {
        Column (Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
            Text(text = "ORDER FOR DELIVERY", fontWeight = FontWeight.Bold)

            Row (modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(top = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp))  {

                CategoryButton(category = "All"){
                    cat.value = it.lowercase()
                    categoryLambda(it.lowercase())
                }
                for (category in categories){
                    CategoryButton(category = category){
                        cat.value = it
                        categoryLambda(it)
                    }
                }
            }
        }
    }

}

@Composable
fun CategoryButton(category: String, selectedCategory: (sel: String) -> Unit){
    val isClicked = remember {
        mutableStateOf(false)
    }
    Button(onClick = { }) {
        isClicked.value = !isClicked.value
        selectedCategory(category)
    }
    Text(text = category)
}






