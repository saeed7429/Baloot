package ms.saghafi.baloot.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ms.saghafi.baloot.R
import ms.saghafi.baloot.databinding.ActivitySplashScreenBinding
import ms.saghafi.baloot.utils.setStatusBarWhite

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash_screen)

        setStatusBarWhite()
        runAnimation()

        // Go to Main Activity After 3 Seconds:
        Handler(Looper.getMainLooper()).postDelayed({
            startMainActivity()
        }, 3000)
    }

    private fun runAnimation() {
        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right_long)
        logoAnimation.reset()
        binding.splashScreenLogoImageView.clearAnimation()
        binding.splashScreenLogoImageView.startAnimation(logoAnimation)

        val panelNameAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left_long)
        panelNameAnimation.reset()
        binding.splashScreenTitleTextView.clearAnimation()
        binding.splashScreenTitleTextView.startAnimation(panelNameAnimation)

    }

    private fun startMainActivity() {
        val intent = Intent(baseContext, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }


}