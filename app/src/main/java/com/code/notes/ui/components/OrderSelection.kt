package com.code.notes.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.code.notes.domain.common.NoteOrder
import com.code.notes.domain.common.OrderType

@Composable
fun OrderSelection(
    modifier: Modifier = Modifier, noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
           defaultRadioButton(
               text = "Title",
               onCheck = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) },
               checked = noteOrder is NoteOrder.Title
           )
           Spacer(modifier = Modifier.width(8.dp))
           defaultRadioButton(
               text = "Date",
               onCheck = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) },
               checked = noteOrder is NoteOrder.Date
           )
           Spacer(modifier = Modifier.width(8.dp))
           defaultRadioButton(
               text = "Color",
               onCheck = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) },
               checked = noteOrder is NoteOrder.Color
           )
       }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            defaultRadioButton(
                text = "Ascending",
                onCheck = { onOrderChange(noteOrder.copy(OrderType.Ascending)) },
                checked = noteOrder.orderType is OrderType.Ascending
            )
            Spacer(modifier = Modifier.width(8.dp))
            defaultRadioButton(
                text = "Descending",
                onCheck = { onOrderChange(noteOrder.copy(OrderType.Descending)) },
                checked = noteOrder.orderType is OrderType.Descending
            )
        }
    }

}