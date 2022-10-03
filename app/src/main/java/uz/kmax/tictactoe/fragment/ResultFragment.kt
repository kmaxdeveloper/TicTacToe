package uz.kmax.tictactoe.fragment

import uz.kmax.tictactoe.databinding.LayoutResultBinding

class ResultFragment : BaseFragment<LayoutResultBinding>(LayoutResultBinding::inflate) {
    override fun onViewCreated() {
        binding.back.setOnClickListener {
            backFragment()
        }
    }
}