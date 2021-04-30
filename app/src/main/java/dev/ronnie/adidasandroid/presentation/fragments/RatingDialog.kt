package dev.ronnie.adidasandroid.presentation.fragments

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.ronnie.adidasandroid.R
import dev.ronnie.adidasandroid.databinding.RatingDialogBinding


class RatingDialog : DialogFragment() {
    private lateinit var binding: RatingDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.rating_dialog, container, false)

        binding.apply {
            confirm.setOnClickListener {
                val text = text.text.toString()

                val rating = ratingBar.rating.toInt()

                if (text.isNotEmpty() && rating > 0) {
                    mCallback?.sendRating(Pair(text, rating))
                    binding.text.text = null
                    binding.ratingBar.rating = 0f
                }

                dialog?.dismiss()
            }
            cancel.setOnClickListener {
                dialog?.dismiss()
            }

        }


        return binding.root
    }

    var mCallback: SendInterface? = null

    interface SendInterface {
        fun sendRating(rating: Pair<String, Int>)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mCallback = try {
            parentFragment as SendInterface
        } catch (e: ClassCastException) {
            throw ClassCastException(
                parentFragment.toString()
                        + " must implement ChooseInterface"
            )
        }
    }

    override fun onResume() {
        super.onResume()
        val displayRectangle = Rect()
        val window: Window = requireActivity().window

        window.decorView.getWindowVisibleDisplayFrame(displayRectangle)

        this.dialog?.window!!.setLayout(
            (resources.displayMetrics.widthPixels * 3.75 / 4).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window!!.setGravity(Gravity.CENTER)
    }

}