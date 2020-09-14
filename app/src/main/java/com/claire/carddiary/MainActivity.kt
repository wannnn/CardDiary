package com.claire.carddiary

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.claire.carddiary.databinding.ActivityMainBinding
import com.claire.carddiary.utils.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModels()
    private val navController by lazy { findNavController(R.id.container) }
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = vm
        vm.setStatusBarHeight(getStatusBarHeight)

        observeViewModel()

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)
    }

    private fun observeViewModel() {

        vm.isExpand.observe(this, Observer {
            if (it) {
                menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_up_24)
                binding.fabAdd.expandFab()
                binding.fabProfile.expandFab()
            } else {
                menu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_down_24)
                binding.fabAdd.collapseFab()
                binding.fabProfile.collapseFab()
            }
        })

        vm.fabClick.observe(this, Observer {
            vm.setExpand()
            when(it) {
                0 -> navController.navigate(NavGraphDirections.actionGlobalEditFragment(null))
                1 -> navController.navigate(NavGraphDirections.actionGlobalProfileFragment())
                else -> {}
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        this.menu = menu

        navController.addOnDestinationChangedListener { controller, destination, _ ->
            controller.currentDestination?.let {
                when(destination.id) {
                    R.id.cardFragment -> {
                        setOptionsItemVisibility(isArrow = true)
                    }
                    R.id.editFragment -> {
                        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_20)
                        setOptionsItemVisibility(isCheck = true)
                    }
                    R.id.profileFragment -> {
                        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_20)
                        setOptionsItemVisibility()
                    }
                    else -> {}
                }
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.arrow -> {
                vm.setExpand()
                true
            }
            R.id.close -> {
                true
            }
            R.id.edit -> {
                true
            }
            R.id.check -> {
                vm.callSaveData()
                navController.navigateUp()
                Toast.makeText(this, "save!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setOptionsItemVisibility(
        isArrow:Boolean = false,
        isClose:Boolean = false,
        isEdit:Boolean = false,
        isCheck:Boolean = false
    ) {
        menu?.let {
            it.findItem(R.id.arrow).isVisible = isArrow
            it.findItem(R.id.close).isVisible = isClose
            it.findItem(R.id.edit).isVisible = isEdit
            it.findItem(R.id.check).isVisible = isCheck
        }
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()
}