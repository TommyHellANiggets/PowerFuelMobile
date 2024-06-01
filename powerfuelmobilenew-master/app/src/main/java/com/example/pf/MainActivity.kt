package com.example.pf

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedBundle: Bundle?) {
        super.onCreate(savedBundle)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        replaceFragment(HomeFragment())

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            val fragment = when (menuItem.itemId) {
                R.id.menu_cart -> CartFragment()
                R.id.menu_home -> HomeFragment()
                R.id.menu_profile -> ProfileFragment()
                else -> null
            }
            fragment?.let { replaceFragment(it) }
            fragment != null
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}

class CartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }
}

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
}

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
        recyclerView.addItemDecoration(VerticalSpaceItemDecoration(spacingInPixels))

        val productList = listOf(
            Product(R.drawable.ic_launcher_foreground, "BCAA", 899),
            Product(R.drawable.ic_launcher_foreground, "Протеин", 1499),
            Product(R.drawable.ic_launcher_foreground, "Креатин", 699)
        )

        val adapter = ProductAdapter(productList) { product ->
            Toast.makeText(context, "${product.name} добавлен в корзину", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        return view
    }
}
