package com.claire.carddiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.claire.carddiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModels()
    private val navController by lazy { findNavController(R.id.container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)
        navController.setGraph(R.navigation.nav_graph)
        setupActionBarWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        val closeItem = menu.findItem(R.id.close)
        val editItem = menu.findItem(R.id.edit)
        val checkItem = menu.findItem(R.id.check)
        val addItem = menu.findItem(R.id.add)

        navController.addOnDestinationChangedListener { controller, destination, _ ->
            controller.currentDestination?.let {
                when(destination.id) {
                    R.id.cardFragment -> {
                        closeItem.isVisible = false
                        editItem.isVisible = false
                        checkItem.isVisible = false
                        addItem.isVisible = true
                        addItem.setOnMenuItemClickListener {
                            navController.navigate(NavGraphDirections.actionGlobalEditFragment(null))
                            true
                        }
                    }
                    R.id.editFragment -> {
                        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_20)
                        closeItem.isVisible = false
                        editItem.isVisible = false
                        checkItem.isVisible = true
                        addItem.isVisible = false
                        checkItem.setOnMenuItemClickListener {
                            vm.callSaveData()
                            navController.navigateUp()
                            Toast.makeText(this, "save!", Toast.LENGTH_SHORT).show()
                            true
                        }
                    }
                    else -> {}
                }
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()
}