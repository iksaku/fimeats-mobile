package com.equipo2.fimeats.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.equipo2.fimeats.R
import com.equipo2.fimeats.models.Faculty

class FacultyAdapter(private val context: Context, private val facultyListener: FacultyListener): RecyclerView.Adapter<FacultyAdapter.ViewHolder>()   {
    var listFaculties = ArrayList<Faculty>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
        R.layout.faculty_card, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val faculty = listFaculties[position]

        holder.tvFacultyName.text = faculty.shortName
        holder.ivLogo.setImageResource(context.resources.getIdentifier("logo_${faculty.shortName.lowercase()}_foreground", "mipmap", context.packageName));

        holder.itemView.setOnClickListener {
            facultyListener.onClick(faculty, position)
        }
    }

    override fun getItemCount() = listFaculties.size

    fun updateData(data: List<Faculty>) {
        listFaculties.clear()
        listFaculties.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFacultyName = itemView.findViewById<TextView>(R.id.tvFacultyName)
        val ivLogo = itemView.findViewById<ImageView>(R.id.ivLogo)
    }
}