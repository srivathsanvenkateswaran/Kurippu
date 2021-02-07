package com.example.kurippu

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), INotesRecyclerViewAdapter {

    lateinit var viewModel: NoteViewModel
    private val newNoteActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var recyclerView : RecyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter: NoteRecyclerViewAdapter = NoteRecyclerViewAdapter(this, this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer {
            it.let {
                adapter.updateList(it)
            }
        })

        val addNewNoteButton = findViewById<FloatingActionButton>(R.id.addNoteFloatingButton)
        addNewNoteButton.setOnClickListener{
            val intent = Intent(this@MainActivity, NewNoteActivity::class.java)
            startActivityForResult(intent, newNoteActivityRequestCode)
        }
    }

    override fun onItemClicked(note: Note) {
        viewModel.delete(note)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newNoteActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewNoteActivity.EXTRA_REPLY)?.let {
                val word = Note(it)
                viewModel.insert(word)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }

}