package com.example.ecoearth_xin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.ecoearth_xin.databinding.ActivityHomepageBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class Homepage : AppCompatActivity() {
    private lateinit var binding: ActivityHomepageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val navView: NavigationView = findViewById(R.id.nav_view)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        binding.apply {
            navView.bringToFront()

            setSupportActionBar(toolbar)

            toggle = ActionBarDrawerToggle(
                this@Homepage,
                drawerLayout,
                R.string.open,
                R.string.close
            )

            drawerLayout.addDrawerListener(toggle)

            navView.setNavigationItemSelectedListener {
                when (it.itemId) {

                    R.id.nav_home -> {
                        Toast.makeText(this@Homepage, "Home is selected", Toast.LENGTH_SHORT)
                            .show()
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    R.id.nav_community -> {
                        Toast.makeText(this@Homepage, "Community is selected", Toast.LENGTH_SHORT)
                            .show()
                    }
                    R.id.nav_donation -> {
                        Toast.makeText(this@Homepage, "Donation is selected", Toast.LENGTH_SHORT)
                            .show()
                    }
                    R.id.nav_setting -> {
                        Toast.makeText(
                            this@Homepage, "Setting is selected",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    R.id.logoutbtn -> {
                        Toast.makeText(
                            this@Homepage, "Log Out!",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                }
                true
            }

        }

        navView.menu.findItem(R.id.logoutbtn).setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.logoutbtn -> {
                    auth.signOut()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }else -> false
            }

        }

        val newsbutton = findViewById<ImageButton>(R.id.newbutton)

        newsbutton.setOnClickListener {
            val intent = Intent(this, Newspage::class.java)
            startActivity(intent)
        }

        //binding.tipsbutton.setOnClickListener {
        //  startActivity(Intent(this, ClimateTips::class.java))
        //}

        //binding.videobutton.setOnClickListener {
        //  startActivity(Intent(this, EducationVideo::class.java))
        //}

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }
}