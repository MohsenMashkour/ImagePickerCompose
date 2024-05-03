package com.mkrdeveloper.imagepickercompose

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mkrdeveloper.imagepickercompose.ui.theme.ImagePickerComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ImagePickerComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var selectedImageUri by remember {
                        mutableStateOf<Uri?>(null)
                    }
                    var selectedImageUriList by remember {
                        mutableStateOf<List<Uri>>(emptyList())
                    }
                    val singleImagePickerLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.PickVisualMedia(),
                        onResult = { uri ->
                            selectedImageUri = uri
                        }
                    )
                    val multipleImagePickerLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.PickMultipleVisualMedia(),
                        onResult = { uriList ->
                            selectedImageUriList = uriList
                        }
                    )

                    Column(Modifier.fillMaxSize()) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Button(onClick = {
                                singleImagePickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }) {
                                Text(text = "pick single Image")
                            }
                            Button(onClick = {
                                multipleImagePickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }) {
                                Text(text = "pick multiple Images")
                            }
                        }
                        LazyColumn(
                            Modifier
                                .fillMaxSize()
                                .padding(12.dp)
                        ) {
                            item {
                                AsyncImage(
                                    model = selectedImageUri,
                                    contentDescription = "",
                                    Modifier.fillMaxWidth(),
                                    contentScale = ContentScale.Crop
                                )
                                Divider(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp))
                                Divider(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp))
                            }
                            items(selectedImageUriList) { uri ->
                                AsyncImage(
                                    model = uri,
                                    contentDescription = "",
                                    Modifier.fillMaxWidth(),
                                    contentScale = ContentScale.Crop
                                )
                                Divider(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}