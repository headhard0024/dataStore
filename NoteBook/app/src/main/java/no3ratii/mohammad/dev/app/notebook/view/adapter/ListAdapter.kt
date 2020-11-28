package no3ratii.mohammad.dev.app.notebook.view.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_item.view.*
import no3ratii.mohammad.dev.app.notebook.viewModel.ListViewModel
import no3ratii.mohammad.dev.app.notebook.R
import no3ratii.mohammad.dev.app.notebook.data.User
import no3ratii.mohammad.dev.app.notebook.view.fragment.FragmentUpdate
import no3ratii.mohammad.dev.app.notebook.view.activity.ActivityMain


class ListAdapter(val activity: ActivityMain, val lifecycleOwner: ViewModelStoreOwner) :
    RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    //initialize
    private lateinit var userViewModel: ListViewModel
    private var userList = emptyList<User>()
    fun getUserList(userList: List<User>) {
        this.userList = userList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        userViewModel = ViewModelProvider(lifecycleOwner).get(ListViewModel::class.java)
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.user_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tempPosition = position + 1
        val item = userList[position]

        //set layout logic for show and replase value
        laoyutLogic(holder, item, tempPosition)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {}
    }

    private fun laoyutLogic(holder: MyViewHolder, item: User, tempPosition: Int) {

        holder.itemView.txtId.text = "" + tempPosition
        holder.itemView.txtTitle.text = item.title
        holder.itemView.txtDesc.text = item.desc
        holder.itemView.txtStartTime.text = item.startTiem
        holder.itemView.txtEndTime.text = item.endTime

        if (item.startTiem.contains("0:0") && item.endTime.contains("0:0")) {
            holder.itemView.layTime.visibility = View.INVISIBLE
        } else {
            holder.itemView.layTime.visibility = View.VISIBLE
        }

        if (item.checked) {
            setOnImg(holder)
        } else {
            setOffImg(holder)
        }

        holder.itemView.layRoot.setOnClickListener {
            val intent = Intent(it.context, FragmentUpdate::class.java)
            intent.putExtra("id", item.id)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            it.context.startActivity(intent)
            activity.finish()
        }

        holder.itemView.imgCheck.setOnClickListener {
            if (item.checked) {
                upDateUser(item.id, item.title, item.desc, item.startTiem, item.endTime, false)
                setOffImg(holder)
            } else {
                upDateUser(item.id, item.title, item.desc, item.startTiem, item.endTime, true)
                setOnImg(holder)
            }
        }
    }

    private fun setOffImg(holder: MyViewHolder) {
        holder.itemView.imgCheck.setImageResource(R.drawable.ic_check_off)
    }

    private fun setOnImg(holder: MyViewHolder) {
        holder.itemView.imgCheck.setImageResource(R.drawable.ic_check_on)
    }

    private fun upDateUser(
        userId: Int,
        name: String,
        family: String,
        startTime: String,
        endTime: String,
        checked: Boolean
    ) {
        val user = User(userId, name, family, startTime, endTime, checked)
        userViewModel.updateUser(user)
    }
}