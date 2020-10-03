package xyz.ramil.pikaviewer.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import xyz.ramil.pikaviewer.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
            } else {
                getSupportActionBar()?.setDisplayHomeAsUpEnabled(false)

            }
        }
    }
}