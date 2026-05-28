package com.ssk.pagekeeper.feature.library.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import com.ssk.pagekeeper.core.designsystem.R

@Composable
  fun AnimatedMenuIcon(
    isOpen: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    animationSpec: FiniteAnimationSpec<Float> = tween(durationMillis = 300),
  ) {
      val rotation by animateFloatAsState(
          targetValue = if (isOpen) 360f else 0f,
          animationSpec = animationSpec,
          label = "menuRotation",
      )

      IconButton(onClick = onClick, modifier = modifier) {
          Crossfade(
              targetState = isOpen,
              animationSpec = animationSpec,
              label = "menuIcon",
          ) { open ->
              Icon(
                  painter = painterResource(
                      if (open) R.drawable.ic_nav_menu else R.drawable.ic_menu,
                  ),
                  contentDescription = if (open) "Close menu" else "Open menu",
                  modifier = Modifier.graphicsLayer { rotationZ = rotation },
              )
          }
      }
  }