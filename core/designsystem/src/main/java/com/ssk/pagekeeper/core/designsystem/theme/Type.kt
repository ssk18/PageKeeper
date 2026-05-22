package com.ssk.pagekeeper.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.ssk.pagekeeper.core.designsystem.R

private val GoogleFontsProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs,
)

private val Lora = FontFamily(
    Font(googleFont = GoogleFont("Lora"), fontProvider = GoogleFontsProvider, weight = FontWeight.Medium),
    Font(googleFont = GoogleFont("Lora"), fontProvider = GoogleFontsProvider, weight = FontWeight.Bold),
)

private val Inter = FontFamily(
    Font(googleFont = GoogleFont("Inter"), fontProvider = GoogleFontsProvider, weight = FontWeight.Normal),
    Font(googleFont = GoogleFont("Inter"), fontProvider = GoogleFontsProvider, weight = FontWeight.Medium),
)

// Mapping from the design spec to M3 type roles.
//   Title-L-Bold      → headlineLarge   (Lora Bold     25/30)
//   Title-M-Medium    → headlineMedium  (Inter Medium  22/28)
//   Title-S-Medium    → titleLarge      (Lora Medium   17/20)
//   Body-L-Regular    → bodyLarge       (Inter Regular 16/24)
//   Body-M-Medium     → bodyMedium      (Inter Medium  15/18)
//   Body-M-Regular    → labelLarge      (Inter Regular 15/18)
//   Body-S-Regular    → bodySmall       (Inter Regular 13/16)
val PageKeeperTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
        lineHeight = 30.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        lineHeight = 20.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 18.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 18.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 16.sp,
    ),
)
