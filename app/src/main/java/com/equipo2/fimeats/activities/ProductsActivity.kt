package com.equipo2.fimeats.activities

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.equipo2.fimeats.R
import com.equipo2.fimeats.adapters.ProductAdapter
import com.equipo2.fimeats.adapters.ProductListener
import com.equipo2.fimeats.database.Manager
import com.equipo2.fimeats.database.providers.Cafeterias
import com.equipo2.fimeats.models.Cafeteria
import com.equipo2.fimeats.models.Faculty
import com.equipo2.fimeats.models.Product
import com.equipo2.fimeats.viewmodels.ProductsViewModel

class ProductsActivity : AppCompatActivity(), ProductListener {
    private var rvProducts: RecyclerView? = null
    private var rlLoadingAllProducts: RelativeLayout? = null
    private lateinit var productsAdapter: ProductAdapter
    private lateinit var viewModel: ProductsViewModel
    private lateinit var myDatabase: SQLiteDatabase
    private lateinit var faculty: Faculty
    private lateinit var cafeteria: Cafeteria
    private lateinit var ivArrowBack: ImageView
    private lateinit var tvFacultyName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        ivArrowBack = findViewById(R.id.ivArrowBack)
        ivArrowBack.setOnClickListener { finish() }
        tvFacultyName = findViewById(R.id.tvFacultyName)

        val extras: Bundle? = intent.extras
        if (extras != null) {
            faculty = extras.getSerializable("faculty")!! as Faculty
        }

        tvFacultyName.text = faculty.shortName
        myDatabase = Manager(this).readableDatabase
        cafeteria = Cafeterias.find(myDatabase, faculty)!!

        rvProducts = findViewById(R.id.rvProducts)
        rlLoadingAllProducts = findViewById(R.id.rlLoadingAllProducts)
        getAllProducts(this)
    }

    private fun getAllProducts(context: Context) {
        this.viewModel = ViewModelProvider(this)[ProductsViewModel::class.java]
        this.viewModel.db = myDatabase
        this.viewModel.cafeteria = this.cafeteria
        this.viewModel.refresh()

        productsAdapter = ProductAdapter(this, this)
        rvProducts?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = productsAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        this.viewModel.listProducts.observe(this, {
            productsAdapter.updateData(it)
        })

        viewModel.isLoading.observe(this, {
            if (it != null) {
                when(it) {
                    true -> rlLoadingAllProducts?.visibility = View.VISIBLE
                    false -> rlLoadingAllProducts?.visibility = View.INVISIBLE
                }
            } else {
                rlLoadingAllProducts?.visibility = View.INVISIBLE
            }
        })
    }

    override fun onClick(product: Product, position: Int) {
        val intent = Intent(this@ProductsActivity, PaymentActivity::class.java)
        intent.putExtra("product", product)
        startActivity(intent)
    }
}