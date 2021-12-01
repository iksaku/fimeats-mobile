package com.equipo2.fimeats.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.equipo2.fimeats.R
import com.equipo2.fimeats.adapters.FacultyAdapter
import com.equipo2.fimeats.adapters.FacultyListener
import com.equipo2.fimeats.database.Manager
import com.equipo2.fimeats.models.Faculty
import com.equipo2.fimeats.viewmodels.FacultiesViewModel

class MainActivity : AppCompatActivity(), FacultyListener {
    private var rvFaculties: RecyclerView? = null
    private var rlLoadingAllFaculties: RelativeLayout? = null
    private lateinit var facultiesAdapter: FacultyAdapter
    private lateinit var viewModel: FacultiesViewModel
    private lateinit var myDatabase: SQLiteDatabase

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvFaculties = findViewById(R.id.rvFaculties)
        rlLoadingAllFaculties = findViewById(R.id.rlLoadingAllFaculties)

        myDatabase = Manager(this).readableDatabase

        getAllFaculties(this)
    }

    private fun getAllFaculties(context: Context) {
        this.viewModel = ViewModelProvider(this)[FacultiesViewModel::class.java]
        this.viewModel.db = myDatabase
        this.viewModel.getAll = true
        this.viewModel.refresh()

        facultiesAdapter = FacultyAdapter(this, this)
        rvFaculties?.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = facultiesAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        this.viewModel.listFaculties.observe(this, {
            facultiesAdapter.updateData(it)
        })

        viewModel.isLoading.observe(this, {
            if (it != null) {
                when(it) {
                    true -> rlLoadingAllFaculties?.visibility = View.VISIBLE
                    false -> rlLoadingAllFaculties?.visibility = View.INVISIBLE
                }
            } else {
                rlLoadingAllFaculties?.visibility = View.INVISIBLE
            }
        })
    }

    override fun onClick(faculty: Faculty, position: Int) {
        val intent = Intent(this@MainActivity, ProductsActivity::class.java)
        intent.putExtra("faculty", faculty)
        startActivity(intent)
    }
}