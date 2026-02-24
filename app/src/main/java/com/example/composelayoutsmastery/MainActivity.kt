package com.example.composelayoutsmastery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.composelayoutsmastery.ui.theme.ComposeLayoutsMasteryTheme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip

// custom theme
// read ahead to ch 67
val WorkoutDarkPalette = darkColorScheme(
    primary = Color(0xFFCCFF00),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF2E3500),
    onPrimaryContainer = Color(0xFFCCFF00),
    secondary = Color(0xFFB0BEC5),
    surface = Color(0xFF121412),
    background = Color(0xFF0A0B0A),
    outline = Color(0xFF3F443F)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                MaterialTheme(colorScheme = WorkoutDarkPalette) {
                    SettingsScreen()
            }
        }
    }
}

// allows for using the TopAppBar if experimental (ch 66, we read later)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    // state management
    var isChecked by remember { mutableStateOf(true)}
    var cameraPermission by remember { mutableStateOf(false) }
    var locationPermission by remember { mutableStateOf(true) }
    var sliderValue by remember { mutableFloatStateOf(0.5f) }

    // --- MAIN SCREEN CONTAINER ---
    // REQUIREMENT: column as main layout container
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // REQUIREMENT: modifier - background!
            .padding(16.dp) // REQUIREMENT: modifier - padding

    ) {
        // ELEMENT: HEADER (TopAppBar)
        // REQUIREMENT: M3 component - TopAppBar
        TopAppBar(title = { Text("Epic Workout App Settings") })

        // ELEMENT: MAIN SETTINGS GROUP (Card)
        // REQUIREMENT: M3 component - Card
        Card(
            modifier = Modifier
                .fillMaxWidth() // REQUIREMENT: modifier - fillMaxWidth
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)) // REQUIREMENT: modifier - border
                .clip(RoundedCornerShape(8.dp)) // REQUIREMENT: modifier - clip
        ) {
            // inner column for stacking settings rows (ch 28.4)
            Column{
                // REQUIREMENT: each setting row must be a Row

                // SETTING 1: TOGGLE SWITCH (Training Alerts)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 64.dp) //REQUIREMENT: modifier - heightIn
                        .clickable { isChecked = !isChecked } // REQUIREMENT: modifier - clickable
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically // ch 28.5
                ) {
                    // ELEMENT: labels
                    // REQUIREMENT: Left side label + supporting text (Column inside Row)
                    Column(
                        modifier = Modifier.weight(1f) // REQUIREMENT: modifier - weight
                    ) {
                        Text("Training Alerts", fontWeight = FontWeight.Bold)
                        Text("Receive updates and alerts", fontSize = 12.sp)
                    }

                    // ELEMENT: right side control
                    // REQUIREMENT: right side control, M3 Component - Switch)
                    Switch(
                        checked = isChecked,
                        onCheckedChange = { isChecked = it },
                        modifier = Modifier.align(Alignment.CenterVertically) // REQUIREMENT: modifier - align
                    )
                }

                // REQUIREMENT: M3 component - Divider
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

                // SETTING 2: PERMISSIONS (Checkboxes)
                // REQUIREMENT: M3 component - checkboxes
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "App Permissions",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    // camera permissions
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { cameraPermission = !cameraPermission }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // M3 COMPONENT 5: Checkbox
                        Checkbox(checked = cameraPermission, onCheckedChange = { cameraPermission = it })
                        Text("Camera Access", modifier = Modifier.padding(start = 8.dp))
                    }

                    // location permissions
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { locationPermission = !locationPermission }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = locationPermission, onCheckedChange = { locationPermission = it })
                        Text("Location Access", modifier = Modifier.padding(start = 8.dp))
                    }
                }

                // REQUIREMENT: M3 component - Divider
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

                // SETTING 3: VOLUME ADJUSTER (Slider)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .sizeIn(minHeight = 64.dp) // REQUIREMENT: modifier - sizeIn
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // ELEMENT: labels
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Volume", fontWeight = FontWeight.Bold)
                        Text("Adjust audio levels", fontSize = 12.sp)
                    }

                    // ELEMENT: right side control
                    // REQUIREMENT: M3 component - Slider
                    Slider(
                        value = sliderValue,
                        onValueChange = { sliderValue = it },
                        modifier = Modifier.width(100.dp)
                    )

                    // extra because I highly disliked the slider without a value
                    Spacer(modifier = Modifier.width(8.dp))
                    // convert the 0.0-1.0 float to a 0-100 integer for display
                    Text(
                        text = (sliderValue * 100).toInt().toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.width(30.dp) // fixed width prevents "jumping" text
                    )

                }
            }
        }

        // ELEMENT: FOOTER ACTION (AssistChip)
        // REQUIREMENT: M3 component - AssistChip
        AssistChip(
            onClick = { },
            label = { Text("Storage Settings") },
            modifier = Modifier.padding(top = 16.dp).align(Alignment.End)
        )

    }





}


