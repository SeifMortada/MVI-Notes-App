package com.example.notesapp.core.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.core.presentation.screens.Screen
import com.example.notesapp.note_list.ui.NoteListScreenCore

@Composable
fun NotesAppNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.NoteList
    ) {
        composable<Screen.NoteList>() {
            NoteListScreenCore(
                onNavigateToAddNote = {
                    navController.navigate(Screen.AddNote)

                }
            )
        }
        composable<Screen.AddNote> {

        }
    }
}