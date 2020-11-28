package no3ratii.mohammad.dev.app.notebook.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.fragment_list.view.fab
import no3ratii.mohammad.dev.app.notebook.view.activity.ActivityMain
import no3ratii.mohammad.dev.app.notebook.R
import no3ratii.mohammad.dev.app.notebook.view.adapter.ListAdapter
import no3ratii.mohammad.dev.app.notebook.viewModel.ListViewModel
import no3ratii.mohammad.dev.app.notebook.base.G


class FragmentList : Fragment() {

    private lateinit var userViewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        userViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        setHasOptionsMenu(true)

        val adapter = ListAdapter(activity = ActivityMain(), lifecycleOwner = this)
        val recycler = view.recRoot
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)

        userViewModel.readAllUser.observe(viewLifecycleOwner, Observer { userList ->
            adapter.getUserList(userList)
        })

        view.fab.setOnClickListener {
            val intent = Intent(G.context, FragmentAdd::class.java)
            startActivity(intent)
            activity?.finish()
        }

        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && fab.visibility === View.VISIBLE) {
                    fab.hide()
                } else if (dy < 0 && fab.visibility !== View.VISIBLE) {
                    fab.show()
                }
            }
        })

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.item_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete -> {
                showDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDialog() {
        val builder =
            AlertDialog.Builder(requireContext())
        builder.setTitle("هشدار")
        builder.setMessage("آیا میخواهید همه لیست ها حذف شوند ؟")
        builder.setPositiveButton(
            "بله"
        ) { dialog, which ->
            userViewModel.deleteAll()
            dialog.dismiss()
        }
        builder.setNegativeButton(
            "نه"
        ) { dialog, which -> // Do nothing
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }


}