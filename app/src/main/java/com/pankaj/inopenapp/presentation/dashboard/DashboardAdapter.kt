package com.pankaj.inopenapp.presentation.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pankaj.inopenapp.databinding.ViewHolderSearchListBinding
import com.pankaj.inopenapp.domain.model.EmployeePostsItem

class DashboardAdapter : RecyclerView.Adapter<DashboardAdapter.MyViewHolder>() {

    private var listener: ((EmployeePostsItem) -> Unit)? = null

    var list = mutableListOf<EmployeePostsItem>()
    lateinit var employeeName: String
    lateinit var employeeCompany: String


    class MyViewHolder(val viewHolder: ViewHolderSearchListBinding) :
        RecyclerView.ViewHolder(viewHolder.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            ViewHolderSearchListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        Assigning post values to the item including name and company name
        holder.viewHolder.post = this.list[position]
        holder.viewHolder.employeeNameEdt.text = employeeName
        holder.viewHolder.employeeCompanyEdt.text = employeeCompany
        holder.viewHolder.root.setOnClickListener {
            listener?.let {
                it(this.list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return this.list.size
    }
}