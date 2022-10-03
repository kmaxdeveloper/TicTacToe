package uz.kmax.tictactoe.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import uz.kmax.tictactoe.R
import uz.kmax.tictactoe.databinding.LayoutDialogWinBinding

class DialogWin {

    private var dialogClickListener: ((command: Int) -> Unit)? = null

    fun setOnClickListener(f: (command: Int) -> Unit) {
        dialogClickListener = f
    }

    fun show(context: Context, turn: Int, WinnerName: String) {
        val dialog = Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        val binding = LayoutDialogWinBinding.inflate(LayoutInflater.from(context))
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)

        binding.winPlayer.setImageResource(
            if (turn == 1) {
                R.drawable.player1
            } else {
                R.drawable.player2
            }
        )

        binding.winPlayerName.text = if (turn == 1) {
            "$WinnerName (WIN)"
        } else {
            "$WinnerName (WIN)"
        }

        binding.back.setOnClickListener {
            dialogClickListener?.invoke(1)
            dialog.dismiss()
        }

        binding.next.setOnClickListener {
            dialogClickListener?.invoke(2)
            dialog.dismiss()
        }
        dialog.show()
    }
}