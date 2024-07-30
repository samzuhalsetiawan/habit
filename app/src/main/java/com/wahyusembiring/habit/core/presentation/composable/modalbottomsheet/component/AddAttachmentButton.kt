package com.wahyusembiring.habit.core.presentation.composable.modalbottomsheet.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.wahyusembiring.habit.R
import com.wahyusembiring.habit.core.domain.model.Attachment
import com.wahyusembiring.habit.core.domain.model.File
import com.wahyusembiring.habit.core.domain.model.Link
import com.wahyusembiring.habit.core.domain.model.Photo
import com.wahyusembiring.habit.theme.spacing
import java.net.URI

@Composable
fun AddAttachmentButton(
   modifier: Modifier = Modifier,
   attachments: List<Attachment>,
   onClicked: (() -> Unit)? = null
) {
   Column {
      ListItem(
         modifier = modifier
            .then(
               if (onClicked != null) {
                  Modifier.clickable { onClicked() }
               } else {
                  Modifier
               }
            ),
         leadingContent = {
            Icon(
               painter = painterResource(id = R.drawable.ic_attachment),
               contentDescription = stringResource(R.string.add_attachment)
            )
         },
         headlineContent = {
            Text(
               color = TextFieldDefaults.colors().disabledTextColor,
               text = stringResource(R.string.add_attachment),
            )
         }
      )
      AttachmentPreview(attachments)
   }
}

@Composable
private fun AttachmentPreview(
   attachments: List<Attachment> = emptyList()
) {
   val urls = attachments.filterIsInstance<Link>()
   val photos = attachments.filterIsInstance<Photo>()
   val files = attachments.filterIsInstance<File>()

   LazyRow {
      if (urls.isNotEmpty()) {
         item {
            Box(
               modifier = Modifier
                  .size(85.dp)
                  .background(
                     color = MaterialTheme.colorScheme.primaryContainer,
                     shape = MaterialTheme.shapes.medium
                  ),
               contentAlignment = Alignment.Center
            ) {
               Text(
                  style = MaterialTheme.typography.bodySmall,
                  textAlign = TextAlign.Center,
                  text = "+${urls.size}\n${stringResource(R.string.links)}"
               )
            }
         }
      }
      item { Spacer(modifier = Modifier.width(MaterialTheme.spacing.Small)) }
      if (photos.isNotEmpty()) {
         items(photos) {
            Box(
               modifier = Modifier
                  .size(85.dp)
                  .background(
                     color = MaterialTheme.colorScheme.primaryContainer,
                     shape = MaterialTheme.shapes.medium
                  )
                  .clip(MaterialTheme.shapes.medium),
               contentAlignment = Alignment.Center
            ) {
               AsyncImage(
                  modifier = Modifier.fillMaxSize(),
                  model = it.uri,
                  contentDescription = it.name,
                  contentScale = ContentScale.Crop
               )
            }
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.Small))
         }
      }
      if (files.isNotEmpty()) {
         item {
            Box(
               modifier = Modifier
                  .size(85.dp)
                  .background(
                     color = MaterialTheme.colorScheme.primaryContainer,
                     shape = MaterialTheme.shapes.medium
                  ),
               contentAlignment = Alignment.Center
            ) {
               Column(
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.Center
               ) {
                  Icon(
                     modifier = Modifier.size(MaterialTheme.spacing.Medium),
                     painter = painterResource(id = R.drawable.ic_file),
                     contentDescription = stringResource(R.string.file)
                  )
                  Spacer(modifier = Modifier.height(MaterialTheme.spacing.Small))
                  Text(
                     style = MaterialTheme.typography.bodySmall,
                     textAlign = TextAlign.Center,
                     text = "+${files.size} ${stringResource(R.string.files)}"
                  )
               }
            }
         }
      }
   }
}