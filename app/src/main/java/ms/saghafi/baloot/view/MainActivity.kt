package ms.saghafi.baloot.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ms.saghafi.baloot.R
import ms.saghafi.baloot.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindig : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindig = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainActivityNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        bindig.mainActivityBottomNavigation.setupWithNavController(navController)

    }

}