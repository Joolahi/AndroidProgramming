package com.example.e08_employees_app

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONArray
import org.json.JSONObject

class EmployeesAdapter(private val employees: JSONArray)
    : RecyclerView.Adapter<EmployeesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : EmployeesAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.employee_item, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val title: TextView = view.findViewById(R.id.titleView)
        val email: TextView = view.findViewById(R.id.emailView)
        val phone: TextView = view.findViewById(R.id.phoneView)
        val department: TextView = view.findViewById(R.id.departmentView)
        val image : ImageView = view.findViewById(R.id.imageView)

        init {
            itemView.setOnClickListener{
                val intent = Intent(view.context, EmployeeActivity::class.java)
                intent.putExtra("employee", employees[adapterPosition].toString())
                view.context.startActivity(intent)
            }
        }

    }

    override fun onBindViewHolder(holder: EmployeesAdapter.ViewHolder, position: Int)
    {
        val employee: JSONObject = employees.getJSONObject(position)
        holder.nameTextView.text = employee["lastName"].toString()+" "+ employee["firstName"].toString()
        holder.title.text = employee["title"].toString()
        holder.email.text = employee["email"].toString()
        holder.phone.text = employee["phone"].toString()
        holder.department.text = employee["department"].toString()

        val imageUrl = employees.getJSONObject(position).getString("image")
        Glide.with(holder.image.context)
            .load(imageUrl)
            .into(holder.image)
    }
    override fun getItemCount(): Int = employees.length()
}