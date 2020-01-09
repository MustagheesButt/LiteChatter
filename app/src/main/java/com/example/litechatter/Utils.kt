package com.example.litechatter

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnackbar(view: View, msg: String) {
    Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
}