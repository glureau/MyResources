package com.glureau.myresources

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.glureau.myresources.core.ResourceAnalyser
import com.glureau.myresources.ui.BaseFragment
import com.glureau.myresources.ui.bool.BoolFragment
import com.glureau.myresources.ui.color.ColorFragment
import com.glureau.myresources.ui.drawable.DrawableFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MyResourcesActivity : AppCompatActivity() {

    private data class NavItem(
        val title: String,
        val tag: String,
        val fragmentFactory: () -> BaseFragment
    )

    // MenuItem id to NavItem
    private val navMap = mapOf(
        R.id.nav_bool to NavItem("Booleans", BoolFragment.FRAGMENT_TAG, ::BoolFragment),
        R.id.nav_color to NavItem("Colors", ColorFragment.FRAGMENT_TAG, ::ColorFragment),
        R.id.nav_drawable to NavItem("Drawables", DrawableFragment.FRAGMENT_TAG, ::DrawableFragment)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.myr_activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { item ->
            Log.e("OO", "Open $item")
            updateContent(item.itemId)
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        ResourceAnalyser.init(applicationContext)
        updateContent(R.id.nav_drawable)
        drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun updateContent(itemId: Int) {
        val nav = navMap[itemId] ?: navMap.values.first()
        val fragment =
            (supportFragmentManager.findFragmentByTag(nav.tag) as? BaseFragment)
                ?: nav.fragmentFactory()

        supportFragmentManager.beginTransaction()
            .replace(R.id.myr_fragment_container, fragment, nav.tag)
            .addToBackStack(null)
            .commit()
        title = nav.title
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}
