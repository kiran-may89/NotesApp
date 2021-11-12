package com.code.notes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.code.notes.presentation.screens.AddEditNoteScreen
import com.code.notes.presentation.util.Routes
import com.code.notes.ui.screens.notesScreen
import com.code.notes.ui.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : androidx.activity.ComponentActivity() {
    @SuppressLint("RestrictedApi")
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                androidx.compose.material.Surface(
                    color = MaterialTheme.colors.onSurface
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Routes.NoteScreen.route,
                    ) {
                        composable(route = Routes.NoteScreen.route) {
                            notesScreen(navController = navController)
                        }
                        composable(route = Routes.AddEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(name = "noteId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )) {
                            val color = it.arguments?.getInt("noteColor")?:-1
                            AddEditNoteScreen(navController = navController,noteColor = color)
                        }
                    }
                }
            }

        }
    }
}