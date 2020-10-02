package xyz.ramil.pikaviewer.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xyz.ramil.pikaviewer.R
import xyz.ramil.pikaviewer.database.DataBaseManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (DataBaseManager.get() == null) DataBaseManager.create(applicationContext)
    }


}