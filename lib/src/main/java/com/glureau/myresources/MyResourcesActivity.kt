package com.glureau.myresources

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListPopupWindow
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.glureau.myresources.core.Package
import com.glureau.myresources.core.ResParser
import com.glureau.myresources.core.filter.PackageFilter
import com.glureau.myresources.ui.BaseFragment
import com.glureau.myresources.ui.bool.BoolFragment
import com.glureau.myresources.ui.color.ColorFragment
import com.glureau.myresources.ui.dimen.DimenFragment
import com.glureau.myresources.ui.drawable.DrawableFragment
import com.glureau.myresources.ui.font.FontFragment
import com.glureau.myresources.ui.layout.LayoutFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


class MyResourcesActivity : AppCompatActivity() {

    private data class NavItem(
        val title: String,
        val tag: String,
        val fragmentFactory: () -> BaseFragment,
        val resCount: Package.() -> Int
    )

    // MenuItem id to NavItem
    private val navMap by lazy {
        mapOf(
            R.id.nav_bool to NavItem(
                getString(R.string.menu_bool),
                BoolFragment.FRAGMENT_TAG,
                ::BoolFragment,
                Package::boolCount
            ),
            R.id.nav_color to NavItem(
                getString(R.string.menu_color),
                ColorFragment.FRAGMENT_TAG,
                ::ColorFragment,
                Package::colorCount
            ),
            R.id.nav_dimen to NavItem(
                getString(R.string.menu_dimen),
                DimenFragment.FRAGMENT_TAG,
                ::DimenFragment,
                Package::dimenCount
            ),
            R.id.nav_drawable to NavItem(
                getString(R.string.menu_drawable),
                DrawableFragment.FRAGMENT_TAG,
                ::DrawableFragment,
                Package::drawableCount
            ),
            R.id.nav_font to NavItem(
                getString(R.string.menu_font),
                FontFragment.FRAGMENT_TAG,
                ::FontFragment,
                Package::fontCount
            ),
            R.id.nav_layout to NavItem(
                getString(R.string.menu_layout),
                LayoutFragment.FRAGMENT_TAG,
                ::LayoutFragment,
                Package::layoutCount
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.myr_activity_main)

        Log.e("OO", "ResourceAnalyser.INIT")
        ResParser.init(applicationContext)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener { item ->
            updateContent(item.itemId)
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        findViewById<View>(R.id.myr_appbar_menu)?.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        prepareSearchView()

        updateContent(R.id.nav_drawable)
        drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun prepareSearchView() {
        val searchView = findViewById<SearchView>(R.id.myr_appbar_search)
        searchView?.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    ResParser.repository.searchQuery(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    ResParser.repository.searchQuery(newText)
                    // Auto-close search view when no text
                    if (newText.isNullOrEmpty()) {
                        // TODO: This trick is not working great so far, since the focus is not redirected yet
                        //searchView.isIconified = true
                    }
                    return true
                }

            })
    }

    private fun preparePackageFiltering(currentPage: NavItem) {
        val selectedFilter = ResParser.repository.packageFilter
        val options = mutableListOf<String>()
        options += (if (selectedFilter is PackageFilter.AllPackageFilter) "✓ " else "") + "All packages"
        options += (if (selectedFilter is PackageFilter.KnownPackageFilter) "✓ " else "") + "Auto filtered"
        ResParser.repository.packages.forEach { pack ->
            val count = currentPage.resCount(pack)
            if (count > 0) {
                options += (if (selectedFilter is PackageFilter.SpecificPackageFilter && selectedFilter.packageName == pack.name) "✓ " else "") + "${pack.name} ($count)"
            }
        }
        val filterView = findViewById<View>(R.id.myr_appbar_filter)
        val listPopupWindow = ListPopupWindow(this)
        listPopupWindow.setAdapter(
            ArrayAdapter<String>(
                this@MyResourcesActivity,
                android.R.layout.simple_list_item_1,
                options
            )
        )
        listPopupWindow.anchorView = filterView
        listPopupWindow.width = 800
        listPopupWindow.height = ViewGroup.LayoutParams.WRAP_CONTENT
        listPopupWindow.isModal = true
        listPopupWindow.setOnItemClickListener { _, _, position, _ ->
            ResParser.repository.packageFilter = when (position) {
                0 -> PackageFilter.AllPackageFilter
                1 -> PackageFilter.KnownPackageFilter
                else -> PackageFilter.SpecificPackageFilter(ResParser.repository.packages[position - 1].name)
            }
            listPopupWindow.dismiss()
        }
        filterView?.setOnClickListener {
            listPopupWindow.show()
        }
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
        findViewById<TextView>(R.id.myr_appbar_title)?.text = nav.title

        preparePackageFiltering(nav)
    }

}
