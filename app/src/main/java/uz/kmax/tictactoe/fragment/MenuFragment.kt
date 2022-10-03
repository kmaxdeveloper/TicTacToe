package uz.kmax.tictactoe.fragment

import android.widget.Toast
import uz.kmax.tictactoe.databinding.LayoutMenuBinding

class MenuFragment : BaseFragment<LayoutMenuBinding>(LayoutMenuBinding::inflate) {
    override fun onViewCreated() {
        binding.play.setOnClickListener {
            replaceFragment(GameFragment())
        }
        binding.exit.setOnClickListener {
            activity?.finish()
        }
        binding.about.setOnClickListener {
            replaceFragment(AboutFragment())
        }
        binding.result.setOnClickListener {
            //replaceFragment(ResultFragment())
            Toast.makeText(requireContext(), "Coming Soon !!!", Toast.LENGTH_SHORT).show()
        }
    }
}