package no3ratii.mohammad.dev.app.notebook.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import no3ratii.mohammad.dev.app.notebook.R


class ActivityMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* use navgation Controller from android jetpack
        set viewId for show in layout
        nte : in layout use app:navGraph for set navigation */
        setupActionBarWithNavController(findNavController(R.id.fragment))
    }

    //visible back arrow in action Bar and set for back to result activity
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}