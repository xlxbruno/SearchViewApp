package com.example.searchview

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.util.*
import kotlin.collections.ArrayList

@Composable
fun CountryListItem(countryText: String, onItemClick : (String) -> Unit){
    Row(
        modifier = Modifier
            .clickable(onClick = { onItemClick(countryText) })
            .background(colorResource(id = R.color.white))
            .height(57.dp)
            .fillMaxWidth()
            .padding(PaddingValues(8.dp, 16.dp))
    ) {
        Text("$countryText")
    }
}



@Composable
fun CountryList(navController: NavController, state:MutableState<TextFieldValue>){
val countries = getListOfCountries()
    var filteredCountries :ArrayList<String>
    LazyColumn(modifier = Modifier.fillMaxWidth()){
        val searchedText = state.value.text
        filteredCountries = if (searchedText.isEmpty()){
            countries
        } else {
            val resultList = ArrayList<String>()
            for(x in countries){
                if(x.lowercase(Locale.getDefault())
                    .contains(searchedText.lowercase((Locale.getDefault()))
                    )){
                        resultList.add(x)
                    }
            }
            resultList
        }
        items(filteredCountries) {filteredCountry -> CountryListItem(
            countryText = filteredCountry,
            onItemClick = { selectedCountry ->
                navController.navigate("details/$selectedCountry") {
                    popUpTo("main") {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
            }
        })
    }
}


@Composable
fun CountryLisPreview(){
    val navController = rememberNavController()
    val textState = remember {
        mutableStateOf(TextFieldValue(""))}
    CountryList(navController = navController, state = textState)
    }

fun getListOfCountries(): ArrayList<String> {
    val isoCountryCodes = Locale.getISOCountries()
    val countryListWithEmojis = ArrayList<String>()
    for (countryCode in isoCountryCodes) {
        val locale = Locale("", countryCode)
        val countryName = locale.displayCountry
        val flagOffset = 0x1F1E6
        val asciiOffset = 0x41
        val firstChar = Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset
        val secondChar = Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset
        val flag =
            (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))
        countryListWithEmojis.add("$countryName $flag")
    }
    return countryListWithEmojis
}}

fun getListOfCountries(): ArrayList<String> {
    val isoCountryCodes = Locale.getISOCountries()
    val countryListWithEmojis = ArrayList<String>()
    for (countryCode in isoCountryCodes) {
        val locale = Locale("", countryCode)
        val countryName = locale.displayCountry
        val flagOffset = 0x1F1E6
        val asciiOffset = 0x41
        val firstChar = Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset
        val secondChar = Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset
        val flag =
            (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))
        countryListWithEmojis.add("$countryName $flag")
    }
    return countryListWithEmojis

}

