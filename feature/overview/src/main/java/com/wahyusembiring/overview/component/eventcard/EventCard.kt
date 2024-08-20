package com.wahyusembiring.overview.component.eventcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wahyusembiring.overview.R
import com.wahyusembiring.ui.theme.spacing

@Composable
fun EventCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Header()
        Body()
        Footer()
    }
}

@Composable
private fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.Medium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(50)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .padding(8.dp),
                painter = painterResource(id = R.drawable.ic_event),
                contentDescription = stringResource(R.string.event_icon),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.Small))
        Text(
            text = stringResource(R.string.event_overview),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun Body() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.Medium),
    ) {
        Image(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.Medium),
            painter = painterResource(id = R.drawable.relaxing),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.Medium))
        Text(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.Small),
            text = "There are no events",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "You can add new events with the button in the bottom right corner",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun Footer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.Medium),
        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_read_more),
                contentDescription = stringResource(R.string.show_more)
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.Small))
            Text(text = stringResource(R.string.show_more))
        }
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.Small))
        Button(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = stringResource(R.string.add_new_event)
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.Small))
            Text(text = stringResource(R.string.new_event))
        }
    }
}

@Preview
@Composable
private fun EventCardPreview() {
    EventCard()
}