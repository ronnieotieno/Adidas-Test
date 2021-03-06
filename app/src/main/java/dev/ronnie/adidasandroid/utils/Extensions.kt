package dev.ronnie.adidasandroid.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}


fun Fragment.hideSoftKeyboard() {
    val view = requireActivity().currentFocus

    view?.let {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}

fun Fragment.makeSnackBar(): Snackbar {
    val parentLayout = requireActivity().findViewById<View>(android.R.id.content)
    return Snackbar.make(
        parentLayout,
        "Posting review...",
        Snackbar.LENGTH_INDEFINITE
    )

}