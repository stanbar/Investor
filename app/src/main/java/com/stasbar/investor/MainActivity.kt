package com.stasbar.investor

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.stasbar.investor.fragments.CompoundFragment
import com.stasbar.investor.fragments.RoiFragment
import com.stasbar.investor.utils.LogUtils

import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by stasbar on 13.07.2017
 */

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initDrawerView()
        if (savedInstanceState == null) { // Check if this is fresh new instance
            navigationView.setCheckedItem(R.id.navigation_item_compound_effect)
            drawerLayout.openDrawer(GravityCompat.START)
            setFragment(CompoundFragment(), CompoundFragment::class.java.canonicalName)
        }
    }


    private fun initDrawerView() {
        drawerToggle = object : ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close) {
            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
                hideKeyboard()
            }

        }
        drawerLayout.addDrawerListener(drawerToggle)
        navigationView.setNavigationItemSelectedListener(this)
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        LogUtils.d(item.title.toString())
        when (item.itemId) {
            R.id.navigation_item_compound_effect -> setFragment(CompoundFragment(), item.title)
            R.id.navigation_item_roi -> setFragment(RoiFragment(), item.title)
            else -> throw IllegalStateException()
        }
        return true
    }


    private fun setFragment(fragment: Fragment, name: CharSequence) {
        if (supportFragmentManager.findFragmentByTag(name.toString()) == null) {
            drawerLayout.closeDrawers()
            toolbar.title = name
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment, name.toString())
                    .commit()
        }
    }

    private fun hideKeyboard() {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }


    internal var wasClosed = false
    override fun onBackPressed() {
        if (wasClosed) {
            wasClosed = false
            super.onBackPressed()
        } else {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(navigationView)
                wasClosed = true
            } else {
                drawerLayout.openDrawer(navigationView)
                wasClosed = true
            }
        }
    }
}
