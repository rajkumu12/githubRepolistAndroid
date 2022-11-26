package com.decode.githubrepolisttask.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decode.githubrepolisttask.R
import com.decode.githubrepolisttask.ViewModel.MyViewModel
import com.decode.githubrepolisttask.data.Repos
import com.decode.githubrepolisttask.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var recyclerAdapter: RepoRecyclerViewAdapter
    lateinit var  viewModel:MyViewModel
    lateinit var binding: ActivityMainBinding
    var templist:List<Repos>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel= ViewModelProvider(this).get(MyViewModel::class.java)
        intRecyclerView();
        initViewModel()


       binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(query: String): Boolean {
                fiter(query)
                return false
            }
        })
    }

    private fun fiter(query: String) {
        var list= mutableListOf<Repos>()
        GlobalScope.launch {
            for (item in templist!!){
                if (item.repoName.contains(query)) {
                    list.add(item)
                }
            }
        }

        GlobalScope.launch(Dispatchers.Main){
            recyclerAdapter.setRepoList(list)
            recyclerAdapter.notifyDataSetChanged()
        }

    }

    private fun intRecyclerView() {
       binding.recyclerview.layoutManager=LinearLayoutManager(this)
        recyclerAdapter = RepoRecyclerViewAdapter(this)
        binding.recyclerview.adapter =recyclerAdapter
    }

    private fun initViewModel() {
        viewModel.getLiveDataObserver().observe(this, Observer {
            if(it != null) {
                templist=it
                recyclerAdapter.setRepoList(it)
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
            }
        })
    }
}