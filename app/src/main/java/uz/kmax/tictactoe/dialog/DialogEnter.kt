package uz.kmax.tictactoe.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import uz.kmax.tictactoe.R
import uz.kmax.tictactoe.databinding.LayoutEnterDialogBinding
import uz.kmax.tictactoe.fragment.MenuFragment

class DialogEnter {

    private var dialogPlayerListener: ((player1: String, player2: String) -> Unit)? = null

    fun setOnPlayerListener(f: (player1: String, player2: String) -> Unit) {
        dialogPlayerListener = f
    }

    private var dialogClickListener : (()->Unit)? = null

    fun setOnClickListener(f:()-> Unit){
        dialogClickListener = f
    }

    fun show(context: Context) {
        val dialog = Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        val binding = LayoutEnterDialogBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)

        binding.back.setOnClickListener {
            dialogClickListener?.invoke()
            dialog.dismiss()
        }

        binding.startGame.setOnClickListener {
            val player1 :String = binding.player1.text.toString()
            val player2 :String = binding.player2.text.toString()
            if (player1.isNotEmpty() && player2.isNotEmpty()){
                dialogPlayerListener?.invoke(player1,player2)
                dialog.dismiss()
            }else{
                binding.errorMessage.visibility = View.VISIBLE
                object : CountDownTimer(3000, 100) {
                    override fun onFinish() {
                        binding.errorMessage.visibility = View.GONE
                    }
                    override fun onTick(value: Long) {
                        binding.errorMessage.resources.getColor(R.color.black)
                    }
                }.start()
            }
        }
        dialog.show()
    }
}