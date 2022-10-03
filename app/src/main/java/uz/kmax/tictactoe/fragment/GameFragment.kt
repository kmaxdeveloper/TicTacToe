package uz.kmax.tictactoe.fragment

import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.get
import androidx.core.view.setPadding
import androidx.core.view.size
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import uz.kmax.tictactoe.R
import uz.kmax.tictactoe.databinding.LayoutGameBinding
import uz.kmax.tictactoe.dialog.DialogEnter
import uz.kmax.tictactoe.dialog.DialogWin

class GameFragment : BaseFragment<LayoutGameBinding>(LayoutGameBinding::inflate) {
    private var buttonList = ArrayList<AppCompatImageView>()
    private var mInterstitialAd: InterstitialAd? = null
    private var dialog = DialogWin()
    private var dialogEnter = DialogEnter()
    private var turn = true
    private var nextAds = 0
    private var adType = 0
    private var steps = 0
    private var player1 = 0
    private var player2 = 0
    private var playerName1 = ""
    private var playerName2 = ""
    private var winnerName = ""

    override fun onViewCreated() {
        // Google Ads
        MobileAds.initialize(requireContext()) {}
        //
        dialogEnter.show(requireContext())
        dialogEnter.setOnClickListener {
            if (mInterstitialAd != null) {
                adType = 4
                mInterstitialAd?.show(requireActivity())
            } else {
                startMainFragment(MenuFragment())
            }
        }
        dialog.setOnClickListener {
            if (mInterstitialAd != null) {
                adType = 4
                mInterstitialAd?.show(requireActivity())
            } else {
                startMainFragment(MenuFragment())
            }
        }
        dialogEnter.setOnPlayerListener { player1, player2 ->
            binding.player1.text = "$player1 : "
            binding.player2.text = "$player2 : "
            playerName1 = player1
            playerName2 = player2
            binding.result1.text = "0"
            binding.result2.text = "0"
        }
        loadViews()
        binding.restart.setOnClickListener {
            if (nextAds % 2 == 0) {
                if (mInterstitialAd != null) {
                    adType = 2
                    mInterstitialAd?.show(requireActivity())
                } else {
                    nextAds += 1
                    Toast.makeText(requireContext(), "Restart Game", Toast.LENGTH_SHORT).show()
                    restart()
                    loadViews()
                }
            } else {
                nextAds += 1
                Toast.makeText(requireContext(), "Restart Game", Toast.LENGTH_SHORT).show()
                restart()
                loadViews()
            }
        }
        binding.back.setOnClickListener {
            if (mInterstitialAd != null) {
                adType = 1
                mInterstitialAd?.show(requireActivity())
            } else {
                backFragment()
            }
        }
    }

    private fun restart() {
        turn = true
        for (i in 0 until buttonList.size) {
            buttonList[i].setImageResource(0)
            buttonList[i].setPadding(5)
            buttonList[i].isClickable = true
            buttonList[i].tag = 0
        }
        steps = 0
    }

    private fun loadViews() {
        for (i in 0 until binding.gameLayout.size) {
            buttonList.add(binding.gameLayout[i] as AppCompatImageView)
        }

        for (i in 0 until buttonList.size) {
            buttonList[i].setOnClickListener {
                steps += 1
                if (turn) {
                    buttonList[i].setImageResource(R.drawable.iks)
                    buttonList[i].setPadding(35)
                    buttonList[i].isClickable = false
                    buttonList[i].tag = 1
                    turn = false
                    check(1)
                } else {
                    buttonList[i].setImageResource(R.drawable.zero)
                    buttonList[i].isClickable = false
                    buttonList[i].tag = 2
                    turn = true
                    check(2)
                }
            }
        }
    }

    private fun check(turn: Int) {
        if (steps != 9) {
            when (turn) {
                1 -> {
                    win(1)
                }
                2 -> {
                    win(2)
                }
            }
        } else {
            Toast.makeText(requireContext(), "Draw !!!", Toast.LENGTH_SHORT).show()
            restart()
        }
    }

    private fun win(tag: Int) {
        if (buttonList[0].tag == tag && buttonList[1].tag == tag && buttonList[2].tag == tag ||
            buttonList[3].tag == tag && buttonList[4].tag == tag && buttonList[5].tag == tag ||
            buttonList[6].tag == tag && buttonList[7].tag == tag && buttonList[8].tag == tag
        ) {
            if (tag == 1) {
                player1 += 1
                binding.result1.text = player1.toString()
                next(1)
            } else {
                player2 += 1
                binding.result2.text = player2.toString()
                next(2)
            }
        } else if (
            buttonList[0].tag == tag && buttonList[3].tag == tag && buttonList[6].tag == tag ||
            buttonList[1].tag == tag && buttonList[4].tag == tag && buttonList[7].tag == tag ||
            buttonList[2].tag == tag && buttonList[5].tag == tag && buttonList[8].tag == tag
        ) {
            if (tag == 1) {
                player1 += 1
                binding.result1.text = player1.toString()
                next(1)
            } else {
                player2 += 1
                binding.result2.text = player2.toString()
                next(2)
            }
        } else if (
            buttonList[0].tag == tag && buttonList[4].tag == tag && buttonList[8].tag == tag ||
            buttonList[2].tag == tag && buttonList[4].tag == tag && buttonList[6].tag == tag
        ) {
            if (tag == 1) {
                player1 += 1
                binding.result1.text = player1.toString()
                next(1)
            } else {
                player2 += 1
                binding.result2.text = player2.toString()
                next(2)
            }
        }
    }

    private fun next(turn: Int) {
        winnerName = if (turn == 1) {
            playerName1
        } else {
            playerName2
        }
        dialog.show(requireContext(), turn, winnerName)
        dialog.setOnClickListener {
            if (it == 1) {
                if (mInterstitialAd != null) {
                    adType = 1
                    mInterstitialAd?.show(requireActivity())
                } else {
                    backFragment()
                }
            } else {
                if (mInterstitialAd != null) {
                    adType = 3
                    mInterstitialAd?.show(requireActivity())
                } else {
                    restart()
                }
            }
        }
    }

    private fun ad() {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
            }

            override fun onAdDismissedFullScreenContent() {
                when (adType) {
                    1 -> {
                        backFragment()
                    }
                    2 -> {
                        nextAds += 1
                        Toast.makeText(requireContext(), "Restart Game", Toast.LENGTH_SHORT).show()
                        restart()
                        loadViews()
                    }
                    3 -> {
                        restart()
                    }
                    4 -> {
                        startMainFragment(MenuFragment())
                    }
                }
                mInterstitialAd = null
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                // Called when ad fails to show.
                mInterstitialAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // ca-app-pub-3940256099942544/1033173712 simple code
        // ca-app-pub-4664801446868642/8307417583 my code
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            requireContext(),
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("ADS", "ADS NoT loaded !")
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("ADS", "ADS loaded !")
                    mInterstitialAd = interstitialAd
                    ad()
                }
            })
    }
}