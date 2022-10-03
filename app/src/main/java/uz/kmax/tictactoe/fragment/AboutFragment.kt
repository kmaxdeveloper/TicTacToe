package uz.kmax.tictactoe.fragment

import uz.kmax.tictactoe.BuildConfig
import uz.kmax.tictactoe.databinding.LayoutAboutBinding

class AboutFragment : BaseFragment<LayoutAboutBinding>(LayoutAboutBinding::inflate) {
    override fun onViewCreated() {
        binding.back.setOnClickListener {
            backFragment()
        }
        
        binding.buildVersion.text = "V: ${BuildConfig.VERSION_NAME}"
    }
}